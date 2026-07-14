package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
}