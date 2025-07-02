package com.integrador.grupoA.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipoTarifa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoTarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    @NotBlank(message = "El campo tipo_tarifa no puede estar vacío")
    private String tipo_tarifa;

    // Restricción de borrado: no se permite eliminar si tiene elementos vinculados
    // Restricción de modificación: el cambio se propaga a todos los "hijos"
    @OneToMany (mappedBy = "tipo_tarifa", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private List<Tarifa> tarifas = new ArrayList<>();

}