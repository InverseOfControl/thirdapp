package com.zendaimoney.thirdpp.transfer.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.transfer.dao.SysInfoCategoryDao;
import com.zendaimoney.thirdpp.transfer.dao.SysThirdChannelInfoDao;
import com.zendaimoney.thirdpp.transfer.dao.ThirdFieldMapperDao;
import com.zendaimoney.thirdpp.transfer.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.transfer.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.transfer.entity.ThirdFieldMapper;
import com.zendaimoney.thirdpp.transfer.util.LogPrn;
import com.zendaimoney.thirdpp.transfer.util.ThirdPPCacheContainer;

@Service
@Transactional
public class ThirdFieldMapperService {

	// 日誌工具類
	private static final LogPrn logger = new LogPrn(ThirdFieldMapperService.class);

	@Autowired
	private ThirdFieldMapperDao thirdFieldMapperDao;
	
	@Autowired
	private SysThirdChannelInfoDao sysThirdChannelInfoDao;
	
	@Autowired
	private SysInfoCategoryDao sysInfoCategoryDao;
	
	/**
	 * 这个方法通过spring容器实例化该对象时自动执行
	 */
	@PostConstruct
	public void loaddingSysInitData(){
		//初始化银行编码映射数据
		loaddingBanksMappingInfo();
		//初始化币种映射数据
		loaddingCurrencyMappingInfo();
		//初始化银行卡类型映射数据
		loaddingBankCardTypeMappingInfo();
		//加载所有银行额度上限映射记录
		loaddingBankMaxMoneyMappingInfo();
		//加载所有信息类别记录
		loaddingSysInfoCategorys();
		//加载所有系统第三方通道信息
		loaddingSysThirdChannelInfos();
	}

	/** 
	 * 加载所有银行编码映射记录
	 */
	private void loaddingBanksMappingInfo() {

		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_BANK_CODE);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				String tppToThirdMapVal = thirdFieldMapper.getThirdFieldCode();

				String thirdToTppMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getThirdFieldCode();
				String thirdToTppMapVal = thirdFieldMapper.getTppFieldCode();

				String thirdBankName = thirdFieldMapper.getFieldName();

				String thirdNameToMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getFieldName();
				ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap.put(
						tppToThirdMapKey, tppToThirdMapVal);
				ThirdPPCacheContainer.thirdBankCodeToTppBankCodeMap.put(
						thirdToTppMapKey, thirdToTppMapVal);
				ThirdPPCacheContainer.tppBankCodeToThirdBankNameMap.put(
						tppToThirdMapKey, thirdBankName);
				ThirdPPCacheContainer.thirdBankNameToTppBankCodeMap.put(
						thirdNameToMapKey, thirdFieldMapper.getThirdFieldCode());
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}

	}
	
	/** 
	 * 加载所有币种映射记录
	 */
	private void loaddingCurrencyMappingInfo() {

		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_CURRENCY);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				String tppToThirdMapVal = thirdFieldMapper.getThirdFieldCode();

				String thirdToTppMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getThirdFieldCode();
				String thirdToTppMapVal = thirdFieldMapper.getTppFieldCode();

				ThirdPPCacheContainer.tppCurrencyToThirdCurrencyMap.put(
						tppToThirdMapKey, tppToThirdMapVal);
				ThirdPPCacheContainer.thirdCurrencyToTppCurrencyMap.put(
						thirdToTppMapKey, thirdToTppMapVal);
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}

	}
	
	/** 
	 * 加载所有银行卡类型映射记录
	 */
	private void loaddingBankCardTypeMappingInfo() {

		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_BANK_CARD_TYPE);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				String tppToThirdMapVal = thirdFieldMapper.getThirdFieldCode();

				String thirdToTppMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getThirdFieldCode();
				String thirdToTppMapVal = thirdFieldMapper.getTppFieldCode();

				ThirdPPCacheContainer.tppBankCardTypeToThirdBankCardTypeMap.put(
						tppToThirdMapKey, tppToThirdMapVal);
				ThirdPPCacheContainer.thirdBankCardTypeToTppBankCardTypeMap.put(
						thirdToTppMapKey, thirdToTppMapVal);
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}

	}
	
	/** 
	 * 加载所有银行额度上限映射记录
	 */
	private void loaddingBankMaxMoneyMappingInfo() {

		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_BANK_CODE);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				//获取代收业务银行最高限额
				BigDecimal tppToThirdMapVal = thirdFieldMapper.getCollectMaxMoney();
				ThirdPPCacheContainer.tppBankCodeToThirdBankCollectMaxMoneyMap.put(
						tppToThirdMapKey, tppToThirdMapVal);
				
				//获取代付业务银行最高限额
				BigDecimal tppToThirdMapVal1 = thirdFieldMapper.getPayMaxMoney();
				ThirdPPCacheContainer.tppBankCodeToThirdBankPayMaxMoneyMap.put(
						tppToThirdMapKey, tppToThirdMapVal1);
				
				//获取快捷支付业务银行最高限额
				BigDecimal tppToThirdMapVal2 = thirdFieldMapper.getQuickPayMaxMoney();
				ThirdPPCacheContainer.tppBankCodeToThirdBankQucikPayMaxMoneyMap.put(
						tppToThirdMapKey, tppToThirdMapVal2);
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}

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
