package com.zendaimoney.thirdpp.query.mongo;

public class CollectionConfig {
	
	/**
	 * 集合代码
	 */
	private String code;
	
	/**
	 * 集合名称
	 */
	private String collectionName;
	
	/**
	 * 下一集合
	 */
	private String nextCollection;
	
	/**
	 * 停滞时间
	 */
	private Long sleepTime;
	
	/**
	 * 处理条数
	 */
	private int dealSize;
	
	/**
	 * 数据处理驻留时间
	 */
	private Long resideTime;
	
	/**
	 * 业务模式(0：线下；1：线上；2：资金托管)
	 */
	private String opMode;
	
	/**
	 * 最大查询次数
	 */
	private long maxQueryNum;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCollectionName() {
		return collectionName;
	}
	
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public Long getSleepTime() {
		return sleepTime;
	}
	
	public void setSleepTime(Long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public String getNextCollection() {
		return nextCollection;
	}
	
	public void setNextCollection(String nextCollection) {
		this.nextCollection = nextCollection;
	}
	
	public int getDealSize() {
		return dealSize;
	}
	
	public void setDealSize(int dealSize) {
		this.dealSize = dealSize;
	}
	
	public Long getResideTime() {
		return resideTime;
	}
	
	public void setResideTime(Long resideTime) {
		this.resideTime = resideTime;
	}
	
	public String getOpMode() {
		return opMode;
	}
	
	public void setOpMode(String opMode) {
		this.opMode = opMode;
	}
	
	public long getMaxQueryNum() {
		return maxQueryNum;
	}
	
	public void setMaxQueryNum(long maxQueryNum) {
		this.maxQueryNum = maxQueryNum;
	}
}
