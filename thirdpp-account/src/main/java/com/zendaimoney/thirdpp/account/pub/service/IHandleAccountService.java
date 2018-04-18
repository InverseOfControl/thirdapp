package com.zendaimoney.thirdpp.account.pub.service;

import com.zendaimoney.thirdpp.account.pub.vo.AccountResponseVo;

public interface IHandleAccountService {

	/**
	 * 通道对账手工请求
	 * @param reqId
	 * @param configId
	 * @param accountDay
	 * @return
	 */
	public AccountResponseVo channelAccount(String reqId, String configId, String accountDay);
	
	/**
	 * 业务系统对账手工请求
	 * @param reqId
	 * @param configId
	 * @param accountDay
	 * @return
	 */
	public AccountResponseVo bizsysAccount(String reqId, String configId, String accountDay);
	
}
