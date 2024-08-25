package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.secondary.model.User;
import com.example.secondary.repository.UserRepository;
import com.example.secondary.repository.UserRoleRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Попытка загрузить пользователя по имени пользователя: {}", username);
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        List<SimpleGrantedAuthority> userRoles = userRoleRepository.findByUserId(user.getId()).stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .toList();
        log.info("User roles: {}", userRoles);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), userRoles);
    }
}
