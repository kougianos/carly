package com.carly.service.user;

import com.carly.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(String id);

    List<UserDTO> findAll();

    UserDTO patchUserById(String id, UserDTO userDTO);

    UserDTO deleteUserById(String id);
}
