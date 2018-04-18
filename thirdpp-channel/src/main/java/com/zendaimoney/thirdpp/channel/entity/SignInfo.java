package com.zendaimoney.thirdpp.channel.entity;

import java.io.Serializable;

/**
 * 协议支付签约信息
 *
 * @author wulj
 */
public class SignInfo implements Serializable {

    private static final long serialVersionUID = -8087510502552237253L;

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
     * 原通道流水号
     */
    private String srcReqSn;

    /**
     * 短信验证码
     */
    private String verCode;

    /**
     * 协议号
     */
    private String agrmNo;

    /**
     * 000000 成功 111111 失败 222222 处理中 333333 异常
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

    public String getSrcReqSn() {
        return srcReqSn;
    }

    public void setSrcReqSn(String srcReqSn) {
        this.srcReqSn = srcReqSn;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
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

    public String getAgrmNo() {
        return agrmNo;
    }

    public void setAgrmNo(String agrmNo) {
        this.agrmNo = agrmNo;
    }
}
