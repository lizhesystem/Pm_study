package com.ruoyi.project.pro.mapper;

import com.ruoyi.project.pro.domain.ProKnowledge;
import java.util.List;

/**
 * 知识库Mapper接口
 * 
 * @author lizhe
 * @date 2020-01-05
 */
public interface ProKnowledgeMapper 
{
    /**
     * 查询知识库
     * 
     * @param knowledgeId 知识库ID
     * @return 知识库
     */
    public ProKnowledge selectProKnowledgeById(Long knowledgeId);

    /**
     * 查询知识库列表
     * 
     * @param proKnowledge 知识库
     * @return 知识库集合
     */
    public List<ProKnowledge> selectProKnowledgeList(ProKnowledge proKnowledge);

    /**
     * 新增知识库
     * 
     * @param proKnowledge 知识库
     * @return 结果
     */
    public int insertProKnowledge(ProKnowledge proKnowledge);

    /**
     * 修改知识库
     * 
     * @param proKnowledge 知识库
     * @return 结果
     */
    public int updateProKnowledge(ProKnowledge proKnowledge);

    /**
     * 删除知识库
     * 
     * @param knowledgeId 知识库ID
     * @return 结果
     */
    public int deleteProKnowledgeById(Long knowledgeId);

    /**
     * 批量删除知识库
     * 
     * @param knowledgeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProKnowledgeByIds(Long[] knowledgeIds);
}
