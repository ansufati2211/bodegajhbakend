package com.bodegajh.bodegabackend.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    // Columna original obligatoria en BD
    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    // Nuevas columnas del frontend
    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(length = 15)
    private String dni;

    @Column(name = "celular", length = 20)
    private String telefono; // El frontend envía 'telefono', lo guardamos en 'celular'

    @Column(length = 100)
    private String correo;

    @Column(name = "deuda_total")
    private Double deudaTotal = 0.0;

    private Boolean estado = true;

    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;
}