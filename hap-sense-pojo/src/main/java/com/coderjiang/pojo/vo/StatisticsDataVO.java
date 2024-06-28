package com.coderjiang.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDataVO {
    String date;
    int totalPopulation;
    int totalHousehold;
    int totalPlantingHousehold;
}