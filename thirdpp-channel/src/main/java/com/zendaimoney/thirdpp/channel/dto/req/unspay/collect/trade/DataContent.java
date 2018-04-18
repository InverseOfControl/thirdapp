package com.zendaimoney.thirdpp.channel.dto.req.unspay.collect.trade;

import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

import javax.xml.bind.annotation.*;

/**
 * Created by 00233197 on 2017/5/9.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document1")
@XmlRootElement(name = "data_content")
public class DataContent {

    @XmlElement(name = "orderId", required = true)
    private String orderId;

    @XmlElement(name = "amount", required = true)
    private String amount;


    @XmlElement(name = "accName", required = true)
    private String accName;

    @XmlElement(name = "accNo", required = true)
    private String accNo;

    @XmlElement(name = "idCardNo", required = true)
    private String idCardNo;

    @XmlElement(name = "phoneNo", required = true)
    private String phoneNo;

    @XmlElement(name = "purpose", required = true)
    private String purpose;


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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
