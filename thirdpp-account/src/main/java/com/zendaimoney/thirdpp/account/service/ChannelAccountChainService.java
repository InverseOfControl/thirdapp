package com.zendaimoney.thirdpp.account.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.filter.channel.ChannelSimpleFilterChain;
import com.zendaimoney.thirdpp.account.util.CalendarUtils;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.IDGenerateUtil;

@Service
public class ChannelAccountChainService {

	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;

	public void handle(ChannelAccountConfig config, ChannelAccountRequest request) throws IOException {
		ChannelSimpleFilterChain chain = (ChannelSimpleFilterChain) ServerConfig
				.getBean(ChannelSimpleFilterChain.class.getName());
		if (request == null) {
			// 当天没有第三方对账请求记录,表示当天没有进行对账操作
			ChannelAccountRequest newRequest = initAccountRequest(config);
			int fetchType = config.getFetchType();
			// 主动到第三方进行文件获取 或者推送至本地 FTP
			if (Constants.Fetchtype.FETCH_TYPE_ACTIVE == fetchType || Constants.Fetchtype.FETCH_TYPE_PUSH == fetchType) {
				chain.doFilter(config, newRequest, Constants.ChannelFilter.FILTER_ANNOTATION_INITIAL, false);
			}

			// 手动
			if (Constants.Fetchtype.FETCH_TYPE_BY_HAND == fetchType) {
			}

		} else {
			chain.doFilter(config, request, Constants.ChannelFilter.FILTER_ANNOTATION_INITIAL, false);
		}
	}

	/**
	 * 初始化第三方对账请求记录
	 * 
	 * @param config
	 * @throws SQLException
	 */
	private ChannelAccountRequest initAccountRequest(ChannelAccountConfig config) {
		ChannelAccountRequest accountRequest = new ChannelAccountRequest();
		accountRequest.setReqId(IDGenerateUtil.createReqId());
		accountRequest
				.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INITIAL);
		accountRequest.setThirdTypeNo(config.getThirdTypeNo());
		accountRequest.setMerchantNo(config.getMerchantNo());
		accountRequest.setConfigId(config.getId());
		accountRequest
				.setDownloadFailedTimes(Constants.ACCOUNT_REQUEST_DOWNLOAD_FAILED_TIMES_INITIAL);
		accountRequest.setAccountDay(CalendarUtils.getShortFormatNow());
		accountRequest.setBackupFailedTimes(Constants.ACCOUNT_REQUEST_DOWNLOAD_FAILED_TIMES_INITIAL);
		accountRequest.setBizType(config.getBizType());
		accountRequest.setAppName(config.getAppName());
		channelAccountRequestService.insert(accountRequest);
		return accountRequest;
	}
}
