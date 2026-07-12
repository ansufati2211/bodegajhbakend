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

    private Boolean estado = true;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDiasVisita() {
        return diasVisita;
    }

    public void setDiasVisita(String diasVisita) {
        this.diasVisita = diasVisita;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}