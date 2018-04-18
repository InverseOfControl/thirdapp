package com.zendaimoney.thirdpp.channel.dto.req.allinpay.sign.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReqHeader implements Serializable{

    @XmlTransient
    private static final long serialVersionUID = 5819894727469177860L;
    /**
     * 交易代碼
     */
    @XmlElement(name = "TRX_CODE")
    private String trxCode = "310001";

    /**
     * 版本
     */
    @XmlElement(name = "VERSION")
    private String version = "04";

    /**
     * 数据格式,2：xml格式
     */
    @XmlElement(name = "DATA_TYPE")
    private String dataType = "2";

    /**
     * 处理级别,0-9 0优先级最低
     */
    @XmlElement(name = "LEVEL")
    private String level = "";

    /**
     * 商户代码
     */
    @XmlElement(name = "MERCHANT_ID")
    private String merchantId;

    /**
     * 用户名
     */
    @XmlElement(name = "USER_NAME")
    private String userName = "";

    /**
     * 用户密码
     */
    @XmlElement(name = "USER_PASS")
    private String userPass = "";

    /**
     * 交易批次号
     */
    @XmlElement(name = "REQ_SN")
    private String reqSn = "";

    /**
     * 签名信息
     */
    @XmlElement(name = "SIGNED_MSG")
    private String signedMsg = "";

    public String getTrxCode() {
        return trxCode;
    }

    public void setTrxCode(String trxCode) {
        this.trxCode = trxCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getReqSn() {
        return reqSn;
    }

    public void setReqSn(String reqSn) {
        this.reqSn = reqSn;
    }

    public String getSignedMsg() {
        return signedMsg;
    }

    public void setSignedMsg(String signedMsg) {
        this.signedMsg = signedMsg;
    }
}
