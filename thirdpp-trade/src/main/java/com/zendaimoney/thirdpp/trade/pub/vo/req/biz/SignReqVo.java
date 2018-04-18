package com.zendaimoney.thirdpp.trade.pub.vo.req.biz;

/**
 * 协议支付签约Vo
 *
 * @author wulj
 */
public class SignReqVo extends BizReqVo {
    private static final long serialVersionUID = 5222884916886323517L;

    /**
     * 原请求流水号
     */
    private String srcFlowId;

    /**
     * 短信验证码
     */
    private String verCode;

    public String getSrcFlowId() {
        return srcFlowId;
    }

    public void setSrcFlowId(String srcFlowId) {
        this.srcFlowId = srcFlowId;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
}
