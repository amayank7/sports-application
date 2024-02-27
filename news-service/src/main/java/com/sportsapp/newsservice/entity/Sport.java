package com.sportsapp.newsservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "sports")
@Getter
public class Sport extends AbstractEntity{
    private String name;
}
