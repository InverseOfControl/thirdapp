package com.zendaimoney.thirdpp.route.pub.vo;

import java.math.BigDecimal;

/**
 * Created by YM20051 on 2017/5/25.
 */
public class TaskReqVO extends Request{

    /**  */
    private static final long serialVersionUID = -1173156074686294494L;

    // 付款人银行卡号
    private String payerBankCardNo;

    // 付款方银行编码
    private String payerBankCode;

    // 信息类别编码
    private String infoCategoryCode;

    // 金额
    private BigDecimal amount;

    public String getPayerBankCardNo() {
        return payerBankCardNo;
    }

    public void setPayerBankCardNo(String payerBankCardNo) {
        this.payerBankCardNo = payerBankCardNo;
    }

    public String getPayerBankCode() {
        return payerBankCode;
    }

    public void setPayerBankCode(String payerBankCode) {
        this.payerBankCode = payerBankCode;
    }

    public String getInfoCategoryCode() {
        return infoCategoryCode;
    }

    public void setInfoCategoryCode(String infoCategoryCode) {
        this.infoCategoryCode = infoCategoryCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    

}
