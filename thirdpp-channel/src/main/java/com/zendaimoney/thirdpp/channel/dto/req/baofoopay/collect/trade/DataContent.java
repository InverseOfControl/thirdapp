package com.zendaimoney.thirdpp.channel.dto.req.baofoopay.collect.trade;

import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

import javax.xml.bind.annotation.*;

/**
 * Created by 00233197 on 2017/5/9.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document1")
@XmlRootElement(name = "data_content")
public class DataContent {
    @XmlElement(name = "txn_sub_type", required = true)
    private String txnSubType;

    @XmlElement(name = "biz_type", required = true)
    private String bizType="0000";

    @XmlElement(name = "terminal_id", required = true)
    private String terminalId;
    @XmlElement(name = "member_id", required = true)
    private String memberId;

    @XmlElement(name = "pay_code", required = true)
    private String payCode;

    @XmlElement(name = "pay_cm", required = true)
    private String payCm;

    @XmlElement(name = "acc_no", required = true)
    private String accNo;

    @XmlElement(name = "id_card_type", required = true)
    private String idCardType;

    @XmlElement(name = "id_card", required = true)
    private String idCard;

    @XmlElement(name = "id_holder", required = true)
    private String idHolder;

    @XmlElement(name = "mobile", required = true)
    private String mobile;

    @XmlElement(name = "valid_date", required = true)
    private String validDate;

    @XmlElement(name = "valid_no", required = true)
    private String validNo;

    @XmlElement(name = "trans_id", required = true)
    private String transId;

    @XmlElement(name = "txn_amt", required = true)
    private String txnAmt;

    @XmlElement(name = "trade_date", required = true)
    private String tradeDate;

    @XmlElement(name = "additional_info", required = true)
    private String additionalInfo;

    @XmlElement(name = "req_reserved", required = true)
    private String reqReserved;

    @XmlElement(name = "trans_serial_no", required = true)
    private String transSerialNo;

    public String getTxnSubType() {
        return txnSubType;
    }

    /**
     * 对象转XML报文
     *
     * @return
     */
    public String encode() {
        JaxbBinder binder = new JaxbBinder(this.getClass());
        String xml = binder.toXml(this, "UTF-8");
        return xml.substring(xml.indexOf("\n") + 1);
    }

    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayCm() {
        return payCm;
    }

    public void setPayCm(String payCm) {
        this.payCm = payCm;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdHolder() {
        return idHolder;
    }

    public void setIdHolder(String idHolder) {
        this.idHolder = idHolder;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getValidNo() {
        return validNo;
    }

    public void setValidNo(String validNo) {
        this.validNo = validNo;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    public String getTransSerialNo() {
        return transSerialNo;
    }

    public void setTransSerialNo(String transSerialNo) {
        this.transSerialNo = transSerialNo;
    }
}
