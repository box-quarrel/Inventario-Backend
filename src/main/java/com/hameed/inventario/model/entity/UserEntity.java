package com.hameed.inventario.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "inventario_directory")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity{
    @Id
    @Column(name = "username", unique=true, nullable = false)
    @Length(min = 2, max = 50, message = "username Length must be between 2 and 50")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled")
    private boolean enabled = true;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Authority> authorities;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime creationDate;


    public void addAuthority(Authority authority) {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(authority);
        authority.setUserEntity(this);
    }

}
