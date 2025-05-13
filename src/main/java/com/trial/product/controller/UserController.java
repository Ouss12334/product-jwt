package com.trial.product.controller;

import org.springframework.web.bind.annotation.RestController;

import com.trial.product.service.TokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final TokenService tokenService;

    @PostMapping("/account")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void createAccount(@RequestBody com.trial.product.model.User user, Authentication authentication) {
        tokenService.createUser(user);
    }

    @PostMapping("/token")
    public Jwt login(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
    
}
