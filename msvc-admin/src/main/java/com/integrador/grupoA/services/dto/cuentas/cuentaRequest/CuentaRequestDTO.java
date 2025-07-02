package com.integrador.grupoA.services.dto.cuentas.cuentaRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class CuentaRequestDTO {

    @NotBlank(message = "El campo entidad_bancaria no puede estar vacío")
    private String entidad_bancaria;

    @NotNull(message = "El campo numero_cuenta no puede estar vacío")
    private int numero_cuenta;

    @Positive(message = "El saldo debe ser positivo")
    private double saldo;

    @NotBlank(message = "El campo id_titular no puede estar vacío")
    private Long id_titular;
}
