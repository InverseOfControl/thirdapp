package com.zendaimoney.thirdpp.trade.pub.vo.req.biz;

/**
 * 协议支付签约短信触发
 *
 * @author wulj
 */
public class SignMsgReqVo extends BizReqVo {
    private static final long serialVersionUID = -8818618384236727228L;

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
     * CVV2(信用卡时必填)
     */
    private String cvv2;

    /**
     * 有效期(信用卡时必填) MMYY(信用卡上的两位月两位年)
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
}
