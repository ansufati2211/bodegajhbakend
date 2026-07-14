package com.bodegajh.bodegabackend.controller;

import com.bodegajh.bodegabackend.models.TurnoCaja;
import com.bodegajh.bodegabackend.repositories.TurnoCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
    private TurnoCajaRepository turnoCajaRepository;

    // 1. VERIFICAR SI EL CAJERO TIENE UNA CAJA ABIERTA ACTUALMENTE
    @GetMapping("/verificar/{idUsuario}")
    public ResponseEntity<?> verificarCajaAbierta(@PathVariable Integer idUsuario) {
        Optional<TurnoCaja> turnoAbierto = turnoCajaRepository.findByIdUsuarioAndEstado(idUsuario, "ABIERTA");

        if (turnoAbierto.isPresent()) {
            return ResponseEntity.ok(turnoAbierto.get()); // Retorna el turno actual activo si existe
        } else {
            // Le manda un JSON limpio al frontend avisando que la pantalla debe bloquearse para pedir apertura
            return ResponseEntity.ok().body(Map.of("mensaje", "CAJA_CERRADA"));
        }
    }
// 2. ABRIR EL TURNO DE CAJA (Registra con cuánto efectivo inicia)
    @PostMapping("/abrir")
    public ResponseEntity<?> abrirCaja(@RequestBody TurnoCaja nuevoTurno) {
        // NUEVO CANDADO: Verificar si ya existe una caja abierta para este usuario
        Optional<TurnoCaja> turnoAbierto = turnoCajaRepository.findByIdUsuarioAndEstado(nuevoTurno.getIdUsuario(), "ABIERTA");
        
        if (turnoAbierto.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El usuario ya tiene un turno de caja abierto. Ciérralo antes de abrir uno nuevo."));
        }

        // Inicializamos los valores obligatorios de auditoría antes de persistir
        nuevoTurno.setFechaApertura(LocalDateTime.now());
        nuevoTurno.setEstado("ABIERTA");
        nuevoTurno.setTotalVentas(0.0);
        nuevoTurno.setMontoFinal(0.0);
        
        TurnoCaja turnoGuardado = turnoCajaRepository.save(nuevoTurno);
        return ResponseEntity.ok(turnoGuardado);
    }

    // 3. CERRAR EL TURNO DE CAJA (Recibe los totales calculados y el dinero real)
    @PutMapping("/cerrar/{idTurno}")
    public ResponseEntity<TurnoCaja> cerrarCaja(@PathVariable Integer idTurno, @RequestBody TurnoCaja datosCierre) {
        TurnoCaja turnoActual = turnoCajaRepository.findById(idTurno)
                .orElseThrow(() -> new RuntimeException("El turno de caja especificado no existe."));

        // Sellamos el cierre del turno con la estampa de tiempo del servidor
        turnoActual.setFechaCierre(LocalDateTime.now());
        turnoActual.setMontoFinal(datosCierre.getMontoFinal()); // Lo que el cajero declara que hay físicamente en caja
        turnoActual.setTotalVentas(datosCierre.getTotalVentas()); // Suma total acumulada de las ventas del turno
        turnoActual.setEstado("CERRADA");

        TurnoCaja turnoActualizado = turnoCajaRepository.save(turnoActual);
        return ResponseEntity.ok(turnoActualizado);
    }
}