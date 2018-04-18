package com.zendaimoney.thirdpp.query.conf;

public class TransferConfig {

//	/**
//	 * 最大查询次数
//	 */
//	private long maxQueryNum;
	
	/**
	 * 最大驻留量
	 */
	private int maxResideNum;
	
	/**
	 * 休眠时间
	 */
	private long sleepTime;
	
//	public long getMaxQueryNum() {
//		return maxQueryNum;
//	}
//	
//	public void setMaxQueryNum(long maxQueryNum) {
//		this.maxQueryNum = maxQueryNum;
//	}
	
	public int getMaxResideNum() {
		return maxResideNum;
	}
	
	public void setMaxResideNum(int maxResideNum) {
		this.maxResideNum = maxResideNum;
	}
	
	public long getSleepTime() {
		return sleepTime;
	}
	
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
}
