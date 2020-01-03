package com.ruoyi.project.pro.mapper;

import com.ruoyi.project.pro.domain.ProWorklog;
import java.util.List;

/**
 * 日报Mapper接口
 * 
 * @author lizhe
 * @date 2020-01-03
 */
public interface ProWorklogMapper 
{
    /**
     * 查询日报
     * 
     * @param worklogId 日报ID
     * @return 日报
     */
    public ProWorklog selectProWorklogById(Long worklogId);

    /**
     * 查询日报列表
     * 
     * @param proWorklog 日报
     * @return 日报集合
     */
    public List<ProWorklog> selectProWorklogList(ProWorklog proWorklog);

    /**
     * 新增日报
     * 
     * @param proWorklog 日报
     * @return 结果
     */
    public int insertProWorklog(ProWorklog proWorklog);

    /**
     * 修改日报
     * 
     * @param proWorklog 日报
     * @return 结果
     */
    public int updateProWorklog(ProWorklog proWorklog);

    /**
     * 删除日报
     * 
     * @param worklogId 日报ID
     * @return 结果
     */
    public int deleteProWorklogById(Long worklogId);

    /**
     * 批量删除日报
     * 
     * @param worklogIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProWorklogByIds(Long[] worklogIds);
}
