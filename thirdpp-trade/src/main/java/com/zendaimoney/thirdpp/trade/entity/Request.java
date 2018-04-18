package com.zendaimoney.thirdpp.trade.entity;

import com.zendaimoney.thirdpp.trade.validate.InsertCheck;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 居间人模式下交易请求(代收/代付/融资/还款)
 * 
 * @author 00231257
 *
 */
public class Request implements Serializable {

	// 异步操作
	public static final String ISSYNC_NO = "0";

	// 同步操作
	public static final String ISSYNC_YES = "1";

	// 补单操作
	public static final String ISHANDADD_YES = "1";

	// 非补单操作
	public static final String ISHANDADD_NO = "0";
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6434760746033094596L;

	// TPP交易请求流水号(根据规则生成)
	@NotBlank(message = "000001,is null", groups = InsertCheck.class)
	@Length(max = 20, message = "000002,overlength")
	private String reqId;

	// 业务类型
	@NotBlank(message = "000001,is null", groups = InsertCheck.class)
	@Length(max = 4, message = "000002,overlength")
	private String bizTypeNo;

	// 业务系统编码
	@NotBlank(message = "000001,is null", groups = InsertCheck.class)
	@Length(max = 4, message = "000002,overlength")
	private String bizSysNo;

	

	// 请求IP
	@NotBlank(message = "000001,is null", groups = InsertCheck.class)
	@Length(max = 20, message = "000002,overlength")
	private String ip;

	// 同步(1):交易请求直接又通道发送；异步(0):交易请求由转发程序代为发送;
	@NotBlank(message = "000001,is null", groups = InsertCheck.class)
	private String isSync;

	// 是否补单(1补单、0非补单)
	@NotBlank(message = "000001,is null", groups = InsertCheck.class)
	private String isHandAdd;

	// 描述
	@Length(max = 128, message = "000002,overlength")
	private String remark;

	// 创建人
	@Length(max = 32, message = "000002,overlength")
	private String creater;

	// 创建时间
	private String createTime;

	// 备注1
	@Length(max = 100, message = "000002,overlength")
	private String spare1;

	// 备注2
	@Length(max = 100, message = "000002,overlength")
	private String spare2;
	
	//信息类别编码
	@NotBlank(message = "000001,is null", groups = InsertCheck.class)
	private String infoCategoryCode;

	private String paySysNo;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
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


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	public String getIsHandAdd() {
		return isHandAdd;
	}

	public void setIsHandAdd(String isHandAdd) {
		this.isHandAdd = isHandAdd;
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
}