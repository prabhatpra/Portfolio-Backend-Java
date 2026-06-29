package com.prabhat.portfolio.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prabhat.portfolio.entity.User;
import com.prabhat.portfolio.repository.AdminRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        log.debug("Loading user for authentication | email: {}", email);

      
        User user = adminRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Authentication failed - user not found | email: {}", email);
                    throw new UsernameNotFoundException("User not found");
                });

        log.debug("User loaded successfully | email: {}, role: {}",
                user.getEmail(), user.getRole());

        
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}