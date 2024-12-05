package com.hameed.inventario.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers", schema = "inventario-directory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends AbstractEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Length(min = 2, max = 50, message = "Customer Name Length must be between 2 and 50")
    private String customerName;

    @Column(name = "email")
    @Email(message = "Email must be valid")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Sale> sales = new HashSet<>();

}
