package com.coderjiang.service.impl;

import com.coderjiang.common.wrapper.ResponseWrapper;
import com.coderjiang.mapper.ResidentMapper;
import com.coderjiang.pojo.entities.Resident;
import com.coderjiang.service.ResidentManagementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class ResidentManagementServiceImpl implements ResidentManagementService {

    @Resource
    private ResidentMapper residentMapper;

    /**
     * 查询居民信息
     *
     * @param queryType    查询类型
     * @param queryContent 查询内容
     * @param page         页码
     * @param pageSize     每页大小
     * @return 居民信息
     */
    @Override
    public ResponseWrapper queryResidents(String queryType, String queryContent, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Resident> residents;
        Example example = new Example(Resident.class);
        Example.Criteria criteria = example.createCriteria();
        // 根据查询类型选择不同的查询方法
        switch (queryType.toLowerCase()) {
            case "name":
                criteria.andLike("name", "%" + queryContent + "%");
                residents = residentMapper.selectByExample(example);
                break;
            case "identity_number":
                criteria.andLike("identityNumber", "%" + queryContent + "%");
                residents = residentMapper.selectByExample(example);
                break;
            case "family_number":
                criteria.andEqualTo("familyNumber", queryContent);
                residents = residentMapper.selectByExample(example);
                break;
            case "":
                residents = residentMapper.selectAll();
                break;
            default:
                // 如果查询类型不支持，则返回空列表或错误信息
                return ResponseWrapper.failed().message("查询类型不支持");
        }

        // 获取分页信息
        PageInfo<Resident> pageInfo = new PageInfo<>(residents);
        return ResponseWrapper.success().data(pageInfo).message("查询成功！");
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
    @Override
    public ResponseWrapper addResident(String name, String identityNumber, Integer familyNumber, String inParkTime, String tags) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 为了加快完成进度，暂且不检查错误
        // 可能存在的情况有：身份证号重复、在园时间格式错误、标签格式错误
        Resident resident = new Resident(uuid, identityNumber, name, familyNumber, inParkTime, tags);
        residentMapper.insert(resident);
        return ResponseWrapper.success().message("添加居民成功！").data(resident);
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
    @Override
    public ResponseWrapper updateResident(String id, String name, String identityNumber, Integer familyNumber, String inParkTime, String tags) {
        // 为了加快完成进度，暂且不检查错误
        // 可能存在的情况有：ID不存在、身份证号重复、在园时间格式错误、标签格式错误
        Resident resident = new Resident(id, identityNumber, name, familyNumber, inParkTime, tags);
        residentMapper.updateByPrimaryKey(resident);
        return ResponseWrapper.success().message("居民信息更新成功！").data(resident);
    }

    /**
     * 删除居民
     *
     * @param id 居民ID
     * @return 删除结果
     */
    @Override
    public ResponseWrapper deleteResident(String id) {
        residentMapper.deleteByPrimaryKey(id);
        return ResponseWrapper.success().message("删除居民成功！");
    }
}
