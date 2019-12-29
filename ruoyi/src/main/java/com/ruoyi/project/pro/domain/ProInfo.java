package com.ruoyi.project.pro.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 项目列对象 pro_proinfo
 *
 * @author ruoyi
 * @date 2019-12-28
 */
public class ProInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    private Long proId;

    /**
     * 项目编号
     */
    @Excel(name = "项目编号")
    private String proNum;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String proName;

    /**
     * 项目运营平台
     */
    @Excel(name = "项目运营平台")
    private String operator;

    /**
     * 项目交易模式：根据字典表获取
     */
    @Excel(name = "项目交易模式：根据字典表获取")
    private String tradeMode;

    /**
     * 银行编号：根据字典获取
     */
    @Excel(name = "银行编号：根据字典获取")
    private String bankId;

    /**
     * 银行名称
     */
    @Excel(name = "银行名称")
    private String bankName;

    /**
     * 客户联系人姓名
     */
    @Excel(name = "客户联系人姓名")
    private String custname;

    /**
     * 客户联系人手机
     */
    @Excel(name = "客户联系人手机")
    private String custphone;

    /**
     * 客户联系人邮箱
     */
    @Excel(name = "客户联系人邮箱")
    private String custemail;

    /**
     * 所属项目经理
     */
    @Excel(name = "所属项目经理")
    private String proManager;

    /**
     * 系统部署人员
     */
    @Excel(name = "系统部署人员")
    private Long insetallId;

    /**
     * 系统培训人人员
     */
    @Excel(name = "系统培训人人员")
    private Long trainId;

    /**
     * 验收状态：1验收 2未验收
     */
    @Excel(name = "验收状态：1验收 2未验收")
    private String acceptanceStatus;

    /**
     * 运行状态：1正常 2暂停
     */
    @Excel(name = "运行状态：1正常 2暂停")
    private String proStatus;

    /**
     * 部署时间
     */
    @Excel(name = "部署时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date insertTime;

    /**
     * 培训时间
     */
    @Excel(name = "培训时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date trainTime;

    /**
     * 上线时间
     */
    @Excel(name = "上线时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date businessTime;

    /**
     * 终止时间
     */
    @Excel(name = "终止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date terminationTime;

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public Long getProId() {
        return proId;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    public String getProNum() {
        return proNum;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProName() {
        return proName;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }

    public String getTradeMode() {
        return tradeMode;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustphone(String custphone) {
        this.custphone = custphone;
    }

    public String getCustphone() {
        return custphone;
    }

    public void setCustemail(String custemail) {
        this.custemail = custemail;
    }

    public String getCustemail() {
        return custemail;
    }

    public void setProManager(String proManager) {
        this.proManager = proManager;
    }

    public String getProManager() {
        return proManager;
    }

    public void setInsetallId(Long insetallId) {
        this.insetallId = insetallId;
    }

    public Long getInsetallId() {
        return insetallId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setAcceptanceStatus(String acceptanceStatus) {
        this.acceptanceStatus = acceptanceStatus;
    }

    public String getAcceptanceStatus() {
        return acceptanceStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setTrainTime(Date trainTime) {
        this.trainTime = trainTime;
    }

    public Date getTrainTime() {
        return trainTime;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public Date getBusinessTime() {
        return businessTime;
    }

    public void setTerminationTime(Date terminationTime) {
        this.terminationTime = terminationTime;
    }

    public Date getTerminationTime() {
        return terminationTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("proId", getProId())
                .append("proNum", getProNum())
                .append("proName", getProName())
                .append("operator", getOperator())
                .append("tradeMode", getTradeMode())
                .append("bankId", getBankId())
                .append("bankName", getBankName())
                .append("custname", getCustname())
                .append("custphone", getCustphone())
                .append("custemail", getCustemail())
                .append("proManager", getProManager())
                .append("insetallId", getInsetallId())
                .append("trainId", getTrainId())
                .append("acceptanceStatus", getAcceptanceStatus())
                .append("proStatus", getProStatus())
                .append("insertTime", getInsertTime())
                .append("trainTime", getTrainTime())
                .append("businessTime", getBusinessTime())
                .append("terminationTime", getTerminationTime())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
