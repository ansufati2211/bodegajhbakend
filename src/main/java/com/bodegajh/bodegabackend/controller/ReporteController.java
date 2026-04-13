package com.bodegajh.bodegabackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. Reporte para saber qué comprar (Stock <= 5)
    @GetMapping("/alertas-stock")
    public List<Map<String, Object>> obtenerAlertasStock() {
        String sql = "SELECT * FROM v_alertas_stock";
        return jdbcTemplate.queryForList(sql);
    }

    // 2. Reporte para saber quién debe en el barrio
    @GetMapping("/fiados-pendientes")
    public List<Map<String, Object>> obtenerEstadoFiados() {
        String sql = "SELECT * FROM v_estado_fiados";
        return jdbcTemplate.queryForList(sql);
    }

    // 3. Reporte de ganancias para el Dueño
    @GetMapping("/ganancias-semanales")
    public List<Map<String, Object>> obtenerReporteGanancias() {
        String sql = "SELECT * FROM v_reporte_ganancias";
        return jdbcTemplate.queryForList(sql);
    }
}