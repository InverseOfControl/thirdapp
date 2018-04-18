package com.zendaimoney.thirdpp.account.filter.channel;

import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;

public interface ChannelFilter {

	// 执行过滤   
    public void doFilter(ChannelAccountConfig config, ChannelAccountRequest request, ChannelFilterChain chain, boolean isHandle);
    
}
