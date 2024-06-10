package com.example.authservice.controller;

import com.example.authservice.model.dto.*;
import com.example.authservice.service.AuthUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthUserService authUserService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserRegisterRequestDto requestDto) {
        authUserService.register(requestDto);
    }

    @PostMapping("/confirm-registration")
    public void confirmRegistration(@RequestBody @Valid ConfirmRegistrationRequestDto requestDto) {
        authUserService.confirmRegistration(requestDto);
    }

    @PostMapping("/login")
    public SuccessAuthResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authUserService.login(requestDto);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody @Valid UserLogoutRequestDto requestDto) {
        authUserService.logout(requestDto);
    }

    @PostMapping("/refresh")
    public SuccessAuthResponseDto refresh(@RequestBody @Valid UserRefreshRequestDto requestDto) {
        return authUserService.refresh(requestDto);
    }

}
