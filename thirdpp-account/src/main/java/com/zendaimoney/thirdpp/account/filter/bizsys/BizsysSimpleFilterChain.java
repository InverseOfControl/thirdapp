package com.zendaimoney.thirdpp.account.filter.bizsys;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.util.ActionMapperUtil;

@Component(value = "com.zendaimoney.thirdpp.account.filter.bizsys.BizsysSimpleFilterChain")
public class BizsysSimpleFilterChain implements BizsysFilterChain {

	public static final String bName = "com.zendaimoney.thirdpp.account.filter.bizsys.BizsysSimpleFilterChain";
	
	private static final Log logger = LogFactory.getLog(BizsysSimpleFilterChain.class);
	
	@Override
	public void doFilter(BizsysAccountRequest bizsysAccountRequest, BizsysAccountConfig config, int index, boolean isHandle) {
		Map<Integer, BizsysFilterTarget> filterMap = ActionMapperUtil.bizsysFilterMap;
		if (filterMap.get(index + 1) != null) {
			BizsysFilter nextFilter = (BizsysFilter)ServerConfig.getBean(filterMap.get(index + 1).getActionClazz().getName());
			nextFilter.doFilter(bizsysAccountRequest, config, this, isHandle);
		} else {
			logger.info("没有可执行的filter");
		}
	}
}
