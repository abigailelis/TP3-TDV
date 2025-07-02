package com.integrador.grupoA.services;

import com.integrador.grupoA.entities.Tarifa;
import com.integrador.grupoA.entities.TipoTarifa;
import com.integrador.grupoA.exceptions.custom.BusinessValidationException;
import com.integrador.grupoA.exceptions.custom.ResourceNotFoundException;
import com.integrador.grupoA.repository.TarifaRepository;
import com.integrador.grupoA.services.dto.tarifas.tarifaRequest.TarifaRequestDTO;
import com.integrador.grupoA.services.dto.tarifas.tarifaResponse.TarifaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarifaService {

    @Autowired
    TarifaRepository tarifaRepository;

    @Autowired
    TipoTarifaService tipoTarifaService;

    //Obtiene una lista de todas las tarifas
    @Transactional (readOnly = true)
    public List<TarifaResponseDTO> obtenerTarifas(){
        return tarifaRepository.findAllTarifas();
    }

    // Obtiene una TarifaResponseDTO por su id
    @Transactional(readOnly = true)
    public TarifaResponseDTO findTarifaPorId(Long id){
        TarifaResponseDTO tarifa = tarifaRepository.findTarifaPorId(id);
        if (tarifa == null)
            throw new ResourceNotFoundException(String.format("No existen tarifas con el ID: %d", id));
        return tarifa;
    }

    // Obtiene una lista de TarifaResponseDTO por su tipo
    @Transactional (readOnly = true)
    public List<TarifaResponseDTO> obtenerTarifasPorTipo(String tipoTarifa){
        List<TarifaResponseDTO> tarifas = tarifaRepository.findTarifasPorTipo(tipoTarifa);
        if (tarifas.isEmpty())
            throw new ResourceNotFoundException(String.format("No existen tarifas con el tipo: %s", tipoTarifa));
        return tarifas;
    }

    // Agrega una nueva tarifa
    @Transactional
    public TarifaResponseDTO agregarTarifa(TarifaRequestDTO tarifaRequestDTO){
        validarMonto(tarifaRequestDTO.getMonto());
        validarFecha(tarifaRequestDTO.getFechaVigencia(), tarifaRequestDTO.getTipo_tarifa());
        Tarifa tarifa = convertirATarifa(tarifaRequestDTO);
        tarifaRepository.save(tarifa);
        return convertirADTO(tarifa);
    }

    // Elimina una tarifa por su id
    @Transactional
    public void borrarTarifa(Long id){
        findById(id);
        tarifaRepository.deleteById(id);
    }

    // Modifica una tarifa por su id
    @Transactional
    public TarifaResponseDTO modificarTarifa(Long id, TarifaRequestDTO tarifaRequestDTO) {
        Tarifa tarifaEncontrada = findById(id);
        validarId(id, tarifaEncontrada);
        validarMonto(tarifaRequestDTO.getMonto());
        validarFecha(tarifaRequestDTO.getFechaVigencia(), tarifaRequestDTO.getTipo_tarifa());

        String tipo_tarifa_request = tarifaRequestDTO.getTipo_tarifa();
        String tipo_tarifa_actual= tarifaEncontrada.getTipo_tarifa().getTipo_tarifa();

        // En caso de que quiera modificar el tipo_tarifa debe verificar que exista en la tabla TipoTarifa
        if (!tipo_tarifa_actual.equals(tipo_tarifa_request)) {
            TipoTarifa tipoTarifa = tipoTarifaService.findByTipoTarifa(tipo_tarifa_request);
            tarifaEncontrada.setTipo_tarifa(tipoTarifa);
        }

        tarifaEncontrada.setMonto(tarifaRequestDTO.getMonto());
        tarifaEncontrada.setFechaVigencia(tarifaRequestDTO.getFechaVigencia());
        tarifaRepository.save(tarifaEncontrada);

        return convertirADTO(tarifaEncontrada);
    }


    /** ----- MÉTODOS AUXILIARES CON MANEJO DE EXCEPCIONES ---- */

    // Verifica que el monto sea positivo y no nulo
    private void validarMonto(double monto){
        if (monto <= 0.0)
            throw new BusinessValidationException("El monto debe ser positivo");
    }

    // Verifica que no se intente cambiar el ID
    private void validarId(Long id, Tarifa tarifa){
        if (!id.equals(tarifa.getId()))
            throw new BusinessValidationException("El ID de la tarifa no puede ser modificado");
    }

    // Valida la fecha de vigencia
    public void validarFecha(LocalDate fecha, String tipo_tarifa) {

        // Valída que la fecha sea posterior a la última fecha vigente
        TarifaResponseDTO tarifaVigente = tarifaRepository.findTarifaVigente(tipo_tarifa);
        if (tarifaVigente != null){
            LocalDate fechaVigente = tarifaVigente.getFechaVigencia();
            if (fecha.isBefore(fechaVigente) || fecha.isEqual(fechaVigente))
                throw new BusinessValidationException(String.format("La fecha de vigencia debe ser posterior a la última fecha vigente (%s)", fechaVigente));
        }

        // Valída que no exista otra tarifa con la misma fecha para ese tipo
        if (tarifaRepository.existsByTipoTarifaAndFechaVigencia(tipo_tarifa, fecha))
            throw new BusinessValidationException(String.format("Ya existe una tarifa del tipo %s para la fecha %s", tipo_tarifa, fecha));
    }

    // Obtiene una tarifa por su id
    @Transactional(readOnly = true)
    public Tarifa findById(Long id){
        return tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tarifa no encontrada con ID: %d", id)));
    }

    // Convierte una tarifa a TarifaResponseDTO.
    private TarifaResponseDTO convertirADTO(Tarifa tarifa){
        TarifaResponseDTO tarifaResponseDTO = new TarifaResponseDTO();
        tarifaResponseDTO.setId(tarifa.getId());
        tarifaResponseDTO.setTipo_tarifa(tarifa.getTipo_tarifa().getTipo_tarifa());
        tarifaResponseDTO.setMonto(tarifa.getMonto());
        tarifaResponseDTO.setFechaVigencia(tarifa.getFechaVigencia());
        return tarifaResponseDTO;
    }

    // Convierte una TarifaResponseDTO a una tarifa.
    @Transactional
    public Tarifa convertirATarifa(TarifaRequestDTO tarifaRequestDTO){
        Tarifa tarifa = new Tarifa();
        TipoTarifa tipoTarifa = tipoTarifaService.findByTipoTarifa(tarifaRequestDTO.getTipo_tarifa());
        tarifa.setTipo_tarifa(tipoTarifa);
        tarifa.setMonto(tarifaRequestDTO.getMonto());
        tarifa.setFechaVigencia(tarifaRequestDTO.getFechaVigencia());
        return tarifa;
    }
}
