package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Usuario;
import com.bodegajh.bodegabackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        usuario.setEstado(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setContrasena(encoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario detalles) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        u.setNombreCompleto(detalles.getNombreCompleto());
        u.setIdRol(detalles.getIdRol());
        if(detalles.getUsername() != null) {
            u.setUsername(detalles.getUsername());
        }
        return ResponseEntity.ok(usuarioRepository.save(u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        u.setEstado(false); // Borrado lógico
        usuarioRepository.save(u);
        return ResponseEntity.ok().build();
    }

    // NUEVO: Endpoint para alternar estado (Reactivar/Desactivar)
    @PutMapping("/{id}/toggle-estado")
    public ResponseEntity<?> toggleEstadoUsuario(@PathVariable Integer id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        u.setEstado(!u.getEstado());
        usuarioRepository.save(u);
        return ResponseEntity.ok().build();
    }
}