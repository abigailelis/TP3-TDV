package com.integrador.grupoA.exceptions.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo para respuestas de error")
public class ErrorResponse {

    @Schema(description = "Timestamp del error", example = "2025-06-21T22:15:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código del error", example = "RECURSO_NO_ENCONTRADO")
    private String codigo;

    @Schema(description = "Mensaje descriptivo del error", example = "No se encontró el usuario con ID 5")
    private String mensaje;

    public ErrorResponse(int statusCode, String error, String message, String description) {
        this.timestamp = LocalDateTime.now();
        this.codigo = String.valueOf(statusCode);
        this.mensaje = error + ": " + message + " - " + description;
    }

    public ErrorResponse(LocalDateTime now, int value, String conflict, String message, String description) {
        this.timestamp = now;
        this.codigo = String.valueOf(value);
        this.mensaje = conflict + ": " + message + " - " + description;
    }

    public ErrorResponse(String codigo, String message) {
        this.timestamp = LocalDateTime.now();
        this.codigo = codigo;
        this.mensaje = message;
    }
}


