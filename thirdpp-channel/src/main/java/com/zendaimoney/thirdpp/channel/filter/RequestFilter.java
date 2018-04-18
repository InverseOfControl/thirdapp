package com.zendaimoney.thirdpp.channel.filter;

import com.zendaimoney.thirdpp.common.vo.Response;

public interface RequestFilter {
	
	/**
	 * 系统基础过滤器
	 * @param object
	 * @return
	 */
	public Response doFilter(Object object);
	
	/**
	 * 第三方支持银行卡过滤
	 * @param object
	 * @param bankCode
	 * @return
	 */
	public Response bankFilter(Object object, String bankCode);
}
