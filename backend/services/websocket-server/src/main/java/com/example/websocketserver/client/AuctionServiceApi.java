package com.example.websocketserver.client;

import com.example.shared.dto.auction.AuctionDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auction-service", url = "localhost:8083/api/v1/auction")
public interface AuctionServiceApi {

    @GetMapping("/{id}")
    AuctionDetailsDto getAuctionHistory(@PathVariable Long id);

}
