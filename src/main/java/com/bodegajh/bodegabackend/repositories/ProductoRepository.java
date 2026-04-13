package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Spring Data JPA creará automáticamente consultas útiles aquí más adelante
}