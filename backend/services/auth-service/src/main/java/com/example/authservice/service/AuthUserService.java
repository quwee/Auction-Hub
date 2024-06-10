package com.example.authservice.service;

import com.example.authservice.client.UserServiceApi;
import com.example.authservice.exception.*;
import com.example.authservice.model.dto.*;
import com.example.authservice.model.entity.AuthUser;
import com.example.authservice.model.Role;
import com.example.authservice.repository.AuthUserRepo;
import com.example.shared.dto.event.EmailRegisterEvent;
import com.example.shared.dto.user.UserCreateRequest;
import com.example.shared.dto.user.UserDto;
import com.example.shared.jwtprocessing.model.User;
import com.example.shared.jwtprocessing.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUserService {

    private final AuthUserRepo authUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private final EmailEventSender emailEventSender;
    private final JwtService jwtService;
    private final UserServiceApi userServiceApi;

    @Transactional
    public void register(UserRegisterRequestDto requestDto) {
        if(!requestDto.getPassword().equals(requestDto.getPasswordConfirmation())) {
            throw new PasswordsNotMatchException("Passwords don't match!");
        }

        authUserRepo.findByEmail(requestDto.getEmail()).ifPresent(user -> {
            var message = "User with email %s already exist".formatted(user.getEmail());
            throw new UserAlreadyExistException(message);
        });

        AuthUser authUser = AuthUser.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(Role.ROLE_USER)
                .isAccountEnabled(false)
                .build();

        authUser = authUserRepo.save(authUser);

        var detailsDto = AccountDetailsDto.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .build();

        var verificationToken = verificationTokenService.create(authUser, detailsDto);

        var event = EmailRegisterEvent.builder()
                .email(authUser.getEmail())
                .fullName(String.format("%s %s", detailsDto.getFirstName(), detailsDto.getLastName()))
                .token(verificationToken.getToken())
                .build();

        emailEventSender.sendRegisterEvent(event);
    }

    @Transactional
    public void confirmRegistration(ConfirmRegistrationRequestDto requestDto) {
        var verificationToken = verificationTokenService.getByToken(requestDto.getVerificationToken());

        if(verificationToken.getIsUsed()) {
            throw new RegistrationAlreadyConfirmException("Registration is already confirmed");
        }

        verificationToken.setIsUsed(true);
        verificationToken.getAuthUser().setIsAccountEnabled(true);

        var userCreateDto = UserCreateRequest.builder()
                .id(verificationToken.getAuthUser().getId())
                .email(verificationToken.getAuthUser().getEmail())
                .firstName(verificationToken.getFirstName())
                .lastName(verificationToken.getLastName())
                .build();

        try {
            userServiceApi.createUser(userCreateDto);
        } catch (Exception ex) {
            var message = "Fail to create user with id %d in User Service"
                    .formatted(userCreateDto.getId());
            throw new CreateUserException(message, ex);
        }
    }

    @Transactional
    public SuccessAuthResponseDto login(UserLoginRequestDto requestDto) {
        AuthUser authUser = authUserRepo.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new UserNotFoundException("User with email %s not found"
                        .formatted(requestDto.getEmail())));

        if(!passwordEncoder.matches(requestDto.getPassword(), authUser.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        if(!authUser.getIsAccountEnabled()) {
            throw new AccountIsNotEnabledException("Account with email %s is not enabled"
                    .formatted(authUser.getEmail()));
        }

        return getSuccessAuthResponse(authUser);
    }

    @Transactional
    public void logout(UserLogoutRequestDto requestDto) {
        User principal = jwtService.extractUser(requestDto.getRefreshToken());

        authUserRepo.findById(principal.getId()).orElseThrow(() ->
                new IncorrectRefreshTokenException("User with id %d not found".formatted(principal.getId())));

        var authUser = authUserRepo.findByRefreshToken(requestDto.getRefreshToken()).orElseThrow(() ->
                new IncorrectRefreshTokenException("Incorrect refresh token for user with id %d".formatted(principal.getId())));

        authUser.setRefreshToken(null);
        authUserRepo.save(authUser);
    }

    @Transactional
    public SuccessAuthResponseDto refresh(UserRefreshRequestDto requestDto) {
        if (requestDto.getRefreshToken() == null || requestDto.getRefreshToken().isBlank()) {
            throw new IncorrectRefreshTokenException("Refresh token is empty");
        }

        User user = jwtService.extractUser(requestDto.getRefreshToken());

        authUserRepo.findById(user.getId()).orElseThrow(() ->
                new IncorrectRefreshTokenException("User with id %d not found".formatted(user.getId())));

        var authUser = authUserRepo.findByRefreshToken(requestDto.getRefreshToken()).orElseThrow(() ->
                new IncorrectRefreshTokenException("Refresh token not found for user with id %d".formatted(user.getId())));

        return getSuccessAuthResponse(authUser);
    }

    private AccessRefreshDto createTokens(AuthUser authUser) {
        String accessToken = jwtService.createAccessToken(authUser.getId(), authUser.getEmail(), authUser.getRole().name());
        String refreshToken = jwtService.createRefreshToken(authUser.getId(), authUser.getEmail(), authUser.getRole().name());

        return new AccessRefreshDto(accessToken, refreshToken);
    }

    private SuccessAuthResponseDto getSuccessAuthResponse(AuthUser authUser) {
        var accessRefreshDto = createTokens(authUser);

        authUser.setRefreshToken(accessRefreshDto.getRefreshToken());

        log.debug("login: accessToken: {}", accessRefreshDto.getAccessToken());
        log.debug("login: refreshToken: {}", accessRefreshDto.getRefreshToken());

        UserDto userDto;

        try {
            userDto = userServiceApi.getUser(authUser.getId());
        } catch (Exception e) {
            var message = "Failed to get user with id %d in User Service"
                    .formatted(authUser.getId());
            throw new GetUserException(message);
        }

        return SuccessAuthResponseDto.builder()
                .accessToken(accessRefreshDto.getAccessToken())
                .refreshToken(accessRefreshDto.getRefreshToken())
                .user(userDto)
                .build();
    }
}
