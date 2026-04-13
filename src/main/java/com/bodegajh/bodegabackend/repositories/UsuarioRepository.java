package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Este método será la clave para iniciar sesión más adelante
    Optional<Usuario> findByUsername(String username);
}