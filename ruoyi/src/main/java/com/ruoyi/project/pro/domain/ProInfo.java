package com.ruoyi.project.pro.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
     * 运维人员
     */
    @Excel(name = "运维人员")
    private String workName;

    /**
     * 系统部署人员
     */
    @Excel(name = "系统部署人员")
    private String insetallName;

    /**
     * 系统培训人人员
     */
    @Excel(name = "系统培训人人员")
    private String trainName;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insetallTime;

    /**
     * 培训时间
     */
    @Excel(name = "培训时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date trainTime;

    /**
     * 上线时间
     */
    @Excel(name = "上线时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date businessTime;

    /**
     * 验收时间
     */
    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date acceptanceTime;

    /**
     * 终止时间
     */
    @Excel(name = "终止时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
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

    @NotBlank(message = "项目编号不能为空")
    @Size(min = 0, max = 10, message = "项目编号长度不能超过10个字符")
    public String getProNum() {
        return proNum;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    @NotBlank(message = "项目名称不能为空")
    @Size(min = 0, max = 20, message = "项目编号长度不能超过20个字符")
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

    @NotBlank(message = "维护人员不能为空")
    @Size(min = 0, max = 10, message = "维护人员姓名长度不能超过10个字符")
    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getInsetallName() {
        return insetallName;
    }

    public void setInsetallName(String insetallName) {
        this.insetallName = insetallName;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
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

    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    public String getCustphone() {
        return custphone;
    }

    public void setCustemail(String custemail) {
        this.custemail = custemail;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getCustemail() {
        return custemail;
    }

    public void setProManager(String proManager) {
        this.proManager = proManager;
    }

    @NotBlank(message = "项目经理不能为空")
    @Size(min = 0, max = 10, message = "项目经理姓名长度不能超过10个字符")
    public String getProManager() {
        return proManager;
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

    public Date getInsetallTime() {
        return insetallTime;
    }

    public void setInsetallTime(Date insetallTime) {
        this.insetallTime = insetallTime;
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

    public Date getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(Date acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    @Override
    public String toString() {
        return "ProInfo{" +
                "proId=" + proId +
                ", proNum='" + proNum + '\'' +
                ", proName='" + proName + '\'' +
                ", operator='" + operator + '\'' +
                ", tradeMode='" + tradeMode + '\'' +
                ", bankId='" + bankId + '\'' +
                ", custname='" + custname + '\'' +
                ", custphone='" + custphone + '\'' +
                ", custemail='" + custemail + '\'' +
                ", proManager='" + proManager + '\'' +
                ", workName='" + workName + '\'' +
                ", insetallName='" + insetallName + '\'' +
                ", trainName='" + trainName + '\'' +
                ", acceptanceStatus='" + acceptanceStatus + '\'' +
                ", proStatus='" + proStatus + '\'' +
                ", insetallTime=" + insetallTime +
                ", trainTime=" + trainTime +
                ", businessTime=" + businessTime +
                ", acceptanceTime=" + acceptanceTime +
                ", terminationTime=" + terminationTime +
                '}';
    }
}
