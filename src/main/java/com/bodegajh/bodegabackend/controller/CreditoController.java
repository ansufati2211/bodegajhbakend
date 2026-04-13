package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Credito;
import com.bodegajh.bodegabackend.repositories.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
@CrossOrigin(origins = "*")
public class CreditoController {

    @Autowired
    private CreditoRepository creditoRepository;

    @GetMapping
    public List<Credito> obtenerTodosLosCreditos() {
        return creditoRepository.findAll();
    }

    @PostMapping
    public Credito registrarCredito(@RequestBody Credito credito) {
        return creditoRepository.save(credito);
    }
}