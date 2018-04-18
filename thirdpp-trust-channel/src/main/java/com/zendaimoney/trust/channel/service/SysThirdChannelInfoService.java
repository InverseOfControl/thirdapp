package com.zendaimoney.trust.channel.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.trust.channel.dao.SysThirdChannelInfoDao;
import com.zendaimoney.trust.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ThirdPPCacheContainer;

@Service
@Transactional
public class SysThirdChannelInfoService {

	// 日誌工具類
	private static final LogPrn logger = new LogPrn(
			SysThirdChannelInfoService.class);

	@Autowired
	private SysThirdChannelInfoDao sysThirdChannelInfoDao;

	/**
	 * 这个方法通过spring容器实例化该对象时自动执行
	 */
	@PostConstruct
	public void loaddingSysInitData() {
		loaddingSysThirdChannelInfos();
	}

	/**
	 * 加载所有系统第三方通道信息
	 */
	private void loaddingSysThirdChannelInfos() {
		List<SysThirdChannelInfo> list = null;

		try {
			list = sysThirdChannelInfoDao.querySysThirdChannelInfos();

			// 增加新数据
			if (list != null && !list.isEmpty()) {
				for (SysThirdChannelInfo sysThirdChannelInfo : list) {
					String key = sysThirdChannelInfo.getThirdTypeNo()
							+ sysThirdChannelInfo.getMerchantType();
					if (SysThirdChannelInfo.STATUS_OPENED == sysThirdChannelInfo
							.getStatus()) {
						ThirdPPCacheContainer.sysThirdChannelInfoMap.put(key,
								sysThirdChannelInfo);
					} else {
						ThirdPPCacheContainer.sysThirdChannelInfoMap
								.remove(key);
					}
				}
			} else {
				ThirdPPCacheContainer.sysThirdChannelInfoMap.clear();
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}

	}

}
