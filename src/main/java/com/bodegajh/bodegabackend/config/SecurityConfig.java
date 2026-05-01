package com.bodegajh.bodegabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. APAGAMOS EL ESCUDO CSRF: Esto es lo que causaba el error 403 al hacer POST
                .csrf(csrf -> csrf.disable())

                // 2. CONFIGURAMOS LOS PERMISOS: Dejamos pasar la ruta del login
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/login").permitAll()

                                // (Opcional) Si quieres que por ahora TODO esté libre mientras programas,
                                // comenta la línea de abajo y descomenta la siguiente:
                                .anyRequest().authenticated()
                        // .anyRequest().permitAll()
                );

        return http.build();
    }
}