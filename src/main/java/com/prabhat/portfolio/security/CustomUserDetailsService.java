package com.prabhat.portfolio.security;


import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prabhat.portfolio.entity.Admin;
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

        log.info("Loading user for authentication | email: {}", email);

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Authentication failed - admin not found | email: {}", email);
                    return new UsernameNotFoundException("Admin not found");
                });

        log.info("User loaded successfully | email: {}, role: {}",
                admin.getEmail(), admin.getRole());

        return new User(
                admin.getEmail(),
                admin.getPassword(),
                List.of(new SimpleGrantedAuthority(admin.getRole().name()))
        );
    }
}