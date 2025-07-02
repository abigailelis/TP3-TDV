package com.integrador.grupoA.services;

import com.integrador.grupoA.entities.TipoTarifa;
import com.integrador.grupoA.exceptions.custom.BusinessValidationException;
import com.integrador.grupoA.exceptions.custom.ResourceNotFoundException;
import com.integrador.grupoA.repository.TipoTarifaRepository;
import com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaRequest.TipoTarifaRequestDTO;
import com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaResponse.TipoTarifaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TipoTarifaService {

    @Autowired
    private TipoTarifaRepository tipoTarifaRepository;

    // Obtiene una lista de todos los tipos de tarifa
    @Transactional(readOnly = true)
    public List<TipoTarifaResponseDTO> findAllTipoTarifas() {
        return tipoTarifaRepository.findAllTipoTarifas();
    }

    // Obtiene un tipo de tarifa por su id
    @Transactional (readOnly = true)
    public TipoTarifaResponseDTO findTarifaPorId(Long id){
        TipoTarifaResponseDTO tipo_tarifa = tipoTarifaRepository.findTarifaPorId(id);
        if (tipo_tarifa == null)
            throw new ResourceNotFoundException(String.format("No existen tarifas con el ID: %d", id));
        return tipo_tarifa;
    }

    // Obtiene el tipo de tarifa si existe en la base de datos
    @Transactional (readOnly = true)
    public TipoTarifaResponseDTO findTarifaPorTipo(String tipo){
        TipoTarifaResponseDTO tipo_tarifa = tipoTarifaRepository.findTarifaPorTipo(tipo);
        if (tipo_tarifa == null)
            throw new ResourceNotFoundException(String.format("No existen tarifas con el tipo: %s", tipo));
        return tipo_tarifa;
    }

    // Crea un nuevo tipo de tarifa con las validaciones necesarias
    @Transactional
    public TipoTarifaResponseDTO crearTipoTarifa(TipoTarifaRequestDTO request) {
        String tipo = request.getTipo_tarifa();
        validarNombreTipoTarifa(tipo);

        if (tipoTarifaRepository.existsByTipoTarifa(tipo))
            throw new BusinessValidationException(String.format("Ya existe un tipo de tarifa con el tipo :%s", tipo));

        TipoTarifa nuevo = new TipoTarifa();
        nuevo.setTipo_tarifa(tipo);
        tipoTarifaRepository.save(nuevo);
        return convertirADTO(nuevo);
    }

    // Elimina un tipo de tarifa únicamente sin ninguna tarifa esta asociada a dicho tipo.
    @Transactional
    public void eliminarTipoTarifa(Long id) {
        TipoTarifa tipo = findById(id);

        if (!tipo.getTarifas().isEmpty())
            throw new BusinessValidationException("No se puede eliminar: hay tarifas asociadas a este tipo de tarifa.");

        tipoTarifaRepository.delete(tipo);
    }

    // Actualiza un tipo de tarifa y se modifican todas aquellas tarifas que estuvieran asociadas.
    @Transactional
    public TipoTarifaResponseDTO actualizarTipoTarifa(Long id, TipoTarifaRequestDTO request) {
        String nuevoNombre = request.getTipo_tarifa();
        validarNombreTipoTarifa(nuevoNombre);

        TipoTarifa tipo = findById(id);
        tipo.setTipo_tarifa(nuevoNombre); // Se propaga a todas las tarifas relacionadas
        tipoTarifaRepository.save(tipo);
        return convertirADTO(tipo);
    }

    // Valida que el tipo_tarifa no sea nulo ni vacío
    @Transactional (readOnly = true)
    public void validarNombreTipoTarifa (String nuevoNombre){
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty())
            throw new BusinessValidationException("El nombre del tipo de tarifa no puede estar vacío");
    }

    // Obtiene un TipoTarifa por su id
    @Transactional (readOnly = true)
    public TipoTarifa findById (Long id){
        return tipoTarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tipo de tarifa no encontrado con el id :%d", id)));
    }

    // Obtiene una TipoTarifa por su tipo_tarifa
    @Transactional(readOnly = true)
    public TipoTarifa findByTipoTarifa (String tipo_tarifa){
        return tipoTarifaRepository.findByTipoTarifa(tipo_tarifa)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tipo de tarifa no encontrado con el tipo :%s", tipo_tarifa)));
    }

    public TipoTarifaResponseDTO convertirADTO(TipoTarifa tipoTarifa){
        TipoTarifaResponseDTO tipoTarifaResponseDTO = new TipoTarifaResponseDTO();
        tipoTarifaResponseDTO.setId(tipoTarifa.getId());
        tipoTarifaResponseDTO.setTipo_tarifa(tipoTarifa.getTipo_tarifa());
        return tipoTarifaResponseDTO;
    }
}
