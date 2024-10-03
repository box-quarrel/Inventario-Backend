package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories", schema = "inventario-directory")
@Getter
@Setter
public class Category extends AbstractEntity{

    @Column(name = "name")
    private String categoryName;

    @Column(name = "code")
    private String categoryCode;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();

}
