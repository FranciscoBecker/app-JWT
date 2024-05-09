package com.jwt.authorization;

import com.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public AuthorizationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        com.jwt.model.User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }
}

