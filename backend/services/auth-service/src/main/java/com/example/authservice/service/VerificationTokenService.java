package com.example.authservice.service;

import com.example.authservice.exception.TokenAlreadyExistException;
import com.example.authservice.exception.VerificationTokenNotFoundException;
import com.example.authservice.model.dto.AccountDetailsDto;
import com.example.authservice.model.entity.AuthUser;
import com.example.authservice.model.entity.VerificationToken;
import com.example.authservice.repository.VerificationTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationTokenService {

    private final VerificationTokenRepo tokenRepo;

    @Transactional
    public VerificationToken create(AuthUser authUser, AccountDetailsDto detailsDto) {
        tokenRepo.findByAuthUserId(authUser.getId()).ifPresent(token -> {
            var message = "Verification token associated with user %d already exist".formatted(authUser.getId());
            throw new TokenAlreadyExistException(message);
        });

        var token = UUID.randomUUID().toString();

        var verificationToken = VerificationToken.builder()
                .token(token)
                .isUsed(false)
                .firstName(detailsDto.getFirstName())
                .lastName(detailsDto.getLastName())
                .authUser(authUser)
                .build();

        return tokenRepo.save(verificationToken);
    }

    public VerificationToken getByToken(String token) {
        return tokenRepo.findByToken(token).orElseThrow(() ->
                new VerificationTokenNotFoundException("Verification token not found")
        );
    }
}
