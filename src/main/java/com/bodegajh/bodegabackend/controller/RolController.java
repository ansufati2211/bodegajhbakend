package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Rol;
import com.bodegajh.bodegabackend.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }
}