package com.bodegajh.bodegabackend.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String mensaje;
    private LocalDateTime fecha;

    public ErrorResponse(int status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
    }

    // Genera los Getters y Setters (o usa @Data si tienes Lombok instalado)
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
