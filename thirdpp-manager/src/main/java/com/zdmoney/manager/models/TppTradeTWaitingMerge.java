package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

public class TppTradeTWaitingMerge implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3315819495095793488L;

	private Long id;

    private String tradeFlow;

    private String bizTypeNo;

    private Date createTime;

    private Date updateTime;

    private String status;

    private String mergeModuleName;

    private String creater;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeFlow() {
        return tradeFlow;
    }

    public void setTradeFlow(String tradeFlow) {
        this.tradeFlow = tradeFlow == null ? null : tradeFlow.trim();
    }

    public String getBizTypeNo() {
        return bizTypeNo;
    }

    public void setBizTypeNo(String bizTypeNo) {
        this.bizTypeNo = bizTypeNo == null ? null : bizTypeNo.trim();
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

    public String getMergeModuleName() {
        return mergeModuleName;
    }

    public void setMergeModuleName(String mergeModuleName) {
        this.mergeModuleName = mergeModuleName == null ? null : mergeModuleName.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }
}