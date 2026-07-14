package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditoRepository extends JpaRepository<Credito, Integer> {
}