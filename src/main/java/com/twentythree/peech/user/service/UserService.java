package com.twentythree.peech.user.service;

import com.twentythree.peech.user.dto.CreateUserRequestDTO;

public interface UserService {
    Long createUser(CreateUserRequestDTO request);
}
