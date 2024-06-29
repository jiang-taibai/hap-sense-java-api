package com.coderjiang.pojo.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Statistics {

    @Id
    @Column(name = "date")
    private String date;

    @Column(name = "population")
    private Integer totalPopulation;

    @Column(name = "households")
    private Integer totalHousehold;

    @Column(name = "planting_households")
    private Integer totalPlantingHousehold;

}
