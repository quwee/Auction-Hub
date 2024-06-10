package com.example.auctionservice.service.impl;

import com.example.auctionservice.client.UserServiceApi;
import com.example.auctionservice.exception.*;
import com.example.auctionservice.model.dto.*;
import com.example.auctionservice.model.entity.Auction;
import com.example.auctionservice.model.entity.Bid;
import com.example.auctionservice.model.enums.AuctionStatus;
import com.example.auctionservice.model.mapper.AuctionMapper;
import com.example.auctionservice.model.mapper.BidMapper;
import com.example.auctionservice.model.mapper.ChatMessageMapper;
import com.example.auctionservice.repository.AuctionRepo;
import com.example.auctionservice.service.AuctionService;
import com.example.auctionservice.service.util.AuctionServiceUtils;
import com.example.auctionservice.service.util.ChatMessageServiceUtils;
import com.example.auctionservice.util.BidComparator;
import com.example.shared.dto.auction.AuctionCompleteDto;
import com.example.shared.dto.auction.AuctionDetailsDto;
import com.example.shared.dto.auction.AuctionDto;
import com.example.shared.dto.bid.BidResponseDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;
import com.example.shared.dto.user.UserDto;
import com.example.shared.jwtprocessing.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.shared.messagequeueconfig.constants.AppMessageQueueConstants.AUCTION_CLOSE_Q_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepo auctionRepo;
    private final AuctionMapper auctionMapper;
    private final BidMapper bidMapper;
    private final ChatMessageMapper chatMessageMapper;
    private final AuctionServiceUtils auctionServiceUtils;
    private final TaskScheduler taskScheduler;
    private final AmqpTemplate amqpTemplate;
    private final UserServiceApi userServiceApi;
    private final ChatMessageServiceUtils chatMessageServiceUtils;

    @Value("${app.img-upload-path}")
    private String imgUploadPath;


    @Override
    @Transactional
    public void createAuction(
            MultipartFile[] images, AuctionCreateRequestDto auctionRequest) {
        validate(images);

        List<String> imgNames = saveImages(images);

        log.debug("createAuction: principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Auction auction = auctionMapper.auctionCreateRequestDtoToAuction(
                auctionRequest, user.getId(), LocalDateTime.now(), AuctionStatus.ACTIVE, imgNames);

        auctionRepo.save(auction);

        scheduleCompleteAuction(auction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuctionSimpDto> getActiveAuctions() {
        return auctionRepo.findAllByStatus(AuctionStatus.ACTIVE)
                .stream().map(auction -> auctionMapper.auctionToAuctionSimpDto(
                        auction,
                        calculateCurrentPrice(auction),
                        auction.getImgNames().get(0)))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Auction getAuctionById(Long id) {
        return auctionRepo.findById(id).orElseThrow(() ->
                new AuctionNotFoundException("Auction with id %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionDetailsDto getAuctionDetails(Long auctionId) {
        Auction auction = getAuctionById(auctionId);

        List<BidResponseDto> bids = auction.getBids().stream()
                .map(bidMapper::bidToBidResponseDto)
                .toList();

        List<ChatMessageResponseDto> chatMessages = chatMessageServiceUtils.getChatMessages(auction);

        UserDto owner;
        try {
            owner = userServiceApi.getUser(auction.getOwnerId());
        } catch (Exception e) {
            throw new GetUserException("Fail to find auction owner with id %d in User Service"
                    .formatted(auction.getOwnerId()));
        }

        AuctionCompleteDto auctionComplete = null;

        if (auction.getStatus().equals(AuctionStatus.COMPLETED)) {
            var winner = calculateWinner(auction.getBids());

            Long winnerId = winner == null ? 0 : winner.getId();

            log.debug("getAuctionDetails: winnerId: {}", winnerId);

            String firstName = null;
            String lastName = null;

            if(winnerId != 0) {
                try {
                    UserDto user = userServiceApi.getUser(winnerId);
                    firstName = user.getFirstName();
                    lastName = user.getLastName();

                    log.debug("getAuctionDetails: firstName: {}", firstName);
                    log.debug("getAuctionDetails: lastName: {}", lastName);
                } catch (Exception e) {
                    throw new GetUserException("Fail to find winner with id %d in User Service"
                            .formatted(winnerId));
                }
            }

            auctionComplete = AuctionCompleteDto.builder()
                    .auctionId(auctionId)
                    .winnerId(winnerId)
                    .firstName(firstName)
                    .lastName(lastName)
                    .totalPrice(calculateCurrentPrice(auction))
                    .endDate(auction.getEndDate())
                    .build();

            log.debug("getAuctionDetails: auctionComplete: {}", auctionComplete);
        }

        String ownerFullName = owner.getFirstName() + " " + owner.getLastName();

        AuctionDto auctionDto = auctionMapper.auctionToAuctionDto(
                auction, ownerFullName, auctionServiceUtils.getMaxPriceBidDto(
                        bids, auction.getMinPrice()));

        return AuctionDetailsDto.builder()
                .auction(auctionDto)
                .auctionComplete(auctionComplete)
                .bids(bids)
                .chatMessages(chatMessages)
                .build();
    }

    @Override
    @Transactional
    public void completeAuction(AuctionCompleteRequestDto auctionRequestDto, boolean isPrematureFinished) {
        Long auctionId = auctionRequestDto.getAuctionId();
        Auction auction = getAuctionById(auctionId);

        if (auction.getStatus().equals(AuctionStatus.COMPLETED)) {
            throw new AuctionAlreadyCompletedException(
                    "Auction with id %d is already completed".formatted(auctionId));
        }

        LocalDateTime endDate = isPrematureFinished ? auction.getEndDate() : LocalDateTime.now();

        AuctionCompleteDto auctionComplete;
        var winner = calculateWinner(auction.getBids());

        Long winnerId = winner == null ? 0 : winner.getId();

        log.debug("getAuctionDetails: winnerId: {}", winnerId);

        String firstName = null;
        String lastName = null;

        if(winnerId != 0) {
            try {
                UserDto user = userServiceApi.getUser(winnerId);
                firstName = user.getFirstName();
                lastName = user.getLastName();

                log.debug("getAuctionDetails: firstName: {}", firstName);
                log.debug("getAuctionDetails: lastName: {}", lastName);
            } catch (Exception e) {
                throw new GetUserException("Fail to find winner with id %d in User Service"
                        .formatted(winnerId));
            }
        }

        auctionComplete = AuctionCompleteDto.builder()
                .auctionId(auctionId)
                .winnerId(winnerId)
                .firstName(firstName)
                .lastName(lastName)
                .totalPrice(calculateCurrentPrice(auction))
                .endDate(auction.getEndDate())
                .build();

        log.debug("getAuctionDetails: auctionComplete: {}", auctionComplete);

        auction.setStatus(AuctionStatus.COMPLETED);
        auction.setEndDate(endDate);

        auctionRepo.save(auction);

        amqpTemplate.convertAndSend(AUCTION_CLOSE_Q_NAME, auctionComplete);
    }

    @Override
    public Resource loadImage(String imageName) throws MalformedURLException {
        Path fileStorageLocation = Paths.get(imgUploadPath).toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(imageName).normalize();
        return new UrlResource(filePath.toUri());
    }

    private void validate(MultipartFile[] images) {
        if (images.length == 0 || images.length > 3) {
            throw new IncorrectImagesNumberException("Incorrect number of images");
        }
    }

    private List<String> saveImages(MultipartFile[] images) {
        List<String> imgNames = new ArrayList<>(images.length);

        for (int i = 0; i < images.length; i++) {
            String imgName = System.currentTimeMillis() + i +
                    getFileExtension(Objects.requireNonNull(images[i].getOriginalFilename()));
            try {
                images[i].transferTo(Paths.get(imgUploadPath + imgName));
                imgNames.add(imgName);
            } catch (IOException ex) {
                throw new FailSaveImagesException("Fail to save images", ex);
            }
        }
        return imgNames;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return fileName.substring(dotIndex);
    }

    private void scheduleCompleteAuction(Auction auction) {
        LocalDateTime endDate = auction.getEndDate();
        ZoneOffset minskOffset = ZoneOffset.ofHours(3);
        Instant instant = endDate.toInstant(minskOffset);

        taskScheduler.schedule(() -> completeAuction(
                new AuctionCompleteRequestDto(auction.getId()), false), instant);
    }

    private Integer calculateCurrentPrice(Auction auction) {
        if (auction.getBids().isEmpty()) {
            return auction.getMinPrice();
        }
        return calculateWinner(auction.getBids()).getPrice();
    }

    private Bid calculateWinner(List<Bid> bids) {
        return bids.stream()
                .max(new BidComparator())
                .orElse(null);
    }
}
