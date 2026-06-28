package com.prabhat.portfolio.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prabhat.portfolio.constants.Constants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

   private final JwtService jwtService;
   
   private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader(Constants.AUTH_HEADER);

        if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
        	log.debug("No JWT token found in request");
        	chain.doFilter(request, response);
        	return;
        }
        
            String token = header.substring(Constants.TOKEN_PREFIX.length());
            
            if(!jwtService.isTokenValid(token)) {
            	chain.doFilter(request, response);
            	return;
            }
            
            try {
            	log.debug("Processing JWT token");

                String email = jwtService.extractEmail(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                	log.debug("JWT authentication success for user: {}", email);
                	
                	UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                	
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.debug("JWT valid — user: {}, role: {}", email, userDetails.getAuthorities());
                }
            } catch (Exception e) {
                log.warn("JWT authentication failed: {}", e.getMessage());
            }
            
            chain.doFilter(request, response);
        }
}