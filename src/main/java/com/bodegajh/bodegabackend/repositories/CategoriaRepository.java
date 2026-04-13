package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Solo con heredar de JpaRepository, ya tienes métodos como findAll(), save(), delete()
}