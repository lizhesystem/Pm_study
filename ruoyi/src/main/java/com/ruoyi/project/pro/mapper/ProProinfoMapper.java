package com.ruoyi.project.pro.mapper;

import com.ruoyi.project.pro.domain.ProInfo;

import java.util.List;

/**
 * 项目列Mapper接口
 *
 * @author ruoyi
 * @date 2019-12-28
 */
public interface ProProinfoMapper {
    /**
     * 查询项目列
     *
     * @param proId 项目列ID
     * @return 项目列
     */
    public ProInfo selectProProinfoById(Long proId);

    /**
     * 查询项目列列表
     *
     * @param proInfo 项目列
     * @return 项目列集合
     */
    public List<ProInfo> selectProProinfoList(ProInfo proInfo);

    /**
     * 新增项目列
     *
     * @param proInfo 项目列
     * @return 结果
     */
    public int insertProProinfo(ProInfo proInfo);

    /**
     * 修改项目列
     *
     * @param proInfo 项目列
     * @return 结果
     */
    public int updateProProinfo(ProInfo proInfo);

    /**
     * 删除项目列
     *
     * @param proId 项目列ID
     * @return 结果
     */
    public int deleteProProinfoById(Long proId);

    /**
     * 批量删除项目列
     *
     * @param proIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProProinfoByIds(Long[] proIds);
}
