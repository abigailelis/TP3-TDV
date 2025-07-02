package com.integrador.grupoA.repository;


import com.integrador.grupoA.entities.TipoTarifa;
import com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaResponse.TipoTarifaResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoTarifaRepository extends JpaRepository<TipoTarifa, Long> {

    @Query("SELECT tt FROM TipoTarifa tt WHERE tt.tipo_tarifa = :tipo_tarifa")
    Optional<TipoTarifa> findByTipoTarifa(@Param("tipo_tarifa") String tipo_tarifa);


    @Query("SELECT CASE WHEN COUNT(tt) > 0 THEN true ELSE false END " +
            "FROM TipoTarifa tt WHERE tt.tipo_tarifa = :tipoTarifa")
    boolean existsByTipoTarifa(@Param("tipoTarifa") String tipoTarifa);


    @Query("SELECT new com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaResponse.TipoTarifaResponseDTO(" +
            "tt.id, tt.tipo_tarifa) " +
            "FROM TipoTarifa tt " )
    List<TipoTarifaResponseDTO> findAllTipoTarifas();

    @Query("SELECT new com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaResponse.TipoTarifaResponseDTO(" +
            "tt.id, tt.tipo_tarifa) " +
            "FROM TipoTarifa tt " +
            "WHERE tt.id = :id")
    TipoTarifaResponseDTO findTarifaPorId(@Param("id") Long id);

    @Query("SELECT new com.integrador.grupoA.services.dto.tipoTarifas.tipoTarifaResponse.TipoTarifaResponseDTO(" +
            "tt.id, tt.tipo_tarifa) " +
            "FROM TipoTarifa tt " +
            "WHERE tt.tipo_tarifa = :tipo_tarifa")
    TipoTarifaResponseDTO findTarifaPorTipo(@Param("tipo_tarifa") String tipo_tarifa);

}
