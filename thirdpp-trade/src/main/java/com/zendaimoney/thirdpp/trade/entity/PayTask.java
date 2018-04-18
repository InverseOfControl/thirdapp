package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zendaimoney.thirdpp.trade.validate.InsertCheck;
import com.zendaimoney.thirdpp.trade.validate.rule.BankCardType;
import com.zendaimoney.thirdpp.trade.validate.rule.Currency;
import com.zendaimoney.thirdpp.trade.validate.rule.IDType;
import com.zendaimoney.thirdpp.trade.validate.rule.IsNeedPush;
import com.zendaimoney.thirdpp.trade.validate.rule.Money;
import com.zendaimoney.thirdpp.trade.validate.rule.PaySysNo;
import com.zendaimoney.thirdpp.trade.validate.rule.Priority;
import com.zendaimoney.thirdpp.trade.validate.rule.UserType;

/**
 * 代付/融资业务任务对象
 * 
 * @author 00237489
 *
 */
public class PayTask implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 698565161071274966L;

	//主键(对应SEQ_TPP_TRADE_TASK序列)
	private Long id;

	//交易请求ID,对应TPP_TRADE_T_REQUEST字段REQ_ID
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 20, message = "000002,过长")
    private String reqId;

    //业务系统客户编号
	@Length(max = 64, message = "000002,过长")
    private String bizSysAccountNo;

    //证大客户编号
	@Length(max = 64, message = "000002,过长")
    private String zengdaiAccountNo;

    //付款方账户编号
	@Length(max = 32, message = "000002,过长")
    private String payerAccountNo;

    //付款方姓名
	@Length(max = 64, message = "000002,过长")
    private String payerAccountName;

    //收款方姓名
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 64, message = "000002,过长")
    private String receiverName;

    //收款方银行卡号
	@Length(max = 64, message = "000002,过长")
    private String receiverBankCardNo;

    //收款方银行卡类型 1.借记卡，2信用卡,3存折
	@Length(max = 1, message = "000002,过长")
	@BankCardType(message = "000003,应该为 {1}{2}{3}")
    private String receiverBankCardType;

    //收款方证件类型0=身份证  1=户口簿  2=护照 3=军官证 4=士兵证 5=港澳居民来往内地通行证  6=台湾同胞来往内地通行证  7=临时身份证	8=外国人居留证  9=警官证  X=其他证件
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 1, message = "000002,过长")
	@IDType(message = "000003,应该为 {0}{1}{2}{3}{4}{5}{6}{7}{8}{9}{X}{Y}{Z}")
	private String receiverIdType;

    //收款方证件号
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 64, message = "000002,过长")
    private String receiverId;

    //收款方银行编码
	@Length(max = 32, message = "000002,过长")
    private String receiverBankCode;

    //收款方银行支行行号
	@Length(max = 32, message = "000002,过长")
    private String receiverSubBankCode;

    //收款方账户编号
	@Length(max = 32, message = "000002,过长")
    private String receiverAccountNo;

    //币种(0人民币)
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Currency(message = "000003,应该为 {0}")
    private String currency;

    //金额
	@NotNull(message = "000001,为空", groups = InsertCheck.class)
	@Money(message = "000006,格式有误")
    private BigDecimal amount;

    //手续费
	@Money(message = "000006,格式有误")
    private BigDecimal fee;

    //业务备注
	@Length(max = 128, message = "000002,过长")
    private String bizRemark;

    //业务流水号
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 32, message = "000002,过长")
    private String bizFlow;

    //优先级(3最高 2高 1中 0普通)
	@NotNull(message = "000001,为空", groups = InsertCheck.class)
	@Priority(message = "000003,应该为 {0}{1}{2}{3}")
    private Integer priority;

    //0待发送、1发送中、2已发送
    private String status;

    //备注
    @Length(max = 256, message = "000002,过长")
    private String remark;

    //创建人
    @Length(max = 64, message = "000002,过长")
    private String creater;

    //创建时间
    private String createTime;

    //更新时间
    private String updateTime;

    //发送程序名称
    private String sendThreadName;

    //是否拆单(1:拆单、0不拆单)该字段由转发程序判断进行修改。
    private Integer isSeparate;

    //拆单数
    private Integer separateCount;

    //备用1
    @Length(max = 100, message = "000002,过长")
    private String spare1;

    //备用2
    @Length(max = 100, message = "000002,过长")
    private String spare2;

    //发送次数
    private Integer sendNum;

    //业务类型(002居间人模式代付003资金托管融资)
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 4, message = "000002,过长")
    private String bizType;

    //是否需要推送 0不需要推送1需要推送
	@NotNull(message = "000001,为空", groups = InsertCheck.class)
	@IsNeedPush(message = "000003,应该为 {0}{1}")
    private Integer isNeedPush;
    
    //交易结果状态：000000交易成功 111111交易失败222222 交易处理中444444交易部分成功
    private String tradeStatus;

    //交易结果描述
    private String tradeResultInfo;

    //交易成功金额
    private BigDecimal tradeSuccessAmount;

    //信息类别编码
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
    private String infoCategoryCode;
    
    //支付渠道编码
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 4, message = "000002,过长")
	@PaySysNo(message = "000003,值不正确")
    private String paySysNo;
	
    //业务系统编码
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 4, message = "000002,过长")
    private String bizSysNo;
	
	// 收款人类型
	@NotBlank(message = "000001,为空", groups = InsertCheck.class)
	@Length(max = 1, message = "000002,过长")
	@UserType(message = "000003,应该为 {P}{C}")
	private String receiverType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getBizSysAccountNo() {
		return bizSysAccountNo;
	}

	public void setBizSysAccountNo(String bizSysAccountNo) {
		this.bizSysAccountNo = bizSysAccountNo;
	}

	public String getZengdaiAccountNo() {
		return zengdaiAccountNo;
	}

	public void setZengdaiAccountNo(String zengdaiAccountNo) {
		this.zengdaiAccountNo = zengdaiAccountNo;
	}

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public String getPayerAccountName() {
		return payerAccountName;
	}

	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverBankCardNo() {
		return receiverBankCardNo;
	}

	public void setReceiverBankCardNo(String receiverBankCardNo) {
		this.receiverBankCardNo = receiverBankCardNo;
	}

	public String getReceiverBankCardType() {
		return receiverBankCardType;
	}

	public void setReceiverBankCardType(String receiverBankCardType) {
		this.receiverBankCardType = receiverBankCardType;
	}

	public String getReceiverIdType() {
		return receiverIdType;
	}

	public void setReceiverIdType(String receiverIdType) {
		this.receiverIdType = receiverIdType;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverBankCode() {
		return receiverBankCode;
	}

	public void setReceiverBankCode(String receiverBankCode) {
		this.receiverBankCode = receiverBankCode;
	}

	public String getReceiverSubBankCode() {
		return receiverSubBankCode;
	}

	public void setReceiverSubBankCode(String receiverSubBankCode) {
		this.receiverSubBankCode = receiverSubBankCode;
	}

	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}

	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getBizRemark() {
		return bizRemark;
	}

	public void setBizRemark(String bizRemark) {
		this.bizRemark = bizRemark;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getSendThreadName() {
		return sendThreadName;
	}

	public void setSendThreadName(String sendThreadName) {
		this.sendThreadName = sendThreadName;
	}

	public Integer getIsSeparate() {
		return isSeparate;
	}

	public void setIsSeparate(Integer isSeparate) {
		this.isSeparate = isSeparate;
	}

	public Integer getSeparateCount() {
		return separateCount;
	}

	public void setSeparateCount(Integer separateCount) {
		this.separateCount = separateCount;
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

	public Integer getSendNum() {
		return sendNum;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Integer getIsNeedPush() {
		return isNeedPush;
	}

	public void setIsNeedPush(Integer isNeedPush) {
		this.isNeedPush = isNeedPush;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeResultInfo() {
		return tradeResultInfo;
	}

	public void setTradeResultInfo(String tradeResultInfo) {
		this.tradeResultInfo = tradeResultInfo;
	}

	public BigDecimal getTradeSuccessAmount() {
		return tradeSuccessAmount;
	}

	public void setTradeSuccessAmount(BigDecimal tradeSuccessAmount) {
		this.tradeSuccessAmount = tradeSuccessAmount;
	}

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getReceiverType() {
		return receiverType;
	}
	
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	

}