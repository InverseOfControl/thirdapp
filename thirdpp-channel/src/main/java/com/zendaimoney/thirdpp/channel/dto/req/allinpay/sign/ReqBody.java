package com.zendaimoney.thirdpp.channel.dto.req.allinpay.sign;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReqBody implements Serializable {

    @XmlTransient
    private static final long serialVersionUID = 349991560898904841L;

    @XmlElement(name = "MERCHANT_ID")
    private String merchantId;

    @XmlElement(name = "SRCREQSN")
    private String srcReqSn;

    @XmlElement(name = "VERCODE")
    private String verCode;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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
