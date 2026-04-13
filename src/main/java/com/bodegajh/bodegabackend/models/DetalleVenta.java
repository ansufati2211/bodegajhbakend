package com.bodegajh.bodegabackend.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @Column(name = "id_venta", nullable = false)
    private Integer idVenta;

    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    // Estos campos los calcula el Trigger en la BD, por lo que le decimos a JPA que no intente insertarlos
    @Column(name = "ganancia_unitaria", insertable = false, updatable = false)
    private BigDecimal gananciaUnitaria;

    @Column(insertable = false, updatable = false)
    private BigDecimal subtotal;
}