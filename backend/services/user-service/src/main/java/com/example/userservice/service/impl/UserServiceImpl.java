package com.example.userservice.service.impl;

import com.example.shared.dto.user.UserDto;
import com.example.userservice.exception.UserAlreadyExistException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.dto.ChangeDetailsRequest;
import com.example.shared.dto.user.UserCreateRequest;
import com.example.userservice.model.entity.User;
import com.example.userservice.repository.UserRepo;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    @Transactional
    public void create(UserCreateRequest request) {
        userRepo.findById(request.getId()).ifPresent(user -> {
            throw new UserAlreadyExistException("User with id %d already exist".formatted(request.getId()));
        });

        var user = User.builder()
                .id(request.getId())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto get(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
            new UserNotFoundException("User with id %d not found".formatted(id)));

        return UserDto.builder()
                .id(id)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    @Override
    @Transactional
    public void changeDetails(ChangeDetailsRequest request) {
        var principal = (com.example.shared.jwtprocessing.model.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepo.findById(principal.getId()).orElseThrow(() ->
                new UserNotFoundException("User with id %d not found".formatted(principal.getId())));

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
        }
        userRepo.save(user);
    }
}
