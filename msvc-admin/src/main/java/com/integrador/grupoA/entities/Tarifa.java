package com.integrador.grupoA.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "tarifa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    @Positive(message = "El monto debe ser positivo")
    private double monto;

    @Column (nullable = false)
    private LocalDate fechaVigencia;

    //Relación uno a uno con la tabla tipo_tarifa
    @ManyToOne
    @JoinColumn (name = "tipo_tarifa_id")
    @JsonBackReference
    private TipoTarifa tipo_tarifa;
}