package com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaResponse;

import com.integrador.grupoA.entities.TipoTarifa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TipoTarifaResponseDTO {

    private Long id;
    private String tipo_tarifa;

}
