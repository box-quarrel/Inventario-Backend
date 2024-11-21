package com.hameed.inventario.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(name = "last_update_by",  insertable = false)
    @LastModifiedBy
    private String lastUpdateBy;

    @Column(name = "last_update_date", insertable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;
}
