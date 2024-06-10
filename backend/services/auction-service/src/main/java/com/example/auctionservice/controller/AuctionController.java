package com.example.auctionservice.controller;

import com.example.auctionservice.model.dto.AuctionCompleteRequestDto;
import com.example.auctionservice.model.dto.AuctionCreateRequestDto;
import com.example.auctionservice.model.dto.AuctionCreateResponseDto;
import com.example.auctionservice.model.dto.AuctionSimpDto;
import com.example.auctionservice.service.AuctionService;
import com.example.shared.dto.auction.AuctionDetailsDto;
import com.example.shared.dto.constants.PatternConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction")
@Slf4j
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/create-auction")
    public void createAuction(
            @RequestPart @Valid @NotEmpty MultipartFile[] images,
            @RequestPart @Valid AuctionCreateRequestDto requestDto
    ) {
        auctionService.createAuction(images, requestDto);
    }

    @GetMapping
    public List<AuctionSimpDto> getActiveAuctions() {
        return auctionService.getActiveAuctions();
    }

    @GetMapping("/details/{id}")
    public AuctionDetailsDto getAuctionDetails(@PathVariable Long id) {
        return auctionService.getAuctionDetails(id);
    }

    @PostMapping("/complete-auction")
    public void completeAuction(
            @RequestBody @Valid AuctionCompleteRequestDto requestDto
    ) {
        auctionService.completeAuction(requestDto, true);
    }

    @GetMapping("/load-image/{imageName:.+}")
    public ResponseEntity<Resource> loadImage(
            @PathVariable String imageName, HttpServletRequest request) throws IOException {
        Resource resource = auctionService.loadImage(imageName);

        String contentType = request.getServletContext().getMimeType(
                resource.getFile().getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; imageName=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
