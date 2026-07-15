package com.bodegajh.bodegabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir credenciales (vital para que pasen los tokens JWT)[cite: 3]
        config.setAllowCredentials(true);

        // 1. Entorno de Desarrollo Local
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:5174");

        // 2. NUEVO: Entorno de Producción (Reemplaza con tu URL real sin el '/' al final)
        config.addAllowedOrigin("https://TU-DOMINIO-FRONTEND-EN-LA-WEB.com");

        // Permitir todas las cabeceras y métodos[cite: 3]
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Aplicar esta regla a TODAS las rutas de la API[cite: 3]
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}