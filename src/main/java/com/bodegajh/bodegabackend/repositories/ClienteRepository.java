package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}