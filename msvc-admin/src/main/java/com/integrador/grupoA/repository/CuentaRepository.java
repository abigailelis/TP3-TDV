package com.integrador.grupoA.repository;

import com.integrador.grupoA.entities.Cuenta;
import com.integrador.grupoA.services.dto.cuentas.cuentaResponse.CuentaResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("SELECT new com.integrador.grupoA.services.dto.cuentas.cuentaResponse.CuentaResponseDTO (" +
            "c.id, c.entidad_bancaria, c.numero_cuenta, c.saldo, c.id_titular) " +
            "FROM Cuenta c ")
    List<CuentaResponseDTO> findAllCuentas();

    @Query("SELECT new com.integrador.grupoA.services.dto.cuentas.cuentaResponse.CuentaResponseDTO (" +
            "c.id, c.entidad_bancaria, c.numero_cuenta, c.saldo, c.id_titular) " +
            "FROM Cuenta c " +
            "WHERE c.id = :id ")
    CuentaResponseDTO findCuentaById(@Param("id")Long id);

    @Query("SELECT new com.integrador.grupoA.services.dto.cuentas.cuentaResponse.CuentaResponseDTO (" +
            "c.id, c.entidad_bancaria, c.numero_cuenta, c.saldo, c.id_titular) " +
            "FROM Cuenta c " +
            "WHERE c.entidad_bancaria = :entidad_bancaria")
    List<CuentaResponseDTO> findCuentasByEntidadBancaria(@Param("entidad_bancaria")String entidad_bancaria);

    @Query("SELECT new com.integrador.grupoA.services.dto.cuentas.cuentaResponse.CuentaResponseDTO (" +
            "c.id, c.entidad_bancaria, c.numero_cuenta, c.saldo, c.id_titular) " +
            "FROM Cuenta c " +
            "WHERE c.numero_cuenta = :numero_cuenta")
    CuentaResponseDTO findCuentaByNumeroCuenta(@Param("numero_cuenta")int numero_cuenta);

    @Query("SELECT new com.integrador.grupoA.services.dto.cuentas.cuentaResponse.CuentaResponseDTO (" +
            "c.id, c.entidad_bancaria, c.numero_cuenta, c.saldo, c.id_titular) " +
            "FROM Cuenta c " +
            "WHERE c.id_titular = :id_titular")
    CuentaResponseDTO findCuentaByTitular(@Param("id_titular")Long id_titular);

}
