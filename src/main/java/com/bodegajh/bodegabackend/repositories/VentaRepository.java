package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
}