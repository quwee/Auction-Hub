package com.example.authservice.client;

import com.example.shared.dto.user.UserCreateRequest;
import com.example.shared.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "users", url = "localhost:8084/api/v1/users")
public interface UserServiceApi {

    @PostMapping("/create")
    void createUser(@RequestBody UserCreateRequest createRequest);

    @GetMapping("/get")
    UserDto getUser(@RequestParam Long id);

}
