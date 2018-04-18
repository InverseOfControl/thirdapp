package com.zendaimoney.thirdpp.channel.dto.req.allinpay.sign.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReqBody implements Serializable {

    @XmlTransient
    private static final long serialVersionUID = -6178279714582294994L;

    @XmlElement(name = "MERCHANT_ID")
    private String merchantId;

    @XmlElement(name = "BANK_CODE")
    private String bankCode;

    @XmlElement(name = "ACCOUNT_TYPE")
    private String accountType;

    @XmlElement(name = "ACCOUNT_NO")
    private String accountNo;

    @XmlElement(name = "ACCOUNT_NAME")
    private String accountName;

    @XmlElement(name = "ACCOUNT_PROP")
    private String accountProp;

    @XmlElement(name = "ID_TYPE")
    private String idType;

    @XmlElement(name = "ID")
    private String idNum;

    @XmlElement(name = "TEL")
    private String tel;

    @XmlElement(name = "CVV2")
    private String cvv2;

    @XmlElement(name = "VAILDDATE")
    private String validDate;

    @XmlElement(name = "MERREM")
    private String merrem;

    @XmlElement(name = "REMARK")
    private String remark;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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
