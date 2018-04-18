package com.zendaimoney.trust.channel.pub.vo.req;

import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 登记散标(PROA)
 * @author mencius
 *
 */
public class ProaReq extends TrustBizReqVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 1	散标编号	30	M	编号唯一
	 */
	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String disperseSubjectNo;
	
	/**
	 * 2	散标名称	200	M	HEX
	 */
	@CmbAnnotation(index = 2, length = 200, rightFill = true, filler = Constants.BLANK, hex = true)
	private String disperseSubjectName;
	
	/**
	 * 3	借款人类型	40	M	中小企业借款人,个人贷借款人,债券转让融资人  HEX
	 */
	@CmbAnnotation(index = 3, length = 40, rightFill = true, filler = Constants.BLANK, hex = true)
	private String borrowerType;
	
	/**
	 * 4	借款人用户号	20	M
	 */
	@CmbAnnotation(index = 4, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String borrowerNo;
	
	/**
	 * 5	借款人虚拟子账户号	30	M
	 */
	@CmbAnnotation(index = 5, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bVirtualSubNo;
	
	/**
	 * 6	散标期限	4	M	例如0012表示12
	 */
	@CmbAnnotation(index = 6, length = 4, rightFill = false, filler = Constants.ZERO, hex = false)
	private int term;
	
	/**
	 * 7	期限单位	1	M	Y：年，M：月，D：天
	 */
	@CmbAnnotation(index = 7, length = 1, rightFill = true, filler = Constants.BLANK, hex = false)
	private String unit;
	
	/**
	 * 8	总金额	15	M
	 */
	@CmbAnnotation(index = 8, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal totalAmount;
	
	/**
	 * 9	预期收益率	4	M	0510表示年化5.10%
	 */
	@CmbAnnotation(index = 9, length = 4, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal yield;
	
	/**
	 * 10	还款方式	40	M	HEX
	 */
	@CmbAnnotation(index = 10, length = 40, rightFill = true, filler = Constants.BLANK, hex = true)
	private String repayment;
	
	/**
	 * 11	关联团编号	30	C	若散标和团关联，则将团编号填于此
	 */
	@CmbAnnotation(index = 11, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String groupNo;
	
	/**
	 * 12	备用	200	C
	 */
	@CmbAnnotation(index = 12, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getDisperseSubjectNo() {
		return disperseSubjectNo;
	}

	public void setDisperseSubjectNo(String disperseSubjectNo) {
		this.disperseSubjectNo = disperseSubjectNo;
	}

	public String getDisperseSubjectName() {
		return disperseSubjectName;
	}

	public void setDisperseSubjectName(String disperseSubjectName) {
		this.disperseSubjectName = disperseSubjectName;
	}

	public String getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(String borrowerType) {
		this.borrowerType = borrowerType;
	}

	public String getBorrowerNo() {
		return borrowerNo;
	}

	public void setBorrowerNo(String borrowerNo) {
		this.borrowerNo = borrowerNo;
	}

	public String getbVirtualSubNo() {
		return bVirtualSubNo;
	}

	public void setbVirtualSubNo(String bVirtualSubNo) {
		this.bVirtualSubNo = bVirtualSubNo;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getYield() {
		return yield;
	}

	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}

	public String getRepayment() {
		return repayment;
	}

	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
	
	
}
