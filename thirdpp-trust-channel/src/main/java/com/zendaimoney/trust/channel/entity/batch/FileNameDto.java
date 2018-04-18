package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 文件名Dto
 * @author mencius
 *
 */
public class FileNameDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 标识交易包“CMBA”
	 */
	@CmbAnnotation(index = 1, length = 4, rightFill = true, filler = Constants.BLANK, hex = false)
	private String packetMark = "CMBA";
	
	/**
	 * 交易码
	 */
	@CmbAnnotation(index = 2, length = 4, rightFill = true, filler = Constants.BLANK, hex = false)
	private String tradeCode;
	
	/**
	 * 请求机构号
	 */
	@CmbAnnotation(index = 3, length = 10, rightFill = true, filler = Constants.BLANK, hex = false)
	private String organzition;
	
	/**
	 * 商户交易日期
	 */
	@CmbAnnotation(index = 4, length = 8, rightFill = true, filler = Constants.BLANK, hex = false)
	private String merchantDate;
	
	/**
	 * 批次
	 */
	@CmbAnnotation(index = 5, length = 5, rightFill = false, filler = Constants.ZERO, hex = false)
	private String batchNo;
	
	/**
	 * 文件结构标识
	 */
	@CmbAnnotation(index = 6, length = 1, rightFill = true, filler = Constants.BLANK, hex = false)
	private String structureFlag;
	
	/**
	 * 请求标识
	 */
	@CmbAnnotation(index = 7, length = 1, rightFill = true, filler = Constants.BLANK, hex = false)
	private String cmbRequestFlag;
	
	/**
	 * 扩展名
	 */
	@CmbAnnotation(index = 8, length = 4, rightFill = true, filler = Constants.BLANK, hex = false)
	private String cmbFileType;

	public String getPacketMark() {
		return packetMark;
	}

	public void setPacketMark(String packetMark) {
		this.packetMark = packetMark;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getOrganzition() {
		return organzition;
	}

	public void setOrganzition(String organzition) {
		this.organzition = organzition;
	}

	public String getMerchantDate() {
		return merchantDate;
	}

	public void setMerchantDate(String merchantDate) {
		this.merchantDate = merchantDate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getStructureFlag() {
		return structureFlag;
	}

	public void setStructureFlag(String structureFlag) {
		this.structureFlag = structureFlag;
	}

	public String getCmbRequestFlag() {
		return cmbRequestFlag;
	}

	public void setCmbRequestFlag(String cmbRequestFlag) {
		this.cmbRequestFlag = cmbRequestFlag;
	}

	public String getCmbFileType() {
		return cmbFileType;
	}

	public void setCmbFileType(String cmbFileType) {
		this.cmbFileType = cmbFileType;
	}
	
	
}
