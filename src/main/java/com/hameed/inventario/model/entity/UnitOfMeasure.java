package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "unit_of_measures", schema = "inventario-directory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "primaryUom", fetch = FetchType.LAZY)
//    @JsonIgnore
    private Set<Product> products;
}
