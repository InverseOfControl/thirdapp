package com.zendaimoney.thirdpp.channel.dto.resp.allinpay.sign.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 协议支付短信触发响应报文
 *
 * @author wulj
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RespBody {
    /**
     * 返回码
     */
    @XmlElement(name = "RET_CODE")
    private String retCode;

    /**
     * 错误信息
     */
    @XmlElement(name = "ERR_MSG")
    private String errMsg;

    /**
     * 协议号
     */
    @XmlElement(name = "AGRMNO")
    private String agrmNo;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getAgrmNo() {
        return agrmNo;
    }

    public void setAgrmNo(String agrmNo) {
        this.agrmNo = agrmNo;
    }
}
