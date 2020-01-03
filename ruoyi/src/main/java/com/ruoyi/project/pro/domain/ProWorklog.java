package com.ruoyi.project.pro.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 日报对象 pro_worklog
 *
 * @author lizhe
 * @date 2020-01-03
 */
public class ProWorklog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日报ID */
    private Long worklogId;

    /** 日报标题 */
    @Excel(name = "日报标题")
    private String worklogTitle;

    /** 日报附件 */
    @Excel(name = "日报附件")
    private String fileurl;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String proNum;

    public void setWorklogId(Long worklogId)
    {
        this.worklogId = worklogId;
    }

    public Long getWorklogId()
    {
        return worklogId;
    }
    public void setWorklogTitle(String worklogTitle)
    {
        this.worklogTitle = worklogTitle;
    }

    public String getWorklogTitle()
    {
        return worklogTitle;
    }
    public void setFileurl(String fileurl)
    {
        this.fileurl = fileurl;
    }

    public String getFileurl()
    {
        return fileurl;
    }

    public String getProNum() {
        return proNum;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("worklogId", getWorklogId())
            .append("worklogTitle", getWorklogTitle())
            .append("fileurl", getFileurl())
            .append("proname", getProNum())
            .toString();
    }
}
