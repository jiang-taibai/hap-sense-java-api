package com.coderjiang.service.impl;

import com.coderjiang.common.wrapper.ResponseWrapper;
import com.coderjiang.mapper.StatisticsMapper;
import com.coderjiang.pojo.entities.Statistics;
import com.coderjiang.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "StatisticsServiceImplIndependentTable")
public class StatisticsServiceImplIndependentTable implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;


    /**
     * 获取所有的统计信息，包括人口总人数、人口总户数、种植户总数
     *
     * @return {'2024-06-26': {date: '2024-06-26', 'totalPopulation': 100, 'totalHousehold': 10, 'totalPlantingHousehold': 5}, ...}
     */
    @Override
    public ResponseWrapper getAllStatistics() {
        List<Statistics> res = statisticsMapper.selectAll();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        res = res.stream().filter(i -> i.getDate().compareTo(formattedDate) <= 0).collect(Collectors.toList());
        res.sort(Comparator.comparing(Statistics::getDate));
        HashMap<String, Statistics> map = new HashMap<>();
        for (Statistics i : res) {
            map.put(i.getDate(), i);
        }
        return ResponseWrapper.success().data(map).message("获取数据成功！");
    }

}
