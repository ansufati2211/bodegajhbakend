package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Producto;
import com.bodegajh.bodegabackend.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // 1. Obtener TODOS los productos (SOLO LOS ACTIVOS)
    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getEstado() != null && producto.getEstado())
                .collect(Collectors.toList());
    }

    // 2. Guardar un nuevo producto
    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        producto.setEstado(true);
        return productoRepository.save(producto);
    }

    // 3. Actualizar un producto (Para tu botón del Lápiz)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto detallesProducto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(detallesProducto.getNombre());
        producto.setStock(detallesProducto.getStock());

        // CORREGIDO: Usando CamelCase
        producto.setCodigoBarras(detallesProducto.getCodigoBarras());
        producto.setPrecioCompra(detallesProducto.getPrecioCompra());
        producto.setPrecioVenta(detallesProducto.getPrecioVenta());
        producto.setIdCategoria(detallesProducto.getIdCategoria());
        producto.setIdProveedor(detallesProducto.getIdProveedor());

        Producto productoActualizado = productoRepository.save(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // 4. Borrado Lógico (Para tu botón de la Papelera)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado(false);
        productoRepository.save(producto);

        return ResponseEntity.ok().build();
    }
}