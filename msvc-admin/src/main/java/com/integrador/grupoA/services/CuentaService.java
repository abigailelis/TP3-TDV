package com.integrador.grupoA.services;

import com.integrador.grupoA.entities.Cuenta;
import com.integrador.grupoA.exceptions.custom.BusinessValidationException;
import com.integrador.grupoA.exceptions.custom.ResourceNotFoundException;
import com.integrador.grupoA.repository.CuentaRepository;
import com.integrador.grupoA.services.dto.cuentas.cuentaRequest.CuentaRequestDTO;
import com.integrador.grupoA.services.dto.cuentas.cuentaResponse.CuentaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    // Obtiene una lista de todas las cuentas
    @Transactional(readOnly = true)
    public List<CuentaResponseDTO> findAllCuentas() {
        return cuentaRepository.findAllCuentas();
    }

    // Obtiene una cuenta por su id
    @Transactional(readOnly = true)
    public CuentaResponseDTO findCuentaById(Long id) {
        CuentaResponseDTO cuenta = cuentaRepository.findCuentaById(id);
        if (cuenta == null)
            throw new ResourceNotFoundException(String.format("No existen cuentas con el ID: %d", id));
        return cuenta;
    }

    // Obtiene una lista de todas las cuentas por entidad bancaria
    @Transactional(readOnly = true)
    public List<CuentaResponseDTO> findCuentasByEntidadBancaria(String entidad_bancaria) {
        List<CuentaResponseDTO> cuentas = cuentaRepository.findCuentasByEntidadBancaria(entidad_bancaria);
        if (cuentas.isEmpty())
            throw new ResourceNotFoundException(String.format("No existen cuentas con la entidad bancaria: %s", entidad_bancaria));
        return cuentas;
    }


    // Agrega una nueva cuenta
    @Transactional
    public CuentaResponseDTO agregarCuenta(CuentaRequestDTO request) {
        validarNumeroCuenta(request.getNumero_cuenta());
        validarIdTitular(request.getId_titular());

        Cuenta cuenta = crearCuenta(request);
        cuentaRepository.save(cuenta);

        return crearCuentaDTO(cuenta);
    }

    // Elimina una cuenta existente
    @Transactional
    public CuentaResponseDTO borrarCuenta(Long id) {
        Cuenta cuenta = findById(id);
        CuentaResponseDTO cuentaDTO = crearCuentaDTO(cuenta);
        cuentaRepository.deleteById(id);
        return cuentaDTO;
    }

    // Modifica una cuenta existente
    @Transactional
    public CuentaResponseDTO modificarCuenta(Long id, CuentaRequestDTO request) {
        Cuenta cuenta = findById(id);
        if (request.getNumero_cuenta() != cuenta.getNumero_cuenta())
            validarNumeroCuenta(request.getNumero_cuenta());
        if (!request.getId_titular().equals(cuenta.getId_titular()))
            validarIdTitular(request.getId_titular());

        cuenta.setNumero_cuenta(request.getNumero_cuenta());
        cuenta.setSaldo(request.getSaldo());
        cuenta.setEntidad_bancaria(request.getEntidad_bancaria());
        cuenta.setId_titular(request.getId_titular());
        cuentaRepository.save(cuenta);

        return crearCuentaDTO(cuenta);
    }


    /**
     * ----- MÉTODOS AUXILIARES CON MANEJO DE EXCEPCIONES ------
     */

// Valída que el número de cuenta no exista, si existe lanza una excepción y corta el flujo de ejecución
    @Transactional(readOnly = true)
    public void validarNumeroCuenta(int numero_cuenta) {
        if (cuentaRepository.findCuentaByNumeroCuenta(numero_cuenta) != null)
            throw new BusinessValidationException(String.format("Ya existe el número de cuenta :%s", numero_cuenta));
    }

    // Valída que el id de titular no exista, si existe lanza una excepción y corta el flujo de ejecución.
    @Transactional(readOnly = true)
    public void validarIdTitular(Long id_titular) {
        if (cuentaRepository.findCuentaByTitular(id_titular) != null)
            throw new BusinessValidationException(String.format("Ya existe el id de titular :%s", id_titular));
    }


    // Crea una nueva cuenta de la entidad Cuenta a partir de una CuentaRequestDTO.
    public Cuenta crearCuenta(CuentaRequestDTO request) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero_cuenta(request.getNumero_cuenta());
        cuenta.setSaldo(request.getSaldo());
        cuenta.setEntidad_bancaria(request.getEntidad_bancaria());
        cuenta.setId_titular(request.getId_titular());
        return cuenta;
    }

    // Crea una nueva CuentaResponseDTO a partir de una cuenta
    public CuentaResponseDTO crearCuentaDTO(Cuenta cuenta) {
        CuentaResponseDTO cuentaDTO = new CuentaResponseDTO();
        cuentaDTO.setId(cuenta.getId());
        cuentaDTO.setNumero_cuenta(cuenta.getNumero_cuenta());
        cuentaDTO.setSaldo(cuenta.getSaldo());
        cuentaDTO.setEntidad_bancaria(cuenta.getEntidad_bancaria());
        cuentaDTO.setId_titular(cuenta.getId_titular());
        return cuentaDTO;
    }

    // Obtiene una cuenta por su id si existe y si no lanza una excepción y corta el flujo de ejecución.
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No existe una cuenta con el id :%d", id)));
    }

}
