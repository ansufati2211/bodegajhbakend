package com.bodegajh.bodegabackend.models;
import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok genera los Getters y Setters automáticamente
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
}