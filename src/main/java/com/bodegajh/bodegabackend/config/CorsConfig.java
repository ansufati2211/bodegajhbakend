package com.bodegajh.bodegabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. Permitir credenciales (vital para que pasen los tokens)
        config.setAllowCredentials(true);

        // 2. La URL exacta de tu React
        config.addAllowedOrigin("http://localhost:5173");

        // 3. Permitir todas las cabeceras (headers)
        config.addAllowedHeader("*");

        // 4. Permitir todos los métodos (GET, POST, PUT, DELETE, OPTIONS)
        config.addAllowedMethod("*");

        // Aplicar esta regla a TODAS las rutas de la API
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
