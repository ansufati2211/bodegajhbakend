package com.bodegajh.bodegabackend.dto;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejar errores cuando no se encuentra un registro (Ej. Producto no existe)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), // Devuelve un 404
                ex.getMessage()               // Ej: "El producto con ID 5 no existe"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 2. Manejar errores de reglas de negocio (Ej. Stock insuficiente, datos inválidos)
    // Usaremos IllegalArgumentException para este ejemplo
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), // Devuelve un 400
                ex.getMessage()                 // Ej: "Stock insuficiente"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 3. El "Cajón de sastre" (Para cualquier error inesperado del servidor)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // Devuelve un 500
                "Ocurrió un error inesperado en el servidor. Contacte al administrador."
        );
        // Opcional: Puedes hacer un ex.printStackTrace() aquí para verlo en tu consola
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
