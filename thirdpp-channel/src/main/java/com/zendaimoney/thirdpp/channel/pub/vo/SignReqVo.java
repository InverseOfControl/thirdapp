package com.zendaimoney.thirdpp.channel.pub.vo;

/**
 * 协议支付签约VO
 *
 * @author wulj
 */
public class SignReqVo extends BizReqVo {
    private static final long serialVersionUID = -5471316879804627228L;

    /**
     * 请求流水号
     */
    private String reqId;

    /**
     * 交易流水号
     */
    private String tradeFlow;

    /**
     * 原通道流水号
     */
    private String srcReqSn;

    /**
     * 短信验证码
     */
    private String verCode;

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
}
