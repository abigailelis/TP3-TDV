package com.integrador.grupoA.controllers;

import com.integrador.grupoA.services.TarifaService;
import com.integrador.grupoA.services.dto.tarifas.tarifaRequest.TarifaRequestDTO;
import com.integrador.grupoA.services.dto.tarifas.tarifaResponse.TarifaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    TarifaService tarifaService;

    @GetMapping("")
    public ResponseEntity<List<TarifaResponseDTO>> obtenerTarifas(){
        return ResponseEntity.ok(tarifaService.obtenerTarifas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaResponseDTO> obtenerTarifaPorId(@PathVariable Long id){
        return ResponseEntity.ok(tarifaService.findTarifaPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<TarifaResponseDTO>> obtenerTarifasPorTipo(@PathVariable String tipo){
        return ResponseEntity.ok(tarifaService.obtenerTarifasPorTipo(tipo));
    }

    @PostMapping("")
    public ResponseEntity<TarifaResponseDTO> agregarTarifa(@Valid @RequestBody TarifaRequestDTO request) {
        TarifaResponseDTO tarifa = tarifaService.agregarTarifa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarifa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTarifa(@PathVariable Long id) {
        tarifaService.borrarTarifa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaResponseDTO> modificarTarifa(@PathVariable Long id, @Valid @RequestBody TarifaRequestDTO request) {
        return ResponseEntity.ok(tarifaService.modificarTarifa(id, request));
    }
}
