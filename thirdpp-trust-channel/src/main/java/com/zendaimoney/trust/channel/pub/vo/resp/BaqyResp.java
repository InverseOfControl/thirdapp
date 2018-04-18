package com.zendaimoney.trust.channel.pub.vo.resp;

import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 余额查询（BAQY） 响应对象
 * 
 * @author 00237071
 * 
 */
public class BaqyResp extends CommonResp {

	private static final long serialVersionUID = 1L;

	// 交易返回码
	@CmbAnnotation(index = 1, length = 7, rightFill = true, filler = Constants.BLANK)
	private String retCode;

	// 用户号
	@CmbAnnotation(index = 2, length = 20, rightFill = true, filler = Constants.BLANK)
	private String userNo;

	// 用户虚拟子账户号
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK)
	private String virtualSubNo;

	// 可用金额
	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal avaliableAmount;

	// 冻结金额
	@CmbAnnotation(index = 5, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal freezeAmount;

	// 到帐金额
	@CmbAnnotation(index = 6, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal inAmount;

	// 待清算金额
	@CmbAnnotation(index = 7, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal needSettleAmount;

	// 垫付金额
	@CmbAnnotation(index = 8, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal advanceAmount;

	// 用户姓名
	@CmbAnnotation(index = 9, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String userName;

	// 用户类型
	@CmbAnnotation(index = 10, length = 1, rightFill = true, filler = Constants.BLANK)
	private String userType;

	// 证件类型
	@CmbAnnotation(index = 11, length = 3, rightFill = true, filler = Constants.BLANK)
	private String idType;

	// 证件号码
	@CmbAnnotation(index = 12, length = 30, rightFill = true, filler = Constants.BLANK)
	private String idNo;

	// 手机号码
	@CmbAnnotation(index = 13, length = 11, rightFill = true, filler = Constants.BLANK)
	private String mobile;

	// 交易描述信息
	@CmbAnnotation(index = 14, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String msg;

	// 备用 C
	@CmbAnnotation(index = 15, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getVirtualSubNo() {
		return virtualSubNo;
	}

	public void setVirtualSubNo(String virtualSubNo) {
		this.virtualSubNo = virtualSubNo;
	}

	public BigDecimal getAvaliableAmount() {
		return avaliableAmount;
	}

	public void setAvaliableAmount(BigDecimal avaliableAmount) {
		this.avaliableAmount = avaliableAmount;
	}

	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public BigDecimal getNeedSettleAmount() {
		return needSettleAmount;
	}

	public void setNeedSettleAmount(BigDecimal needSettleAmount) {
		this.needSettleAmount = needSettleAmount;
	}

	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}
