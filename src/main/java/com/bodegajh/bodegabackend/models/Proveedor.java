package com.bodegajh.bodegabackend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(nullable = false, length = 100)
    private String empresa;

    @Column(name = "nombre_vendedor", length = 100)
    private String nombreVendedor;

    @Column(length = 20)
    private String telefono;

    @Column(name = "dias_visita", length = 100)
    private String diasVisita;
}