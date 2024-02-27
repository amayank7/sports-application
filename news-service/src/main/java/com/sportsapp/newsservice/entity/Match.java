package com.sportsapp.newsservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "matches")
@Getter
public class Match extends AbstractEntity{
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourId", referencedColumnName = "id", nullable = false)
    private Tour tour;

    private String format;

    private Date startTime;

    private Date endTime;
}
