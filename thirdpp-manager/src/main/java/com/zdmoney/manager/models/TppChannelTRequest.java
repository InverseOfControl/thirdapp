package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

public class TppChannelTRequest implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1561694518612328767L;

	private String reqId;

    private String transferFlow;

    private String payTransFlow;

    private Date createTime;

    private Date updateTime;

    private String status;

    private String paySysNo;

    private String bizType;

    private String bizSysNo;

    private String spare1;

    private String spare2;

    private String failReason;

    private String infoCategoryCode;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId == null ? null : reqId.trim();
    }

    public String getTransferFlow() {
        return transferFlow;
    }

    public void setTransferFlow(String transferFlow) {
        this.transferFlow = transferFlow == null ? null : transferFlow.trim();
    }

    public String getPayTransFlow() {
        return payTransFlow;
    }

    public void setPayTransFlow(String payTransFlow) {
        this.payTransFlow = payTransFlow == null ? null : payTransFlow.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPaySysNo() {
        return paySysNo;
    }

    public void setPaySysNo(String paySysNo) {
        this.paySysNo = paySysNo == null ? null : paySysNo.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo == null ? null : bizSysNo.trim();
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2 == null ? null : spare2.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getInfoCategoryCode() {
        return infoCategoryCode;
    }

    public void setInfoCategoryCode(String infoCategoryCode) {
        this.infoCategoryCode = infoCategoryCode == null ? null : infoCategoryCode.trim();
    }
}