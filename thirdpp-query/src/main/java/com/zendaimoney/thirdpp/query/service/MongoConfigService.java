package com.zendaimoney.thirdpp.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.util.Constants;
import com.zendaimoney.thirdpp.query.util.LogPrn;
import com.zendaimoney.thirdpp.query.util.MongoDBUtils;

/**
 * 查询开关设置
 * @author mencius
 */
@Service
@Transactional
public class MongoConfigService {
	
	private static final LogPrn logger = new LogPrn(MongoConfigService.class);
	
	private static final String DEFAULT = "000"; 
	
	/**
	 * 查询开关设置
	 * @return false/true
	 */
	public boolean queryIsNormal(String bizType) {
		
		logger.debug("获取系统开关配置信息：bizType=" + bizType);
		
		try {
			
			Map<String, Object> queryDoc = new HashMap<String, Object>();
			queryDoc.put("bizType", StringUtils.isEmpty(bizType) ? DEFAULT : bizType);
			// 查询开关设置
			List<Document> lists = MongoDBUtils.query(Constants.TPP_CONFIG_COLLECTION, queryDoc, 1);
			
			if (lists.size() == 1) {
				Document document = lists.get(0);
				String switchStr = (String)document.get("switch");
				
				if ("0".equalsIgnoreCase(switchStr)) {
					logger.debug("查看系统开关：正常");
					return true;
				}
			}
		} catch (Exception e) {
		}
		logger.debug("查看系统开关：关闭");
		return false;
	}

}
