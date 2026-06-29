package com.prabhat.portfolio.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prabhat.portfolio.constants.Constants;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            
            .cors(cors  -> {})
            
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            
            .sessionManagement(s -> s
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers(Constants.AUTH_BASE_PATH + Constants.LOGIN_PATH,
            			         Constants.AUTH_BASE_PATH + Constants.REGISTER_PATH
            			         ).permitAll()
            	
                .requestMatchers(HttpMethod.POST, Constants.CONTACT_BASE_PATH)
                .authenticated()  
                
                .requestMatchers(Constants.CONTACT_BASE_PATH +"/**")
                .hasRole("ADMIN") 
                
                .anyRequest().authenticated()
            )
            
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}