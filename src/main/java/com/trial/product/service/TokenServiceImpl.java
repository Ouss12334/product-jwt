package com.trial.product.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * service to generate the token with claims using the encoder
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder jwtEncoder;
    private final UserDetailsService userDetailsService;

    public Jwt generateToken(Authentication auth) {
        log.info("generating token for user: {}", auth.getName());
        // get user role from login 
        var scope = auth.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
            
        // set expiration to 1h
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("http://localhost:8089") // must be a url if returning the jwt object
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
            .subject(auth.getName())
            .claim("scope", scope)
            .build();
        
        // return jwt token object
        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }

    @Override
    public void createUser(com.trial.product.model.User user) {
        log.info("adding new user");
        ((InMemoryUserDetailsManager) userDetailsService).createUser(
            User.builder()
        .username(user.username())
        .password(user.password())
        .roles("USER")
        .passwordEncoder(psw -> PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(psw))
        .build()
        );
    }

}
