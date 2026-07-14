package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.TurnoCaja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurnoCajaRepository extends JpaRepository<TurnoCaja, Integer> {

    // Esta función busca si el cajero ya tiene un turno con estado "ABIERTA"
    Optional<TurnoCaja> findByIdUsuarioAndEstado(Integer idUsuario, String estado);
}