package com.jwt.service;

import com.jwt.dto.UserDTO;
import com.jwt.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();

    UserDTO findById(Long id);

    User findByLogin(String login);

    UserDTO create(UserDTO newUserDTO);

    UserDTO update(UserDTO newUserDTO);

    void delete(Long id);
}
