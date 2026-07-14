package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Usuario;
import com.bodegajh.bodegabackend.repositories.UsuarioRepository;
import com.bodegajh.bodegabackend.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Correo y contraseña son obligatorios"));
        }

        // Buscamos directo por username en vez de traer TODOS los usuarios
        // y comparar uno por uno (evita el N+1 y no expone la lista completa en memoria).
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(email);

        // Nunca logueamos el password recibido, ni en éxito ni en fallo.
        log.info("Intento de login para: {}", email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // 👇 ÚNICA validación permitida: contraseña hasheada con BCrypt.
            // Se quitó el fallback "password.equals(u.getContrasena())" que
            // aceptaba contraseñas guardadas en texto plano como puerta trasera.
            if (Boolean.TRUE.equals(usuario.getEstado()) && passwordEncoder.matches(password, usuario.getContrasena())) {
                String token = jwtService.generarToken(usuario.getUsername());

                Map<String, String> respuesta = new HashMap<>();
                respuesta.put("token", token);
                respuesta.put("mensaje", "¡Bienvenido!");
                log.info("Login exitoso para: {}", email);
                return ResponseEntity.ok(respuesta);
            }
        }

        log.warn("Login fallido para: {}", email);
        return ResponseEntity.status(401).body(Map.of("error", "Correo o contraseña incorrectos"));
    }
}