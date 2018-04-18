package com.zendaimoney.thirdpp.alarm.common;

import java.io.Serializable;
import java.util.Date;

public class Monitor implements Serializable {

	private static final long serialVersionUID = -4008830337135649956L;

	private Long id;
	private String mobiles;
	private String note;
	private Date createTime;
	private String mark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}