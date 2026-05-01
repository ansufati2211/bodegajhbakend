package com.bodegajh.bodegabackend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Esta es la firma secreta de tu servidor. ¡Nadie más la tiene!
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // El token durará 24 horas (en milisegundos)
    private static final long EXPIRATION_TIME = 86400000;

    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Guardamos el email del usuario en el token
                .setIssuedAt(new Date()) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de caducidad
                .signWith(SECRET_KEY) // Lo firmamos con nuestra llave secreta
                .compact(); // Lo convertimos en un String
    }
}
