package com.trial.product.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import com.trial.product.model.User;

public interface TokenService {

    Jwt generateToken(Authentication auth);

    void createUser(User user);
    
}
