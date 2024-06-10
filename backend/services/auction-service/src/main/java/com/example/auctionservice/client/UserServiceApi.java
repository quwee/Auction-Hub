package com.example.auctionservice.client;

import com.example.shared.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "users", url = "localhost:8084/api/v1/users")
public interface UserServiceApi {

    @GetMapping("/get")
    UserDto getUser(@RequestParam Long id);

}
