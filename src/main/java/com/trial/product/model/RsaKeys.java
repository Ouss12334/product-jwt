package com.trial.product.model;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rsa")
public record RsaKeys (RSAPublicKey publicKey, RSAPrivateKey privateKey) {

}
