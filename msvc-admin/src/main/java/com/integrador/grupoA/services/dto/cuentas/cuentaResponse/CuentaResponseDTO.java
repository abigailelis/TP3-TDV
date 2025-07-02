package com.integrador.grupoA.services.dto.cuentas.cuentaResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CuentaResponseDTO {

    private Long id;
    private String entidad_bancaria;
    private int numero_cuenta;
    private double saldo;
    private Long id_titular;
}
