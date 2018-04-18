package com.zendaimoney.thirdpp.account.filter.bizsys;

import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;

public interface BizsysFilter {

	// 执行过滤
	public void doFilter(BizsysAccountRequest request, BizsysAccountConfig config,BizsysSimpleFilterChain chain, boolean isHandle);

}
