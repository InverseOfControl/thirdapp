package com.zendaimoney.thirdpp.account.filter.channel;

import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;

public interface ChannelFilterChain {

	public void doFilter(ChannelAccountConfig config, ChannelAccountRequest request, int index, boolean isHandle);
	
}
