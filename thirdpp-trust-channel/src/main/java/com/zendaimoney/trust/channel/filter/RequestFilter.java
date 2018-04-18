package com.zendaimoney.trust.channel.filter;

import com.zendaimoney.thirdpp.common.vo.Response;

public interface RequestFilter {
	
	/**
	 * 系统基础过滤器
	 * @param object
	 * @return
	 */
	public Response doFilter(Object object);
	
}
