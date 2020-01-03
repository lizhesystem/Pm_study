package com.ruoyi.project.pro.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pro.mapper.ProWorklogMapper;
import com.ruoyi.project.pro.domain.ProWorklog;
import com.ruoyi.project.pro.service.IProWorklogService;

/**
 * 日报Service业务层处理
 * 
 * @author lizhe
 * @date 2020-01-03
 */
@Service
public class ProWorklogServiceImpl implements IProWorklogService 
{
    @Autowired
    private ProWorklogMapper proWorklogMapper;

    /**
     * 查询日报
     * 
     * @param worklogId 日报ID
     * @return 日报
     */
    @Override
    public ProWorklog selectProWorklogById(Long worklogId)
    {
        return proWorklogMapper.selectProWorklogById(worklogId);
    }

    /**
     * 查询日报列表
     * 
     * @param proWorklog 日报
     * @return 日报
     */
    @Override
    public List<ProWorklog> selectProWorklogList(ProWorklog proWorklog)
    {
        return proWorklogMapper.selectProWorklogList(proWorklog);
    }

    /**
     * 新增日报
     * 
     * @param proWorklog 日报
     * @return 结果
     */
    @Override
    public int insertProWorklog(ProWorklog proWorklog)
    {
        return proWorklogMapper.insertProWorklog(proWorklog);
    }

    /**
     * 修改日报
     * 
     * @param proWorklog 日报
     * @return 结果
     */
    @Override
    public int updateProWorklog(ProWorklog proWorklog)
    {
        return proWorklogMapper.updateProWorklog(proWorklog);
    }

    /**
     * 批量删除日报
     * 
     * @param worklogIds 需要删除的日报ID
     * @return 结果
     */
    @Override
    public int deleteProWorklogByIds(Long[] worklogIds)
    {
        return proWorklogMapper.deleteProWorklogByIds(worklogIds);
    }

    /**
     * 删除日报信息
     * 
     * @param worklogId 日报ID
     * @return 结果
     */
    @Override
    public int deleteProWorklogById(Long worklogId)
    {
        return proWorklogMapper.deleteProWorklogById(worklogId);
    }
}
