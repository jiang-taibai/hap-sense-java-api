package com.coderjiang.service;

import com.coderjiang.common.wrapper.ResponseWrapper;
import org.springframework.stereotype.Service;

public interface StatisticsService {

    /**
     * 获取所有的统计信息，包括人口总人数、人口总户数、种植户总数
     * @return {'2024-06-26': {date: '2024-06-26', 'totalPopulation': 100, 'totalHousehold': 10, 'totalPlantingHousehold': 5}, ...}
     */
    ResponseWrapper getAllStatistics();

}
