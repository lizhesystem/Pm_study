package com.ruoyi.project.pro.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pro.mapper.ProProinfoMapper;
import com.ruoyi.project.pro.domain.ProInfo;
import com.ruoyi.project.pro.service.IProProinfoService;

/**
 * 项目列Service业务层处理
 *
 * @author ruoyi
 * @date 2019-12-28
 */
@Service
public class ProProinfoServiceImpl implements IProProinfoService {
    @Autowired
    private ProProinfoMapper proProinfoMapper;

    /**
     * 查询项目列
     *
     * @param proId 项目列ID
     * @return 项目列
     */
    @Override
    public ProInfo selectProProinfoById(Long proId) {
        return proProinfoMapper.selectProProinfoById(proId);
    }

    /**
     * 查询项目列列表
     *
     * @param proInfo 项目列
     * @return 项目列
     */
    @Override
    public List<ProInfo> selectProProinfoList(ProInfo proInfo) {
        return proProinfoMapper.selectProProinfoList(proInfo);
    }

    /**
     * 新增项目列
     *
     * @param proInfo 项目列
     * @return 结果
     */
    @Override
    public int insertProProinfo(ProInfo proInfo) {
        proInfo.setCreateTime(DateUtils.getNowDate());
        return proProinfoMapper.insertProProinfo(proInfo);
    }

    /**
     * 修改项目列
     *
     * @param proInfo 项目列
     * @return 结果
     */
    @Override
    public int updateProProinfo(ProInfo proInfo) {
        proInfo.setUpdateTime(DateUtils.getNowDate());
        return proProinfoMapper.updateProProinfo(proInfo);
    }

    /**
     * 批量删除项目列
     *
     * @param proIds 需要删除的项目列ID
     * @return 结果
     */
    @Override
    public int deleteProProinfoByIds(Long[] proIds) {
        return proProinfoMapper.deleteProProinfoByIds(proIds);
    }

    /**
     * 删除项目列信息
     *
     * @param proId 项目列ID
     * @return 结果
     */
    @Override
    public int deleteProProinfoById(Long proId) {
        return proProinfoMapper.deleteProProinfoById(proId);
    }
}
