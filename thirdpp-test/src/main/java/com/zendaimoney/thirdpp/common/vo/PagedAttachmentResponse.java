package com.zendaimoney.thirdpp.common.vo;

public class PagedAttachmentResponse<E> extends AttachmentResponse<E> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3601848014131877674L;

	/**
	 * 总记录数
	 */
	private int total;
	
	/**
	 * 每页记录数
	 */
	private int pageSize;
	
	
	/**
	 * 当前页数
	 */
	private int pageNo;


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageNo() {
		return pageNo;
	}


	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
	
	

}
