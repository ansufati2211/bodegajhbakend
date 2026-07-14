package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Producto;
import com.bodegajh.bodegabackend.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
        if (producto.getPrecioVenta() < producto.getPrecioCompra()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El precio de venta no puede ser menor al precio de compra."));
        }
        producto.setEstado(true);
        return ResponseEntity.ok(productoRepository.save(producto));
    }

    // 3. Actualizar un producto (Para tu botón del Lápiz)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Integer id, @RequestBody Producto detallesProducto) {
        if (detallesProducto.getPrecioVenta() < detallesProducto.getPrecioCompra()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El precio de venta no puede ser menor al precio de compra."));
        }

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(detallesProducto.getNombre());
        producto.setStock(detallesProducto.getStock());
        producto.setCodigoBarras(detallesProducto.getCodigoBarras());
        producto.setPrecioCompra(detallesProducto.getPrecioCompra());
        producto.setPrecioVenta(detallesProducto.getPrecioVenta());
        producto.setIdCategoria(detallesProducto.getIdCategoria());
        producto.setIdProveedor(detallesProducto.getIdProveedor());

        return ResponseEntity.ok(productoRepository.save(producto));
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