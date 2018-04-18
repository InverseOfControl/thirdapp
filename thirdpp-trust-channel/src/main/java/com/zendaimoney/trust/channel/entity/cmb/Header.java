package com.zendaimoney.trust.channel.entity.cmb;

import java.io.Serializable;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.Constants;


/**
 * 资金托管-报文头
 * @author mencius
 *
 */
public class Header implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 标识交易包“CMBA”
	 */
	@CmbAnnotation(index = 1, length = 4, rightFill = true, filler = Constants.BLANK, hex = false)
	private String packetMark = "CMBA";
	
	/**
	 * 包长度
	 */
	@CmbAnnotation(index = 2, length = 4, rightFill = false, filler = Constants.ZERO, hex = false)
	private String length;
	
	/**
	 * 交易码
	 */
	@CmbAnnotation(index = 3, length = 4, rightFill = true, filler = Constants.BLANK, hex = false)
	private String tradeCode;
	
	/**
	 * 银行交易日期
	 */
	@CmbAnnotation(index = 4, length = 8, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankDate;
	
	/**
	 * 银行交易时间
	 */
	@CmbAnnotation(index = 5, length = 6, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankTime;
	
	/**
	 * 银行交易流水号
	 */
	@CmbAnnotation(index = 6, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankFlow;
	
	/**
	 * 商户交易日期
	 */
	@CmbAnnotation(index = 7, length = 8, rightFill = true, filler = Constants.BLANK, hex = false)
	private String merchantDate;
	
	/**
	 * 商户交易时间
	 */
	@CmbAnnotation(index = 8, length = 6, rightFill = true, filler = Constants.BLANK, hex = false)
	private String merchantTime;
	
	/**
	 * 商户流水号
	 */
	@CmbAnnotation(index = 9, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String merchantFlow;
	
	/**
	 * 请求机构号
	 */
	@CmbAnnotation(index = 10, length = 10, rightFill = true, filler = Constants.BLANK, hex = false)
	private String organzition;
	
	/**
	 * 通讯返回码
	 */
	@CmbAnnotation(index = 11, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 通讯校验码
	 */
	@CmbAnnotation(index = 12, length = 16, rightFill = true, filler = Constants.BLANK, hex = false)
	private String mac;
	
	/**
	 * 默认构造器
	 */
	public Header() {
		this.merchantDate = CalendarUtils.getShortFormatNow();
		this.merchantTime = CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT_TIME);
	}

	public String getPacketMark() {
		return packetMark;
	}

	public void setPacketMark(String packetMark) {
		this.packetMark = packetMark;
	}

	public String getLength() {
		return length;
	}
	
	public void setLength(String length) {
		this.length = length;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getBankDate() {
		return bankDate;
	}

	public void setBankDate(String bankDate) {
		this.bankDate = bankDate;
	}

	public String getBankTime() {
		return bankTime;
	}

	public void setBankTime(String bankTime) {
		this.bankTime = bankTime;
	}

	public String getBankFlow() {
		return bankFlow;
	}

	public void setBankFlow(String bankFlow) {
		this.bankFlow = bankFlow;
	}

	public String getMerchantDate() {
		return merchantDate;
	}

	public void setMerchantDate(String merchantDate) {
		this.merchantDate = merchantDate;
	}

	public String getMerchantTime() {
		return merchantTime;
	}

	public void setMerchantTime(String merchantTime) {
		this.merchantTime = merchantTime;
	}

	public String getMerchantFlow() {
		return merchantFlow;
	}
	
	public void setMerchantFlow(String merchantFlow) {
		this.merchantFlow = merchantFlow;
	}
	
	public String getOrganzition() {
		return organzition;
	}

	public void setOrganzition(String organzition) {
		this.organzition = organzition;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
}
