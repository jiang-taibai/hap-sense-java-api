package com.coderjiang.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDataVO {
    String startDate = null;
    String endDate = null;
    boolean isPlantingHousehold = false;
}