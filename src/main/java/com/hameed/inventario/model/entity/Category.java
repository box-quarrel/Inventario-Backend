package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories", schema = "inventario_directory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends AbstractEntity{

    @Column(name = "name", nullable = false)
    @NotBlank
    @Length(min = 2, max = 50, message = "Category Name Length must be between 2 and 50")
    private String categoryName;


    @Column(name = "code", nullable = false)
    @NotBlank
    @Length(min = 2, max = 50, message = "Category Code Length must be between 2 and 50")
    private String categoryCode;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
//    @JsonManagedReference
    private Set<Product> products = new HashSet<>();

}
