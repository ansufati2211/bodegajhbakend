package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.Credito;
import com.bodegajh.bodegabackend.repositories.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Credito> obtenerTodosLosCreditos() {
        return creditoRepository.findAll();
    }

    @PostMapping
    public Credito registrarCredito(@RequestBody Credito credito) {
        return creditoRepository.save(credito);
    }

    /**
     * NUEVO: antes no existía forma de saldar un crédito desde la app.
     * Llama al procedimiento sp_abonar_credito ya definido en la BD,
     * que valida el monto y actualiza saldo_pendiente/estado.
     */
    // OJO: a propósito NO lleva @Transactional. sp_abonar_credito tiene un
    // COMMIT interno; si Spring ya hubiera abierto una transacción alrededor
    // de este método, ese COMMIT lanzaría "invalid transaction termination".
    // Sin @Transactional, JdbcTemplate corre en autocommit y el procedure
    // maneja su propio commit sin choque.
    @PostMapping("/{id}/abonar")
    public ResponseEntity<?> abonarCredito(@PathVariable Integer id, @RequestBody Map<String, BigDecimal> body) {
        BigDecimal monto = body.get("monto");
        if (monto == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Debe indicar el monto a abonar"));
        }

        try {
            jdbcTemplate.update("CALL sp_abonar_credito(?, ?)", id, monto);
        } catch (org.springframework.dao.DataAccessException e) {
            // sp_abonar_credito lanza RAISE EXCEPTION para reglas de negocio
            // (crédito inexistente, monto inválido, abono mayor a la deuda).
            // getMostSpecificCause() saca el mensaje real de Postgres, no el
            // wrapper genérico de Spring.
            return ResponseEntity.badRequest().body(Map.of("error", e.getMostSpecificCause().getMessage()));
        }

        Credito actualizado = creditoRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(actualizado);
    }
}