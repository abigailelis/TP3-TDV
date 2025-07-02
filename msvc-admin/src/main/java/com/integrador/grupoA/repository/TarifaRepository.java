package com.integrador.grupoA.repository;

import com.integrador.grupoA.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.integrador.grupoA.services.dto.tarifas.tarifaResponse.TarifaResponseDTO;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Query("SELECT new com.integrador.grupoA.services.dto.tarifas.tarifaResponse.TarifaResponseDTO(" +
            "t.id, tt.tipo_tarifa, t.monto, t.fechaVigencia) " +
            "FROM Tarifa t " +
            "JOIN t.tipo_tarifa tt")
    List<TarifaResponseDTO> findAllTarifas();

    @Query("SELECT new com.integrador.grupoA.services.dto.tarifas.tarifaResponse.TarifaResponseDTO(" +
            "t.id, tt.tipo_tarifa, t.monto, t.fechaVigencia) " +
            "FROM Tarifa t " +
            "JOIN t.tipo_tarifa tt " +
            "WHERE t.id = :id")
    TarifaResponseDTO findTarifaPorId(@Param("id") Long id);

    @Query("SELECT new com.integrador.grupoA.services.dto.tarifas.tarifaResponse.TarifaResponseDTO(" +
            "t.id, tt.tipo_tarifa, t.monto, t.fechaVigencia) " +
            "FROM Tarifa t " +
            "JOIN t.tipo_tarifa tt " +
            "WHERE tt.tipo_tarifa = :tipo_tarifa")
    List<TarifaResponseDTO> findTarifasPorTipo(@Param("tipo_tarifa") String tipo_tarifa);


    @Query("SELECT new com.integrador.grupoA.services.dto.tarifas.tarifaResponse.TarifaResponseDTO(" +
            "t.id, tt.tipo_tarifa, t.monto, t.fechaVigencia) " +
            "FROM Tarifa t " +
            "JOIN t.tipo_tarifa tt " +
            "WHERE t.fechaVigencia <= CURRENT_DATE " +
            "AND tt.tipo_tarifa = :tipoTarifa " +
            "ORDER BY t.fechaVigencia DESC " +
            "LIMIT 1")
    TarifaResponseDTO findTarifaVigente(@Param("tipoTarifa") String tipoTarifa);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
            "FROM Tarifa t " +
            "JOIN t.tipo_tarifa tt " +
            "WHERE tt.tipo_tarifa = :tipoTarifa " +
            "AND t.fechaVigencia = :fecha")
    boolean existsByTipoTarifaAndFechaVigencia(@Param("tipoTarifa") String tipoTarifa, @Param("fecha") LocalDate fecha);

}
