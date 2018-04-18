package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

public class TppTradeTWaitingQuery implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8227997855185719004L;

	private Long id;

    private String tradeFlow;

    private String bizTypeNo;

    private String bizSysNo;

    private String paySysNo;

    private String infoCategoryCode;

    private String queryModuleName;

    private Date createTime;

    private Date updateTime;

    private String status;

    private String creater;
    private String  payerAccountNo;
    
    private String opMode;
    
    private String auditor;
    
    private Date auditTime;
    
    public String getOpMode() {
		return opMode;
	}

	public void setOpMode(String opMode) {
		this.opMode = opMode == null ? null : opMode.trim();
	}

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo == null ? null : payerAccountNo.trim();
	}

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

    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo == null ? null : bizSysNo.trim();
    }

    public String getPaySysNo() {
        return paySysNo;
    }

    public void setPaySysNo(String paySysNo) {
        this.paySysNo = paySysNo == null ? null : paySysNo.trim();
    }

    public String getInfoCategoryCode() {
        return infoCategoryCode;
    }

    public void setInfoCategoryCode(String infoCategoryCode) {
        this.infoCategoryCode = infoCategoryCode == null ? null : infoCategoryCode.trim();
    }

    public String getQueryModuleName() {
        return queryModuleName;
    }

    public void setQueryModuleName(String queryModuleName) {
        this.queryModuleName = queryModuleName == null ? null : queryModuleName.trim();
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor == null ? null : auditor.trim();
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
    
    
}