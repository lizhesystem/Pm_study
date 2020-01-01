package com.ruoyi.project.pro.service.impl;

import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
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

    /**
     *  根据项目编号查询项目编号是否已存在
     * @param proNum
     * @return
     */
    @Override
    public String selectProProInfoByNum(String proNum) {
        int count =  proProinfoMapper.selectProProInfoByNum(proNum);
        if(count != 0){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }



}
