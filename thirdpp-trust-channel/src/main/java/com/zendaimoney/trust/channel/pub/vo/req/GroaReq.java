package com.zendaimoney.trust.channel.pub.vo.req;

import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 登记团信息(GROA)
 * @author mencius
 *
 */
public class GroaReq extends TrustBizReqVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 1	团编号	30	M	编号唯一
	 */
	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String groupNo;
	
	/**
	 * 2	团名称	200	M	HEX
	 */
	@CmbAnnotation(index = 2, length = 200, rightFill = true, filler = Constants.BLANK, hex = true)
	private String groupName;
	
	/**
	 * 3	团用户号	20	M	团常用资金账户
	 */
	@CmbAnnotation(index = 3, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String groupUserNo;
	
	/**
	 * 4	团虚拟子账户号	30	M	团常用资金账户
	 */
	@CmbAnnotation(index = 4, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String gVirtualSubNo;
	
	/**
	 * 5	团风险金用户号	20	C	团风险金账户
	 */
	@CmbAnnotation(index = 5, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String groupRiskUserNo;
	
	/**
	 * 6	团风险金虚拟子账户号	30	C	团风险金账户
	 */
	@CmbAnnotation(index = 6, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String gRiskVirtualSubNo;
	
	/**
	 * 7	团期限	4	C	例如0012表示12
	 */
	@CmbAnnotation(index = 7, length = 4, rightFill = false, filler = Constants.ZERO, hex = false)
	private int term;
	
	/**
	 * 8	期限单位	1	C	Y：年，M：月，D：天
	 */
	@CmbAnnotation(index = 8, length = 1, rightFill = true, filler = Constants.BLANK, hex = false)
	private String unit;
	
	/**
	 * 9	总金额	15	C	
	 */
	@CmbAnnotation(index = 9, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal totalAmount;
	
	/**
	 * 10	预期收益率	4	C	0510表示年化5.10%
	 */
	@CmbAnnotation(index = 10, length = 4, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal 	yield;
	
	/**
	 * 11	还款方式	40	C	HEX
	 */
	@CmbAnnotation(index = 11, length = 40, rightFill = true, filler = Constants.BLANK, hex = true)
	private String repayment;
	
	/**
	 * 12	备用	200	C
	 */
	@CmbAnnotation(index = 12, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupUserNo() {
		return groupUserNo;
	}

	public void setGroupUserNo(String groupUserNo) {
		this.groupUserNo = groupUserNo;
	}

	public String getgVirtualSubNo() {
		return gVirtualSubNo;
	}

	public void setgVirtualSubNo(String gVirtualSubNo) {
		this.gVirtualSubNo = gVirtualSubNo;
	}

	public String getGroupRiskUserNo() {
		return groupRiskUserNo;
	}

	public void setGroupRiskUserNo(String groupRiskUserNo) {
		this.groupRiskUserNo = groupRiskUserNo;
	}

	public String getgRiskVirtualSubNo() {
		return gRiskVirtualSubNo;
	}

	public void setgRiskVirtualSubNo(String gRiskVirtualSubNo) {
		this.gRiskVirtualSubNo = gRiskVirtualSubNo;
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

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
	
}
