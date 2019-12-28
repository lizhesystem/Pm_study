package com.ruoyi.project.pro.mapper;

import com.ruoyi.project.pro.domain.ProProinfo;

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
    public ProProinfo selectProProinfoById(Long proId);

    /**
     * 查询项目列列表
     *
     * @param proProinfo 项目列
     * @return 项目列集合
     */
    public List<ProProinfo> selectProProinfoList(ProProinfo proProinfo);

    /**
     * 新增项目列
     *
     * @param proProinfo 项目列
     * @return 结果
     */
    public int insertProProinfo(ProProinfo proProinfo);

    /**
     * 修改项目列
     *
     * @param proProinfo 项目列
     * @return 结果
     */
    public int updateProProinfo(ProProinfo proProinfo);

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
