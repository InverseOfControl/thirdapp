package com.zendaimoney.thirdpp.trade.pub.vo.req.query;

/**
 * 银行卡认证请求对象
 * 
 * @author 00237071
 * 
 */
public class BankCardAuthReqVo extends BaseQueryRequestVo {

	private static final long serialVersionUID = 8941423640536722684L;
	
	// 银行卡卡类型 （1-借记卡，2：贷记卡（信用卡））
	private String bankCardType;

	// 银行卡号
	private String bankCardNo;

	/** 证件类型： 
	  	0=身份证
		1=户口簿
		2=护照
		3=军官证
		4=士兵证
		5=港澳居民来往内地通行证(回乡证)
		6=台湾同胞来往内地通行证
		7=临时身份证
		8=外国人居留证
		9=警官证
		X=其他证件
	**/
	private String idType;

	// 证件号码
	private String idNo;

	// 证件持有人真实姓名
	private String realName;
	
	// 手机号码
	private String mobile;
	
	// 信息类别编码
	private String infoCategoryCode;

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}
}
