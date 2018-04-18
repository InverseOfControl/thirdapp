package com.zendaimoney.thirdpp.channel.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.dao.ThirdFieldMapperDao;
import com.zendaimoney.thirdpp.channel.entity.ThirdFieldMapper;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

@Service
@Transactional
public class ThirdBankStatusService {

	// 日誌工具類
	private static final LogPrn logger = new LogPrn(
			ThirdBankStatusService.class);

	@Autowired
	private ThirdFieldMapperDao thirdFieldMapperDao;

	/**
	 * 这个方法通过spring容器实例化该对象时自动执行
	 */
	@PostConstruct
	public void loaddingSysInitData() {
		// 初始化银行编码映射数据
		loaddingBanksMappingInfo();
	}

	/**
	 * 加载所有银行编码映射记录
	 */
	private void loaddingBanksMappingInfo() {

		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao
					.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_BANK_CODE);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				ThirdPPCacheContainer.thirdBankStatusMap.put(tppToThirdMapKey,
						thirdFieldMapper.getStatus());
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}

	}

}
