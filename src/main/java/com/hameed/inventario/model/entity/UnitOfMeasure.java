package com.hameed.inventario.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unit_of_measures", schema = "inventoria-directory")
@Getter
@Setter
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String uomName;

    @Column(name = "code")
    private String uomCode;

    @Column(name = "description")
    private String description;
}
