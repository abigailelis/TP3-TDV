package com.integrador.grupoA.services.dto.tarifas.tarifaRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class TarifaRequestDTO {

    @NotBlank(message = "El campo tipo_tarifa no puede estar vacío")
    private String tipo_tarifa;

    @NotNull(message = "El campo monto no puede estar vacío")
    @Positive(message = "El monto debe ser positivo")
    private double monto;

    @NotNull(message = "La fecha de vigencia no puede estar vacía")
    @JsonFormat(pattern = "yyyy-MM-dd") // formato ISO estándar
    @Schema(type = "string", pattern = "yyyy-MM-dd", example = "2025-07-01")
    private LocalDate fechaVigencia;


}
