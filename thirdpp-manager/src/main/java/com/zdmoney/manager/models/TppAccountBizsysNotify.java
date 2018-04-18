package com.zdmoney.manager.models;

import java.util.Date;

public class TppAccountBizsysNotify {
    private Short id;

    private String bizsysAccountDay;

    private String merchantNo;

    private String thirdPartyAccountReqId;

    private Short status;

    private Date createTime;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getBizsysAccountDay() {
        return bizsysAccountDay;
    }

    public void setBizsysAccountDay(String bizsysAccountDay) {
        this.bizsysAccountDay = bizsysAccountDay == null ? null : bizsysAccountDay.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getThirdPartyAccountReqId() {
        return thirdPartyAccountReqId;
    }

    public void setThirdPartyAccountReqId(String thirdPartyAccountReqId) {
        this.thirdPartyAccountReqId = thirdPartyAccountReqId == null ? null : thirdPartyAccountReqId.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}