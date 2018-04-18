package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

public class TppChannelTradeResult implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1524321717149271809L;

	private Long id;

    private String paySysNo;

    private String transferFlow;

    private String payTransFlow;

    private String bizType;

    private String transRepCode;

    private String transRepInfo;

    private String status;

    private Date createTime;

    private String reqId;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaySysNo() {
        return paySysNo;
    }

    public void setPaySysNo(String paySysNo) {
        this.paySysNo = paySysNo == null ? null : paySysNo.trim();
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

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getTransRepCode() {
        return transRepCode;
    }

    public void setTransRepCode(String transRepCode) {
        this.transRepCode = transRepCode == null ? null : transRepCode.trim();
    }

    public String getTransRepInfo() {
        return transRepInfo;
    }

    public void setTransRepInfo(String transRepInfo) {
        this.transRepInfo = transRepInfo == null ? null : transRepInfo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId == null ? null : reqId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}