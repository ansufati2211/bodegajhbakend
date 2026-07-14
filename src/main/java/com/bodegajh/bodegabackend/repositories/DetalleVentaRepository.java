package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {
}