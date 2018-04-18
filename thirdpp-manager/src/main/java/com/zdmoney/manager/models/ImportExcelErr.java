package com.zdmoney.manager.models;

import java.io.Serializable;

public class ImportExcelErr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6918295030523555872L;
	private String rowNum;
	private String fied;
	private String note;
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	public String getFied() {
		return fied;
	}
	public void setFied(String fied) {
		this.fied = fied;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
