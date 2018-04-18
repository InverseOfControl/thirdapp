package com.zendaimoney.thirdpp.channel.dto.req.baofoopay.collect.query;

import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

import javax.xml.bind.annotation.*;

/**
 * Created by 00233197 on 2017/5/9.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document1")
@XmlRootElement(name = "data_content")
public class QueryDataContent {
    @XmlElement(name = "txn_sub_type", required = true)
    private String txnSubType;

    @XmlElement(name = "biz_type", required = true)
    private String bizType="0000";

    @XmlElement(name = "terminal_id", required = true)
    private String terminalId;
    @XmlElement(name = "member_id", required = true)
    private String memberId;

    @XmlElement(name = "orig_trans_id", required = true)
    private String origTransId;

    @XmlElement(name = "orig_trade_date", required = true)
    private String origTradeDate;

    @XmlElement(name = "trans_serial_no", required = true)
    private String transSerialNo;

    @XmlElement(name = "additional_info", required = true)
    private String additionalInfo;

    @XmlElement(name = "req_reserved", required = true)
    private String reqReserved;


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

    public String getTxnSubType() {
        return txnSubType;
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

    public String getOrigTransId() {
        return origTransId;
    }

    public void setOrigTransId(String origTransId) {
        this.origTransId = origTransId;
    }

    public String getOrigTradeDate() {
        return origTradeDate;
    }

    public void setOrigTradeDate(String origTradeDate) {
        this.origTradeDate = origTradeDate;
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
