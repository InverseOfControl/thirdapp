package com.zendaimoney.thirdpp.account.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.account.dao.ChannelAccountConfigDao;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;

@Service
@Component(value = "com.zendaimoney.thirdpp.account.service.ChannelAccountConfigService")
public class ChannelAccountConfigService {

	@Resource(name = "channelAccountConfigDao")
	private ChannelAccountConfigDao channelAccountConfigDao;
	
	public List<ChannelAccountConfig> getAvailableChannelAccountConfig() throws SQLException{
		return channelAccountConfigDao.getAvailableChannelAccountConfig();
	}
	
	public List<ChannelAccountConfig> getChannelAccountConfigByBizType(String bizType) throws SQLException {
		return channelAccountConfigDao.getChannelAccountConfigByBizType(bizType);
	}
	
	public ChannelAccountConfig getChannelAccountConfigByConfigId(long configId) throws SQLException {
		return channelAccountConfigDao.getChannelAccountConfigByConfigId(configId);
	}
}
