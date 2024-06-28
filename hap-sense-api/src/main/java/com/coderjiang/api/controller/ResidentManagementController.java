package com.coderjiang.api.controller;

import com.coderjiang.common.wrapper.ResponseWrapper;
import com.coderjiang.service.ResidentManagementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ResidentManagementController {

    @Resource
    private ResidentManagementService residentManagementService;

    /**
     * 查询居民信息
     *
     * @param queryType    查询类型
     * @param queryContent 查询内容
     * @param page         页码
     * @param pageSize     每页大小
     * @return 居民信息
     */
    @RequestMapping("/resident/query")
    public ResponseWrapper queryResidents(String queryType, String queryContent, int page, int pageSize) {
        return residentManagementService.queryResidents(queryType, queryContent, page, pageSize);
    }

    /**
     * 添加居民
     *
     * @param name           姓名
     * @param identityNumber 身份证号
     * @param familyNumber   家庭编号
     * @param inParkTime     在园时间
     * @param tags           标签
     * @return 添加结果
     */
    @PostMapping("/resident/add")
    public ResponseWrapper addResident(String name, String identityNumber,
                                       Integer familyNumber, String inParkTime, String tags) {
        return residentManagementService.addResident(name, identityNumber, familyNumber, inParkTime, tags);
    }

    /**
     * 编辑居民
     *
     * @param id             居民ID
     * @param name           姓名
     * @param identityNumber 身份证号
     * @param familyNumber   家庭编号
     * @param inParkTime     在园时间
     * @param tags           标签
     * @return 删除结果
     */
    @PostMapping("/resident/update")
    public ResponseWrapper updateResident(String id, String name,
                                          String identityNumber, Integer familyNumber,
                                          String inParkTime, String tags) {
        return residentManagementService.updateResident(id, name, identityNumber, familyNumber, inParkTime, tags);
    }

    /**
     * 删除居民
     *
     * @param id 居民ID
     * @return 删除结果
     */
    @PostMapping("/resident/delete")
    public ResponseWrapper deleteResident(String id) {
        return residentManagementService.deleteResident(id);
    }

}
