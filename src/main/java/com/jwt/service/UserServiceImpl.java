package com.jwt.service;

import com.jwt.dto.UserDTO;
import com.jwt.model.User;
import com.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user -> new UserDTO(
                user.getId(),
                user.getLogin(),
                user.getPassword())).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new IllegalArgumentException("não achei");

        return new UserDTO(
                user.get().getId(),
                user.get().getLogin(),
                user.get().getPassword());
    }

    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login);

        if (user == null)
            throw new IllegalArgumentException("não achei");
        return user;
    }

    @Override
    public UserDTO create(UserDTO newUserDTO) {
        User user = new User(newUserDTO.getLogin(), newUserDTO.getPassword());

        user = userRepository.save(user);
        return new UserDTO(user.getId(), user.getLogin(), user.getPassword());
    }

    @Override
    public UserDTO update(UserDTO newUserDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
