package com.bodegajh.bodegabackend.repositories;

import com.bodegajh.bodegabackend.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}