package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zendaimoney.thirdpp.trade.validate.AccountVerificationInsertCheck;
import com.zendaimoney.thirdpp.trade.validate.CardBinInsertCheck;
import com.zendaimoney.thirdpp.trade.validate.rule.BankCardType;
import com.zendaimoney.thirdpp.trade.validate.rule.IDType;

public class OperationRequest implements Serializable{

	private static final long serialVersionUID = 2624072035389195668L;

	// 请求ID(根据规则生成)
	@NotBlank(message = "000001,为空", groups = {AccountVerificationInsertCheck.class, CardBinInsertCheck.class})
	@Length(max = 20, message = "000002,过长")
	private String reqId;

	// 业务类型(005开户、006绑卡、007开户绑卡、016换卡、017解绑、 010实名验证 018卡bin查询)
	@NotBlank(message = "000001,为空", groups = {AccountVerificationInsertCheck.class, CardBinInsertCheck.class})
	@Length(max = 4, message = "000002,过长")
	private String bizTypeNo;

	// 业务系统编号
	@NotBlank(message = "000001,为空", groups = {AccountVerificationInsertCheck.class, CardBinInsertCheck.class})
	@Length(max = 4, message = "000002,过长")
	private String bizSysNo;

	// 业务系统客户编号
	@Length(max = 64, message = "000002,过长")
	private String bizSysAccountNo;

	// 证大客户编号
	@Length(max = 64, message = "000002,过长")
	private String zengdaiAccountNo;

	// 姓名
	@NotBlank(message = "000001,为空", groups = AccountVerificationInsertCheck.class)
	@Length(max = 100, message = "000002,过长")
	private String realName;

	// 性别(0女 1男)
	@Length(max = 2, message = "000002,过长")
	private String gender;

	// 民族
	@Length(max = 10, message = "000002,过长")
	private String nation;

	// 客户手机号码
	@Length(max = 15, message = "000002,过长")
	private String mobile;

	// 客户预留手机号码
	@Length(max = 15, message = "000002,过长")
	private String reserveMobile;

	// 绑定银行卡号
	@NotBlank(message = "000001,为空", groups = {AccountVerificationInsertCheck.class, CardBinInsertCheck.class})
	@Length(max = 20, message = "000002,过长")
	private String bankCardNo;

	// 银行卡类型 1.借记卡，2信用卡
	@NotBlank(message = "000001,为空", groups = AccountVerificationInsertCheck.class)
	@Length(max = 2, message = "000002,过长")
	@BankCardType(message = "000003,应该为 {1}{2}{3}")
	private String bankCardType;

	// 银行编码
	@Length(max = 4, message = "000002,过长")
	private String bankCode;

	// 支付渠道编码
	@Length(max = 4, message = "000002,过长")
	private String paySysNo;

	// 0-处理中，1-操作成功，2-操作失败
	@Length(max = 2, message = "000002,过长")
	private String status;
	
	// 信息类别编码
	@NotBlank(message = "000001,为空", groups = {AccountVerificationInsertCheck.class, CardBinInsertCheck.class})
	@Length(max = 10, message = "000002,过长")
	private String infoCategoryCode;
	
	public enum StatusEnum {
		processing("0"), success("1"), fail("2");
		private String statusCode;
		private StatusEnum(String no) {
			this.statusCode = no;
		}
		public String getStatusCode(){
			return this.statusCode;
		}
	}

	/**
	 * 证件类型
	 * 0=身份证 1=户口簿 2=护照 3=军官证 4=士兵证 5=港澳居民来往内地通行证 6=台湾同胞来往内地通行证 7=临时身份证 8=外国人居留证
	 * 9=警官证 X=其他证件Y=驾驶证Z=回乡证
	 **/
	
	@NotBlank(message = "000001,为空", groups = AccountVerificationInsertCheck.class)
	@Length(max = 4, message = "000002,过长")
	@IDType(message = "000003,应该为 {0}{1}{2}{3}{4}{5}{6}{7}{8}{9}{X}{Y}{Z}")
	private String idType;

	
	// 证件号
	@NotBlank(message = "000001,为空", groups = AccountVerificationInsertCheck.class)
	@Length(max = 20, message = "000002,过长")
	private String idNo;

	// 请求IP
	@Length(max = 64, message = "000002,过长")
	private String ip;

	// 业务系统流水号
	@Length(max = 64, message = "000002,过长")
	private String bizFlow;

	// 是否同步（1同步0异步）
	@Length(max = 2, message = "000002,过长")
	private String isSync;

	// 开户行编码
	@Length(max = 20, message = "000002,过长")
	private String openBankCode;

	// 支付渠道返回操作流水号
	@Length(max = 64, message = "000002,过长")
	private String payTransFlow;

	// 交易流水号
	@Length(max = 32, message = "000002,过长")
	@NotBlank(message = "000001,为空", groups = {AccountVerificationInsertCheck.class, CardBinInsertCheck.class})
	private String transferFlow;

	// 返回时间
	private Date respTime;

	// 返回信息
	@Length(max = 512, message = "000002,过长")
	private String respInfo;

	// 返回码
	@Length(max = 128, message = "000002,过长")
	private String respCode;

	// 客户第三方账户编号
	@Length(max = 64, message = "000002,过长")
	private String thirdAccountNo;

	// 移动设备信息
	@Length(max = 100, message = "000002,过长")
	private String mac;

	// 创建人
	@Length(max = 64, message = "000002,过长")
	private String creater;

	// 创建时间
	private Date createTime;

	// 更新时间
	private Date updateTime;

	// 备用1
	@Length(max = 100, message = "000002,过长")
	private String spare1;

	// 备用2
	@Length(max = 100, message = "000002,过长")
	private String spare2;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getPayTransFlow() {
		return payTransFlow;
	}

	public void setPayTransFlow(String payTransFlow) {
		this.payTransFlow = payTransFlow;
	}

	public String getTransferFlow() {
		return transferFlow;
	}

	public void setTransferFlow(String transferFlow) {
		this.transferFlow = transferFlow;
	}

	public Date getRespTime() {
		return respTime;
	}

	public void setRespTime(Date respTime) {
		this.respTime = respTime;
	}

	public String getRespInfo() {
		return respInfo;
	}

	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getThirdAccountNo() {
		return thirdAccountNo;
	}

	public void setThirdAccountNo(String thirdAccountNo) {
		this.thirdAccountNo = thirdAccountNo;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getBizTypeNo() {
		return bizTypeNo;
	}

	public void setBizTypeNo(String bizTypeNo) {
		this.bizTypeNo = bizTypeNo;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReserveMobile() {
		return reserveMobile;
	}

	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
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

	public String getOpenBankCode() {
		return openBankCode;
	}

	public void setOpenBankCode(String openBankCode) {
		this.openBankCode = openBankCode;
	}

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}
	
}
