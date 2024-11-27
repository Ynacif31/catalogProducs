package com.ygornacif.projetoCatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Permite acesso ao H2 Console
                        .anyRequest().permitAll() // Permite outras requisições
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // Atualização para desabilitar o uso de frames
                );
        return http.build();
    }
}