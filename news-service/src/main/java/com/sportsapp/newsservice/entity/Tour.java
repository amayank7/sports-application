package com.sportsapp.newsservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "tours")
@Getter
public class Tour extends AbstractEntity{

    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sportId", referencedColumnName = "id", nullable = false)
    Sport sport;

    Date startTime;

    Date endTime;
}
