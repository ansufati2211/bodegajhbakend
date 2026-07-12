package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Proveedor;
import com.bodegajh.bodegabackend.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    @PostMapping
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
    // 3. Actualizar un proveedor existente
    @PutMapping("/{id}")
    public Proveedor actualizar(@PathVariable Integer id, @RequestBody Proveedor proveedorRecibido) { // <--- CAMBIO AQUÍ A Integer
        // Buscamos el proveedor en la base de datos
        Proveedor proveedorExistente = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        // Actualizamos los datos
        proveedorExistente.setEmpresa(proveedorRecibido.getEmpresa());
        proveedorExistente.setNombreVendedor(proveedorRecibido.getNombreVendedor());
        proveedorExistente.setTelefono(proveedorRecibido.getTelefono());
        proveedorExistente.setDiasVisita(proveedorRecibido.getDiasVisita());

        // La línea mágica para el borrado lógico
        proveedorExistente.setEstado(proveedorRecibido.getEstado());

        // Guardamos los cambios
        return proveedorRepository.save(proveedorExistente);
    }
}