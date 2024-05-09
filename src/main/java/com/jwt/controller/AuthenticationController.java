package com.jwt.controller;

import com.jwt.authorization.AuthorizationDTO;
import com.jwt.authorization.TokenDTO;
import com.jwt.dto.UserDTO;
import com.jwt.framework.jwt.JWTService;
import com.jwt.model.User;
import com.jwt.repository.UserRepository;
import com.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthorizationDTO authorizationDTO) {
        UsernamePasswordAuthenticationToken userNamePassword =
                new UsernamePasswordAuthenticationToken(authorizationDTO.getLogin(), authorizationDTO.getPassword());
        Authentication auth = authenticationManager.authenticate(userNamePassword);
        User user = userRepository.findByLogin(authorizationDTO.getLogin());

        String token = jwtService.generateToken(user);
        TokenDTO tokenDTO = new TokenDTO(token);

        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        userDTO.setPassword(encryptedPassword);
        UserDTO savedUserDTO = userService.create(userDTO);

        return ResponseEntity.ok(savedUserDTO);
    }
}
