package com.trial.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.trial.product.model.RsaKeys;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final RsaKeys rsaKeys;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .anyRequest()
                .authenticated()
                )
            // jwt conf 
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults())
            // requires jwtDecoder @Bean to run the app
            .oauth2ResourceServer(oauth2s -> oauth2s.jwt(Customizer.withDefaults()))
            .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(User.builder()
            .username("admin@admin.com")
            .password("admin")
            .roles("ADMIN")
            .authorities("ROLE_ADMIN")
            .passwordEncoder(psw -> PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(psw))
            .build());
    }

    /**
     * local jwt decoder (spring-boot-starter-oauth2-resource-server) instead of 3rd party
     * that uses a locally created certificate
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
            .withPublicKey(rsaKeys.publicKey())
            .build();
    }

    /**
     * encoder to generate jwt token
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey())
            .privateKey(rsaKeys.privateKey())
            .build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }

}
