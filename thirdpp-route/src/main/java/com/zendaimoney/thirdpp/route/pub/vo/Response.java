package com.zendaimoney.thirdpp.route.pub.vo;


/**
 * Created by YM20051 on 2017/5/27.
 */
public class Response implements java.io.Serializable {

    /**  */
    private static final long serialVersionUID = 1939629429313277169L;
    /**
     * 返回码
     */
    private String code = "000000";
    /**
     * 返回信息
     */
    private String msg;

    /**
     * 操作流水号
     */
    private String flowId;
    /**
     * 银行返回码
     */
    private String bankRepCode;
    /**
     * 通道返回码
     */
    private String thirdTypeNo;


    public String getThirdTypeNo() {
        return thirdTypeNo;
    }

    public void setThirdTypeNo(String thirdTypeNo) {
        this.thirdTypeNo = thirdTypeNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getBankRepCode() {
        return bankRepCode;
    }

    public void setBankRepCode(String bankRepCode) {
        this.bankRepCode = bankRepCode;
    }

    @Override
    public String toString() {
        return "Response [code=" + code + ", msg=" + msg + ", flowId=" + flowId + ", bankRepCode="
               + bankRepCode + ", thirdTypeNo=" + thirdTypeNo + "]";
    }
    
    
}
