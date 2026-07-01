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

        // Permitir credenciales (vital para que pasen los tokens JWT)
        config.setAllowCredentials(true);

        // Habilitar ambos puertos de Vite
        config.addAllowedOrigin("http://localhost:5173"); 
        config.addAllowedOrigin("http://localhost:5174");

        // Permitir todas las cabeceras (headers)
        config.addAllowedHeader("*");

        // Permitir todos los métodos (GET, POST, PUT, DELETE, OPTIONS)
        config.addAllowedMethod("*");

        // Aplicar esta regla a TODAS las rutas de la API
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}