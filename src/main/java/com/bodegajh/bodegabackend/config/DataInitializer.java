package com.bodegajh.bodegabackend.config;

import com.bodegajh.bodegabackend.models.Usuario;
import com.bodegajh.bodegabackend.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    // --- ¡ESTA ES LA PIEZA QUE FALTABA! ---
    // Aquí le damos a Spring Boot la herramienta para encriptar
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // --------------------------------------

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {

                System.out.println("Creando usuario Administrador por defecto...");

                Usuario admin = new Usuario();
                admin.setNombreCompleto("Juan Pérez");
                admin.setUsername("admin@sistema.com");
                // Aquí usamos la herramienta para encriptar "admin123"
                admin.setContrasena(passwordEncoder.encode("admin123"));
                admin.setEstado(true);

                usuarioRepository.save(admin);

                System.out.println("¡Administrador creado con éxito!");
                System.out.println("Email: admin@sistema.com | Password: admin123");
            }
        };
    }
}