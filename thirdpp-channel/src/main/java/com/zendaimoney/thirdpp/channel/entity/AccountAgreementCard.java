package com.zendaimoney.thirdpp.channel.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 协议支付银行卡表
 *
 * @author wulj
 */
public class AccountAgreementCard implements Serializable {
    private static final long serialVersionUID = 8572356380791417820L;

    private Long id;

    private String thirdType;

    private String bankCode;

    private String bankName;

    private String accountType;

    private String accountNo;

    private String accountName;

    private String accountProp;

    private String tel;

    private String cvv2;

    private String validDate;

    private String merrem;

    private String remark;

    private String agrmNo;

    private Long accountId;

    private Date createTime;

    private Date updateTime;

    private String status;

    public AccountAgreementCard(){

    }

    public AccountAgreementCard(SignMessageInfo signMessageInfo, Long accountId, String agrmNo){
        this.thirdType = signMessageInfo.getPaySysNo();
        this.bankCode = signMessageInfo.getBankCode();
        this.bankName = signMessageInfo.getBankName();
        this.accountType = signMessageInfo.getAccountType();
        this.accountName = signMessageInfo.getAccountName();
        this.accountNo = signMessageInfo.getAccountNo();
        this.accountProp = signMessageInfo.getAccountProp();
        this.tel = signMessageInfo.getTel();
        this.cvv2 = signMessageInfo.getCvv2();
        this.validDate = signMessageInfo.getValidDate();
        this.merrem = signMessageInfo.getMerrem();
        this.remark = signMessageInfo.getRemark();
        this.accountId = accountId;
        this.status = "1";      // 状态(1-正常,2-解绑) todo 新建一个枚举值
        this.agrmNo = agrmNo;       // 协议号
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThirdType() {
        return thirdType;
    }

    public void setThirdType(String thirdType) {
        this.thirdType = thirdType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountProp() {
        return accountProp;
    }

    public void setAccountProp(String accountProp) {
        this.accountProp = accountProp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getMerrem() {
        return merrem;
    }

    public void setMerrem(String merrem) {
        this.merrem = merrem;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAgrmNo() {
        return agrmNo;
    }

    public void setAgrmNo(String agrmNo) {
        this.agrmNo = agrmNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
