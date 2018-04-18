package com.zendaimoney.thirdpp.channel.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.dao.SysQueryInfoDao;
import com.zendaimoney.thirdpp.channel.entity.SysQueryInfo;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

@Service
@Transactional
public class SysQueryInfoService {

	// 日誌工具類
	private static final LogPrn logger = new LogPrn(SysQueryInfoService.class);

	@Autowired
	private SysQueryInfoDao sysQueryInfoDao;

	/**
	 * 这个方法通过spring容器实例化该对象时自动执行
	 */
	@PostConstruct
	public void loaddingSysInitData() {
		loaddingSysQueryInfos();
	}

	/**
	 * 加载所有查询模块记录
	 */
	private void loaddingSysQueryInfos() {
		List<SysQueryInfo> list = null;

		Map<String, String> temp = new HashMap<String, String>();

		try {
			list = sysQueryInfoDao.querySysQueryInfos();
			// 增加新数据
			if (list != null && !list.isEmpty()) {
				for (SysQueryInfo sysQueryInfo : list) {
					String key = sysQueryInfo.getPaySysNo()
							+ sysQueryInfo.getBizTypeNo();
					ThirdPPCacheContainer.sysQueryInfoMap.put(key,
							sysQueryInfo.getAppName());
					temp.put(key, sysQueryInfo.getAppName());
				}
				// 删掉已删除查询信息(已删除的数据也需要同步到内存)
				Iterator<Map.Entry<String, String>> it = ThirdPPCacheContainer.sysQueryInfoMap
						.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> entry = it.next();
					String key = entry.getKey();
					if (temp.get(key) == null) {
						it.remove();
					}

				}
			} else {
				ThirdPPCacheContainer.sysQueryInfoMap.clear();
			}
			// 释放缓存
			temp = null;

		} catch (SQLException e) {
			logger.error("===========", e);
		}

	}
}
