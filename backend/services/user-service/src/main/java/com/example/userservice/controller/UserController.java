package com.example.userservice.controller;

import com.example.shared.dto.user.UserDto;
import com.example.userservice.model.dto.ChangeDetailsRequest;
import com.example.shared.dto.user.UserCreateRequest;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public void create(@RequestBody @Valid UserCreateRequest request) {
        userService.create(request);
    }

    @GetMapping("/get")
    public UserDto get(@RequestParam Long id) {
        return userService.get(id);
    }

    @PostMapping("/change-details")
    public void changeDetails(@RequestBody @Valid ChangeDetailsRequest request) {
        userService.changeDetails(request);
    }
}
