package com.example.userservice.service;

import com.example.shared.dto.user.UserDto;
import com.example.userservice.model.dto.ChangeDetailsRequest;
import com.example.shared.dto.user.UserCreateRequest;

public interface UserService {

    void create(UserCreateRequest request);

    UserDto get(Long id);

    void changeDetails(ChangeDetailsRequest request);

}
