package com.coderjiang.service.impl;

import com.alibaba.fastjson.JSON;
import com.coderjiang.common.wrapper.ResponseWrapper;
import com.coderjiang.mapper.ResidentMapper;
import com.coderjiang.pojo.entities.Resident;
import com.coderjiang.pojo.vo.FamilyDataVO;
import com.coderjiang.pojo.vo.StatisticsDataVO;
import com.coderjiang.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private ResidentMapper residentMapper;


    /**
     * 获取所有的统计信息，包括人口总人数、人口总户数、种植户总数
     *
     * @return {'2024-06-26': {date: '2024-06-26', 'totalPopulation': 100, 'totalHousehold': 10, 'totalPlantingHousehold': 5}, ...}
     */
    @Override
    public ResponseWrapper getAllStatistics() {
        List<Resident> residents = residentMapper.selectAll();
        String minDate = null;
        HashMap<String, Integer> diffPopulation = new HashMap<>();
        HashMap<String, Integer> diffHousehold = new HashMap<>();
        HashMap<String, Integer> diffPlantingHousehold = new HashMap<>();
        HashMap<Integer, FamilyDataVO> familyTracker = new HashMap<>();

        for (Resident resident : residents) {
            Integer familyId = resident.getFamilyNumber();
            // 格式为 JSON 字符串数组：[["2020-01-01", "2020-02-01"], ["2020-03-01", "2020-04-01"]]
            String inParkTime = resident.getInParkTime();
            String[][] dateRanges = JSON.parseObject(inParkTime, String[][].class);
            boolean isPlanting = resident.getTags().contains("种植户");

            for (String[] range : dateRanges) {
                if (minDate == null || range[0].compareTo(minDate) < 0) {
                    minDate = range[0];
                }
                String startDate = range[0];
                String endDate = range[1];

                diffPopulation.put(startDate, diffPopulation.getOrDefault(startDate, 0) + 1);
                diffPopulation.put(endDate, diffPopulation.getOrDefault(endDate, 0) - 1);

                FamilyDataVO familyDataVO = familyTracker.getOrDefault(familyId, new FamilyDataVO());
                if (familyDataVO.getStartDate() == null) {
                    // 这是家庭首次出现
                    familyDataVO.setStartDate(startDate);
                    familyDataVO.setEndDate(endDate);
                }
                familyDataVO.setPlantingHousehold(familyDataVO.isPlantingHousehold() || isPlanting);
                if (startDate.compareTo(familyDataVO.getStartDate()) < 0) {
                    familyDataVO.setStartDate(startDate);
                }
                if (endDate.compareTo(familyDataVO.getEndDate()) > 0) {
                    familyDataVO.setEndDate(endDate);
                }
                familyTracker.put(familyId, familyDataVO);
            }
        }

        // 应用离开日期的调整
        for (Map.Entry<Integer, FamilyDataVO> entry : familyTracker.entrySet()) {
            FamilyDataVO familyDataVO = entry.getValue();
            diffHousehold.put(familyDataVO.getStartDate(), diffHousehold.getOrDefault(familyDataVO.getStartDate(), 0) + 1);
            diffHousehold.put(familyDataVO.getEndDate(), diffHousehold.getOrDefault(familyDataVO.getEndDate(), 0) - 1);
            if (familyDataVO.isPlantingHousehold()) {
                diffPlantingHousehold.put(familyDataVO.getStartDate(), diffPlantingHousehold.getOrDefault(familyDataVO.getStartDate(), 0) + 1);
                diffPlantingHousehold.put(familyDataVO.getEndDate(), diffPlantingHousehold.getOrDefault(familyDataVO.getEndDate(), 0) - 1);
            }
        }

        // 统计最终结果
        Map<String, StatisticsDataVO> results = new HashMap<>();
        if (minDate == null) {
            return ResponseWrapper.success().data(results);
        }
        int totalPopulation = 0, totalHousehold = 0, totalPlantingHousehold = 0;
        // 从 minDate 开始遍历到今天
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate curDate = LocalDate.parse(minDate, formatter);
        while (!curDate.isAfter(today)) {
            String date = formatter.format(curDate);
            totalPopulation += diffPopulation.getOrDefault(date, 0);
            totalHousehold += diffHousehold.getOrDefault(date, 0);
            totalPlantingHousehold += diffPlantingHousehold.getOrDefault(date, 0);
            results.put(date, new StatisticsDataVO(date, totalPopulation, totalHousehold, totalPlantingHousehold));
            curDate = curDate.plusDays(1);
        }
        return ResponseWrapper.success().data(results);
    }

}
