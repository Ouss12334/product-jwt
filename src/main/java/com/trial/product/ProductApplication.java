package com.trial.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.trial.product.model.RsaKeys;

@EnableWebSecurity
@EnableMethodSecurity // enable @PreAuthorize
@SpringBootApplication
@EnableConfigurationProperties(RsaKeys.class) // for jwt conf
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}
