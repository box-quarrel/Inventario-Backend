package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Entity
@Table(name = "unit_of_measures", schema = "inventario-directory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasure extends AbstractEntity{

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Unit of Measure Name cannot be blank")
    @Length(min = 2, max = 50, message = "Unit of Measure Name Length must be between 2 and 50")
    private String uomName;

    @Column(name = "code", nullable = false)
    @NotBlank
    @Length(min = 2, max = 20, message = "Unit of Measure Code Length must be between 2 and 20")
    private String uomCode;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "primaryUom", fetch = FetchType.LAZY)
//    @JsonIgnore
    private Set<Product> products;
}
