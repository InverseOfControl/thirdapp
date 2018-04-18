package com.zendaimoney.thirdpp.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zendaimoney.thirdpp.account.util.InsertCheck;

public class AccountInfo implements Serializable {

	private static final long serialVersionUID = -2150447719840323694L;
	
	public static final String UNCONTROLLED_FIELD_BLANK_VALUE = "E001";
	public static final String UNCONTROLLED_FIELD_ORIGINAL_AMOUNT_BLANL_VALUE = "-0.00";
	
	public static final String UNCONTROLLED_FIELD_TRADE_FLOW = "tradeFlow";
	public static final String UNCONTROLLED_FIELD_ORIGINAL_AMOUNT = "originalAmount";
	public static final String UNCONTROLLED_FIELD_THIRD_PARTY_TRADE_FLOW = "thirdPartyTradeFlow";
	public static final String UNCONTROLLED_FIELD_THIRD_PARTY_TRADE_TIME = "tradeTime";
	
	// 主键
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	private Long id;
	
	// 对账渠道编码
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	@Length(max = 10, message = "000002,过长")
	private String thirdTypeNo;
	
	// 商户号
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	@Length(max = 100, message = "000002,过长")
	private String merchantNo;
	
	// 第三方对账请求 id
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	@Length(max = 20, message = "000002,过长")
	private String thirdPartyAccountReqId;
	// 业务系统对账请求 id
	private String bizsysAccountReqId;
	// 对账日期
	private String accountDay;
	
	
	// 任务id 【从任务表获取】
	private Long taskId;
	// 任务金额 【从任务表获取】
	private BigDecimal taskAmount;
	// 是否拆单 0不拆单1:拆单
	private int isSeparate;
	// 拆单数
	private int separateCount;
	
	
	// 交易流水号 【从对账文件中获取】
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	@Length(max = 16, message = "000002,过长")
	private String tradeFlow;
	// 原始金额
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	@Length(max = 17, message = "000002,过长")
	private String originalAmount;
	// 金额 根据原始金额 以及货币单位进行转化
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	private String amount;
	// 第三方交易流水号
	@Length(max = 100, message = "000002,过长")
	private String thirdPartyTradeFlow;
	// 交易时间
	@Length(max = 20, message = "000002,过长")
	private String tradeTime;
	// 当前索引值
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	private int currentIndex;
	// 业务类型 取交易流水的前三位
	@NotBlank(groups = InsertCheck.class, message = "000001,为空")
	@Length(max = 4, message = "000002,过长")
	private String bizType;
	
	
	// 业务流水号【从交易流水表中获取】
	private String bizFlow;
	// 业务系统号
	private String bizSysNo;

	
	// 与第三方对账状态 0=未对账 1=对账失败 2=对账成功
	private int accountStatus;
	// 与业务系统对账状态 0=未对账，1=已对账
	private int bizsysAccountStatus;
	// 原始交易订单不存在
	// 订单状态不匹配（第三方成功，我方失败）
	// 交易金额不匹配

	private String failedReason;
	// 创建时间
	private Date createDate;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThirdTypeNo() {
		return thirdTypeNo;
	}

	public void setThirdTypeNo(String thirdTypeNo) {
		this.thirdTypeNo = thirdTypeNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getThirdPartyAccountReqId() {
		return thirdPartyAccountReqId;
	}

	public void setThirdPartyAccountReqId(String thirdPartyAccountReqId) {
		this.thirdPartyAccountReqId = thirdPartyAccountReqId;
	}

	public String getBizsysAccountReqId() {
		return bizsysAccountReqId;
	}

	public void setBizsysAccountReqId(String bizsysAccountReqId) {
		this.bizsysAccountReqId = bizsysAccountReqId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public int getIsSeparate() {
		return isSeparate;
	}

	public void setIsSeparate(int isSeparate) {
		this.isSeparate = isSeparate;
	}

	public int getSeparateCount() {
		return separateCount;
	}

	public void setSeparateCount(int separateCount) {
		this.separateCount = separateCount;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}

	public String getThirdPartyTradeFlow() {
		return thirdPartyTradeFlow;
	}

	public void setThirdPartyTradeFlow(String thirdPartyTradeFlow) {
		this.thirdPartyTradeFlow = thirdPartyTradeFlow;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getBizsysAccountStatus() {
		return bizsysAccountStatus;
	}

	public void setBizsysAccountStatus(int bizsysAccountStatus) {
		this.bizsysAccountStatus = bizsysAccountStatus;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getTaskAmount() {
		return taskAmount;
	}

	public void setTaskAmount(BigDecimal taskAmount) {
		this.taskAmount = taskAmount;
	}

	public String getAccountDay() {
		return accountDay;
	}

	public void setAccountDay(String accountDay) {
		this.accountDay = accountDay;
	}
	
}
