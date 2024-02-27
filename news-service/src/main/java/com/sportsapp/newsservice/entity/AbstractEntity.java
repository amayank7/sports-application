package com.sportsapp.newsservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;


@Getter
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "createdAt", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false)
    protected Date createdAt;

    @Column(name = "recUpdatedAt", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false)
    protected Date recUpdatedAt;

    @Builder.Default
    @Column(columnDefinition = "bit(1) default 1")
    protected boolean status = true;

}
