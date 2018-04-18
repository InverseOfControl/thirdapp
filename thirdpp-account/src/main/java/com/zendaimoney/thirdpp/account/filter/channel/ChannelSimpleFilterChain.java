package com.zendaimoney.thirdpp.account.filter.channel;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.util.ActionMapperUtil;

@Component(value = "com.zendaimoney.thirdpp.account.filter.channel.ChannelSimpleFilterChain")
public class ChannelSimpleFilterChain implements ChannelFilterChain {

	private static final Log logger = LogFactory.getLog(ChannelSimpleFilterChain.class);

	@Override
	public void doFilter(ChannelAccountConfig config, ChannelAccountRequest request, int index, boolean isHandle) {
		Map<Integer, ChannelFilterTarget> filterMap = ActionMapperUtil.channelFilterMap;
		if (filterMap.get(index + 1) != null) {
			ChannelFilter nextFilter = (ChannelFilter)ServerConfig.getBean(filterMap.get(index + 1).getActionClazz().getName());
			nextFilter.doFilter(config, request, this, isHandle);
		} else {
			logger.info("没有可执行的拦截器类");
		}
	}
}
