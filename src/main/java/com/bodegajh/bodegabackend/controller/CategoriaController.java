package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Categoria;
import com.bodegajh.bodegabackend.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*") // ¡Crucial! Permite que React (que correrá en otro puerto) pueda consumir esta API sin bloqueos de seguridad.
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> obtenerTodasLasCategorias() {
        // Esto irá a PostgreSQL, ejecutará un "SELECT * FROM categorias" y lo devolverá en formato JSON
        return categoriaRepository.findAll();
    }
}