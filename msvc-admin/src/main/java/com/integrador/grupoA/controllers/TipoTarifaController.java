package com.integrador.grupoA.controllers;

import com.integrador.grupoA.services.TipoTarifaService;
import com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaRequest.TipoTarifaRequestDTO;
import com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaResponse.TipoTarifaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipo_tarifas")
public class TipoTarifaController  {

    @Autowired
    TipoTarifaService tipoTarifaService;

    @GetMapping("")
    public ResponseEntity<List<TipoTarifaResponseDTO>> findAllTipoTarifas() {
        return ResponseEntity.ok(tipoTarifaService.findAllTipoTarifas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTarifaResponseDTO> obtenerTarifaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoTarifaService.findTarifaPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<TipoTarifaResponseDTO> findTarifaPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(tipoTarifaService.findTarifaPorTipo(tipo));
    }

    @PostMapping("")
    public ResponseEntity<TipoTarifaResponseDTO> agregarTarifa(@Valid @RequestBody TipoTarifaRequestDTO request) {
        TipoTarifaResponseDTO tipo_tarifa = tipoTarifaService.crearTipoTarifa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipo_tarifa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTarifa(@PathVariable Long id) {
        tipoTarifaService.eliminarTipoTarifa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTarifaResponseDTO> actualizarTipoTarifa(@PathVariable Long id, @Valid @RequestBody TipoTarifaRequestDTO request) {
        return ResponseEntity.ok(tipoTarifaService.actualizarTipoTarifa(id, request));
    }
}