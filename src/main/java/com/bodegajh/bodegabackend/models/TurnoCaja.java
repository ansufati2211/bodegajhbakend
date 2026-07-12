package com.bodegajh.bodegabackend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "turnos_caja")
public class TurnoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Integer idTurno;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario; // Para saber qué cajero abrió la caja

    @Column(name = "nombre_cajero")
    private String nombreCajero; // Nombre del cajero para mostrar en reportes fácilmente

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "monto_inicial", nullable = false)
    private Double montoInicial; // La base o sencillo con la que empieza

    @Column(name = "monto_final")
    private Double montoFinal; // Lo que realmente hay en caja al cerrar (declarado por el cajero)

    @Column(name = "total_ventas")
    private Double totalVentas; // Suma de todas las ventas calculadas por el sistema

    @Column(nullable = false, length = 20)
    private String estado; // Solo aceptará "ABIERTA" o "CERRADA"


    // --- GETTERS Y SETTERS MANUALES ---

    public Integer getIdTurno() { return idTurno; }
    public void setIdTurno(Integer idTurno) { this.idTurno = idTurno; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreCajero() { return nombreCajero; }
    public void setNombreCajero(String nombreCajero) { this.nombreCajero = nombreCajero; }

    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDateTime fechaApertura) { this.fechaApertura = fechaApertura; }

    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }

    public Double getMontoInicial() { return montoInicial; }
    public void setMontoInicial(Double montoInicial) { this.montoInicial = montoInicial; }

    public Double getMontoFinal() { return montoFinal; }
    public void setMontoFinal(Double montoFinal) { this.montoFinal = montoFinal; }

    public Double getTotalVentas() { return totalVentas; }
    public void setTotalVentas(Double totalVentas) { this.totalVentas = totalVentas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}