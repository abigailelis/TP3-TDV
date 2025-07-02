package com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class TipoTarifaRequestDTO {

    @NotBlank(message = "El campo tipo_tarifa no puede estar vacío")
    private String tipo_tarifa;

}
