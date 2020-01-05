package com.ruoyi.project.pro.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pro.mapper.ProKnowledgeMapper;
import com.ruoyi.project.pro.domain.ProKnowledge;
import com.ruoyi.project.pro.service.IProKnowledgeService;

/**
 * 知识库Service业务层处理
 * 
 * @author lizhe
 * @date 2020-01-05
 */
@Service
public class ProKnowledgeServiceImpl implements IProKnowledgeService 
{
    @Autowired
    private ProKnowledgeMapper proKnowledgeMapper;

    /**
     * 查询知识库
     * 
     * @param knowledgeId 知识库ID
     * @return 知识库
     */
    @Override
    public ProKnowledge selectProKnowledgeById(Long knowledgeId)
    {
        return proKnowledgeMapper.selectProKnowledgeById(knowledgeId);
    }

    /**
     * 查询知识库列表
     * 
     * @param proKnowledge 知识库
     * @return 知识库
     */
    @Override
    public List<ProKnowledge> selectProKnowledgeList(ProKnowledge proKnowledge)
    {
        return proKnowledgeMapper.selectProKnowledgeList(proKnowledge);
    }

    /**
     * 新增知识库
     * 
     * @param proKnowledge 知识库
     * @return 结果
     */
    @Override
    public int insertProKnowledge(ProKnowledge proKnowledge)
    {
        proKnowledge.setCreateTime(DateUtils.getNowDate());
        return proKnowledgeMapper.insertProKnowledge(proKnowledge);
    }

    /**
     * 修改知识库
     * 
     * @param proKnowledge 知识库
     * @return 结果
     */
    @Override
    public int updateProKnowledge(ProKnowledge proKnowledge)
    {
        proKnowledge.setUpdateTime(DateUtils.getNowDate());
        return proKnowledgeMapper.updateProKnowledge(proKnowledge);
    }

    /**
     * 批量删除知识库
     * 
     * @param knowledgeIds 需要删除的知识库ID
     * @return 结果
     */
    @Override
    public int deleteProKnowledgeByIds(Long[] knowledgeIds)
    {
        return proKnowledgeMapper.deleteProKnowledgeByIds(knowledgeIds);
    }

    /**
     * 删除知识库信息
     * 
     * @param knowledgeId 知识库ID
     * @return 结果
     */
    @Override
    public int deleteProKnowledgeById(Long knowledgeId)
    {
        return proKnowledgeMapper.deleteProKnowledgeById(knowledgeId);
    }
}
