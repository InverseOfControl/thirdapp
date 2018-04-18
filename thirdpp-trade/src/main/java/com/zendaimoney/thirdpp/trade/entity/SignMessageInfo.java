package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;

/**
 * 协议支付签约短信触发信息
 *
 * @author wulj
 */
public class SignMessageInfo implements Serializable {
    private static final long serialVersionUID = 8848844834993907271L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 交易流水号
     */
    private String reqId;

    /**
     * 交易流水号
     */
    private String tradeFlow;

    /**
     * 业务系统编码
     */
    private String bizSysNo;

    /**
     * 业务类型
     */
    private String bizTypeNo;

    /**
     * 第三方通道
     */
    private String paySysNo;

    /**
     * 信息类别编码
     */
    private String infoCategoryCode;


    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 账号类型
     */
    private String accountType;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 账号名
     */
    private String accountName;

    /**
     * 账号属性
     */
    private String accountProp;

    /**
     * 开户证件类型
     */
    private String idType;

    /**
     * 证件号
     */
    private String idNum;

    /**
     * 手机号
     */
    private String tel;

    /**
     * CVV2
     */
    private String cvv2;

    /**
     * 有效期
     */
    private String validDate;

    /**
     * 商户保留信息
     */
    private String merrem;

    /**
     * 备注
     */
    private String remark;

    /**
     * 000000交易成功 111111交易失败 222222交易处理中 333333交易异常
     */
    private String status;

    /**
     * 失败原因
     */
    private String failReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getTradeFlow() {
        return tradeFlow;
    }

    public void setTradeFlow(String tradeFlow) {
        this.tradeFlow = tradeFlow;
    }

    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo;
    }

    public String getBizTypeNo() {
        return bizTypeNo;
    }

    public void setBizTypeNo(String bizTypeNo) {
        this.bizTypeNo = bizTypeNo;
    }

    public String getPaySysNo() {
        return paySysNo;
    }

    public void setPaySysNo(String paySysNo) {
        this.paySysNo = paySysNo;
    }

    public String getInfoCategoryCode() {
        return infoCategoryCode;
    }

    public void setInfoCategoryCode(String infoCategoryCode) {
        this.infoCategoryCode = infoCategoryCode;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
