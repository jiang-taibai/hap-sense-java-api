package com.coderjiang.api.controller;

import com.coderjiang.common.wrapper.ResponseWrapper;
import com.coderjiang.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StatisticsController {

//    @Resource(name = "StatisticsServiceImpl")
    @Resource(name = "StatisticsServiceImplIndependentTable")
    private StatisticsService statisticsService;

    /**
     * 获取所有的统计信息，包括人口总人数、人口总户数、种植户总数
     *
     * @return {'2024-06-26': {date: '2024-06-26', 'totalPopulation': 100, 'totalHousehold': 10, 'totalPlantingHousehold': 5}, ...}
     */
    @GetMapping("/statistics/all")
    public ResponseWrapper getAllStatistics() {
        return statisticsService.getAllStatistics();
    }

}
