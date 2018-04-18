package com.zendaimoney.thirdpp.trade.entity;

import com.zendaimoney.thirdpp.trade.validate.InsertCheck;
import com.zendaimoney.thirdpp.trade.validate.rule.PaySysNo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by 00233197 on 2017/7/4.
 */
public class QueryPayTask implements Serializable {

    private static final long serialVersionUID = 698565161071274965L;

    //业务流水号
    @NotBlank(message = "000001,为空", groups = InsertCheck.class)
    @Length(max = 32, message = "000002,过长")
    private String bizFlow;

    //创建人
    @Length(max = 64, message = "000002,过长")
    private String creater;

    //信息类别编码
    @NotBlank(message = "000001,为空", groups = InsertCheck.class)
    private String infoCategoryCode;

    //业务系统编码
    @NotBlank(message = "000001,为空", groups = InsertCheck.class)
    @Length(max = 4, message = "000002,过长")
    private String bizSysNo;

    private String tradeFlow;

    private String paySysNo;
    //备注
    @Length(max = 256, message = "000002,过长")
    private String remark;

    //
    @Length(max = 100, message = "000002,过长")
    private String spare1;

    //
    @Length(max = 100, message = "000002,过长")
    private String spare2;

    public String getBizFlow() {
        return bizFlow;
    }

    public void setBizFlow(String bizFlow) {
        this.bizFlow = bizFlow;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getInfoCategoryCode() {
        return infoCategoryCode;
    }

    public void setInfoCategoryCode(String infoCategoryCode) {
        this.infoCategoryCode = infoCategoryCode;
    }

    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo;
    }

    public String getRemark() {
        return remark;
    }

    public String getPaySysNo() {
        return paySysNo;
    }

    public void setPaySysNo(String paySysNo) {
        this.paySysNo = paySysNo;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1;
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2;
    }

    public String getTradeFlow() {
        return tradeFlow;
    }

    public void setTradeFlow(String tradeFlow) {
        this.tradeFlow = tradeFlow;
    }
}
