package com.bodegajh.bodegabackend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "tipo_comprobante", length = 20)
    private String tipoComprobante;

    @Column(name = "numero_comprobante", length = 20)
    private String numeroComprobante;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "ganancia_neta", nullable = false, precision = 10, scale = 2)
    private BigDecimal gananciaNeta;

    @Column(name = "es_credito")
    private Boolean esCredito;

    @Column(name = "fecha_venta", insertable = false, updatable = false)
    private LocalDateTime fechaVenta;

    // --- GETTERS Y SETTERS MANUALES PARA EVITAR ERRORES DEL EDITOR ---

    public Integer getIdVenta() { return idVenta; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public String getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(String tipoComprobante) { this.tipoComprobante = tipoComprobante; }

    public String getNumeroComprobante() { return numeroComprobante; }
    public void setNumeroComprobante(String numeroComprobante) { this.numeroComprobante = numeroComprobante; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getGananciaNeta() { return gananciaNeta; }
    public void setGananciaNeta(BigDecimal gananciaNeta) { this.gananciaNeta = gananciaNeta; }

    public Boolean getEsCredito() { return esCredito; }
    public void setEsCredito(Boolean esCredito) { this.esCredito = esCredito; }

    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
}