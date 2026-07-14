package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
}