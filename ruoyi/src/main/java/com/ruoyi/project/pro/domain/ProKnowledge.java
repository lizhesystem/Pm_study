package com.ruoyi.project.pro.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 知识库对象 pro_knowledge
 *
 * @author lizhe
 * @date 2020-01-05
 */
public class ProKnowledge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 知识库ID */
    private Long knowledgeId;

    /** 标题 */
    @Excel(name = "标题")
    private String knowledgeTitle;

    /** 所属项目编号 */
    @Excel(name = "所属项目编号")
    private String proNum;

    /** 交易模式 */
    @Excel(name = "交易模式")
    private String tradeMode;

    /** 知识库内容 */
    @Excel(name = "知识库内容")
    private String knowledgeContent;

    /** 知识库状态（0正常 1已过期） */
    @Excel(name = "知识库状态", readConverterExp = "0=正常,1=已过期")
    private String status;

    /** 所属的项目项目编号 */
    private ProDict proDict;

    public void setKnowledgeId(Long knowledgeId)
    {
        this.knowledgeId = knowledgeId;
    }

    public Long getKnowledgeId()
    {
        return knowledgeId;
    }
    public void setKnowledgeTitle(String knowledgeTitle)
    {
        this.knowledgeTitle = knowledgeTitle;
    }

    public String getKnowledgeTitle()
    {
        return knowledgeTitle;
    }
    public void setProNum(String proNum)
    {
        this.proNum = proNum;
    }

    public String getProNum()
    {
        return proNum;
    }
    public void setTradeMode(String tradeMode)
    {
        this.tradeMode = tradeMode;
    }

    public String getTradeMode()
    {
        return tradeMode;
    }
    public void setKnowledgeContent(String knowledgeContent)
    {
        this.knowledgeContent = knowledgeContent;
    }

    public String getKnowledgeContent()
    {
        return knowledgeContent;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public ProDict getProDict() {
        return proDict;
    }

    public void setProDict(ProDict proDict) {
        this.proDict = proDict;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("knowledgeId", getKnowledgeId())
            .append("knowledgeTitle", getKnowledgeTitle())
            .append("proNum", getProNum())
            .append("tradeMode", getTradeMode())
            .append("knowledgeContent", getKnowledgeContent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
