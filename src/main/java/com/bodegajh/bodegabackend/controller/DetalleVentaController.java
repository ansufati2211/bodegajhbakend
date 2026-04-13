package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.DetalleVenta;
import com.bodegajh.bodegabackend.repositories.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Ahora sí se usa

@RestController
@RequestMapping("/api/detalle-ventas")
@CrossOrigin(origins = "*")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    // Se agrega este método que USA el "List"
    @GetMapping
    public List<DetalleVenta> obtenerTodosLosDetalles() {
        return detalleVentaRepository.findAll();
    }

    @PostMapping
    public DetalleVenta guardarDetalle(@RequestBody DetalleVenta detalle) {
        return detalleVentaRepository.save(detalle);
    }
}