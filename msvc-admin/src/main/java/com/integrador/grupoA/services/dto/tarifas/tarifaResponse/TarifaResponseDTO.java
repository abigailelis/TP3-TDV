package com.integrador.grupoA.services.dto.tarifas.tarifaResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TarifaResponseDTO {

    private Long id;
    private String tipo_tarifa;
    private double monto;
    private LocalDate fechaVigencia;
}
