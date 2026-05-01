package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Usuario;
import com.bodegajh.bodegabackend.repositories.UsuarioRepository;
import com.bodegajh.bodegabackend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService; // <-- Traemos nuestra fábrica de llaves

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        // --- NUESTROS CHISMOSOS PARA LA CONSOLA ---
        System.out.println("=====================================");
        System.out.println("INTENTO DE LOGIN DESDE REACT:");
        System.out.println("Email que llegó: " + email);
        System.out.println("Password que llegó: " + password);

        List<Usuario> usuarios = usuarioRepository.findAll();
        System.out.println("Total de usuarios en la BD: " + usuarios.size());

        for (Usuario u : usuarios) {
            System.out.println("Revisando al usuario en BD: " + u.getUsername());

            if (u.getUsername().equals(email)) {
                System.out.println("¡El correo coincide! Revisando contraseña...");

                if (password.equals(u.getContrasena()) || passwordEncoder.matches(password, u.getContrasena())) {
                    System.out.println("¡CONTRASEÑA CORRECTA! Dejando entrar...");
                    System.out.println("=====================================");

                    // 1. Generamos el token real
                    String tokenReal = jwtService.generarToken(u.getUsername());

                    // 2. Preparamos el mapa de respuesta UNA sola vez
                    Map<String, String> respuesta = new HashMap<>();
                    respuesta.put("token", tokenReal); // <-- Mandamos el token real a React
                    respuesta.put("mensaje", "¡Bienvenido!");

                    // 3. Retornamos la respuesta (y no ponemos nada debajo de esto)
                    return ResponseEntity.ok(respuesta);

                } else {
                    System.out.println("LA CONTRASEÑA NO COINCIDE.");
                    System.out.println("Contraseña en BD es: " + u.getContrasena());
                }
            }
        }

        System.out.println("Login fallido. Mandando error 401 a React.");
        System.out.println("=====================================");
        return ResponseEntity.status(401).body("Correo o contraseña incorrectos");
    }

}