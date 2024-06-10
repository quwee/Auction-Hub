package com.example.auctionservice.service;

import com.example.auctionservice.model.dto.AuctionCompleteRequestDto;
import com.example.auctionservice.model.dto.AuctionCreateRequestDto;
import com.example.auctionservice.model.dto.AuctionCreateResponseDto;
import com.example.auctionservice.model.dto.AuctionSimpDto;
import com.example.auctionservice.model.entity.Auction;
import com.example.shared.dto.auction.AuctionDetailsDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

public interface AuctionService {

    void createAuction(
            MultipartFile[] images, AuctionCreateRequestDto auctionRequestDto);

    List<AuctionSimpDto> getActiveAuctions();

    Auction getAuctionById(Long id);

    AuctionDetailsDto getAuctionDetails(Long auctionId);

    void completeAuction(AuctionCompleteRequestDto auctionRequestDto, boolean isPrematureFinished);

    Resource loadImage(String imageName) throws MalformedURLException;

}
