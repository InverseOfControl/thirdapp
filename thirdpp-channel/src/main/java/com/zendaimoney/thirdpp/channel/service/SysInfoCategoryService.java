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

import com.zendaimoney.thirdpp.channel.dao.SysInfoCategoryDao;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

@Service
@Transactional
public class SysInfoCategoryService {

	// 日誌工具類
	private static final LogPrn logger = new LogPrn(
			SysInfoCategoryService.class);

	@Autowired
	private SysInfoCategoryDao sysInfoCategoryDao;

	/**
	 * 这个方法通过spring容器实例化该对象时自动执行
	 */
	@PostConstruct
	public void loaddingSysInitData() {
		loaddingSysInfoCategorys();
	}

	/**
	 * 加载所有信息类别记录
	 */
	private void loaddingSysInfoCategorys() {
		List<SysInfoCategory> list = null;
		Map<String, SysInfoCategory> temp = new HashMap<String, SysInfoCategory>();
		try {
			list = sysInfoCategoryDao.querySysInfoCategorys();
			if (list != null && !list.isEmpty()) {
				// 添加新增信息类别
				for (SysInfoCategory sysInfoCategory : list) {
					ThirdPPCacheContainer.sysInfoCategoryMap.put(
							sysInfoCategory.getInfoCategoryCode(),
							sysInfoCategory);
					temp.put(sysInfoCategory.getInfoCategoryCode(),
							sysInfoCategory);
				}
				// 删掉已删除信息类别(已删除的数据也需要同步到内存)
				Iterator<Map.Entry<String, SysInfoCategory>> it = ThirdPPCacheContainer.sysInfoCategoryMap
						.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, SysInfoCategory> entry = it.next();
					String key = entry.getKey();
					if (temp.get(key) == null) {
						it.remove();
					}

				}
			} else {
				ThirdPPCacheContainer.sysInfoCategoryMap.clear();
			}
			// 释放缓存
			temp = null;
		} catch (SQLException e) {
			logger.error("===========", e);
		}
	}
}
