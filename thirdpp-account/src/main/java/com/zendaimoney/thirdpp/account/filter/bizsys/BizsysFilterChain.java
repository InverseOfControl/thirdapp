package com.zendaimoney.thirdpp.account.filter.bizsys;

import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;

public interface BizsysFilterChain {

	public void doFilter(BizsysAccountRequest request, BizsysAccountConfig config, int index, boolean isHandle)
			throws PlatformException;

}
