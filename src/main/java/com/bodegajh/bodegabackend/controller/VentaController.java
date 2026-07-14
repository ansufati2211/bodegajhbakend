package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Venta;
import com.bodegajh.bodegabackend.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// 🚨 ¡ESTOS DOS IMPORTS LIBRAN A TU CONTROLADOR DE LAS LÍNEAS ROJAS!
import com.bodegajh.bodegabackend.dto.VentaRequest;
import com.bodegajh.bodegabackend.services.VentaService;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaService ventaService; // 👈 Ya no debe estar en rojo

    @GetMapping
    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequest request) { // 👈 Ya no debe estar en rojo
        try {
            ventaService.procesarNuevaVenta(request);
            return ResponseEntity.ok().body("{\"mensaje\": \"Venta procesada correctamente\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}