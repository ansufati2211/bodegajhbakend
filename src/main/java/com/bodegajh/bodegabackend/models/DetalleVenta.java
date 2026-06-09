package com.bodegajh.bodegabackend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

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

    // --- GETTERS Y SETTERS MANUALES PARA EVITAR ERRORES DEL EDITOR ---

    public Integer getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Integer idDetalle) { this.idDetalle = idDetalle; }

    public Integer getIdVenta() { return idVenta; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getGananciaUnitaria() { return gananciaUnitaria; }
    public void setGananciaUnitaria(BigDecimal gananciaUnitaria) { this.gananciaUnitaria = gananciaUnitaria; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}