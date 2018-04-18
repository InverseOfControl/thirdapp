package com.zendaimoney.thirdpp.trade.pub.vo.req.biz;

import java.io.Serializable;
import java.util.ArrayList;

public class RequestVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7731958054978050377L;

	// 业务系统编码
	private String bizSysNo;
	
	//业务类型
	private String bizTypeNo;

	// 信息类别编码
	private String infoCategoryCode;

	// 描述
	private String remark;

	// 备注1
	private String spare1;

	// 备注2
	private String spare2;

	private ArrayList<BizReqVo> list = new ArrayList<BizReqVo>();

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public ArrayList<BizReqVo> getList() {
		return list;
	}

	public void setList(ArrayList<BizReqVo> list) {
		this.list = list;
	}

    

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}

	public String getBizTypeNo() {
		return bizTypeNo;
	}

	public void setBizTypeNo(String bizTypeNo) {
		this.bizTypeNo = bizTypeNo;
	}
}
