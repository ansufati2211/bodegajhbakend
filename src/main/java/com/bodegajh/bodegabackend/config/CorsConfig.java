package com.bodegajh.bodegabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource; // <- NUEVO IMPORT
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    // 1. Cambiamos el tipo de retorno a CorsConfigurationSource y el nombre del Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir credenciales (vital para que pasen los tokens JWT)
        config.setAllowCredentials(true);

        // La URL exacta de tu React
        config.addAllowedOrigin("http://localhost:5173");

        // Permitir todas las cabeceras (headers)
        config.addAllowedHeader("*");

        // Permitir todos los métodos (GET, POST, PUT, DELETE, OPTIONS)
        config.addAllowedMethod("*");

        // Aplicar esta regla a TODAS las rutas de la API
        source.registerCorsConfiguration("/**", config);

        // 2. Retornamos el 'source' directamente en lugar de envolverlo en un CorsFilter
        return source;
    }
}