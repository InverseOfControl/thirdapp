package com.zendaimoney.thirdpp.trade.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.ThirdFieldMapperDao;
import com.zendaimoney.thirdpp.trade.entity.ThirdFieldMapper;
import com.zendaimoney.thirdpp.trade.util.LogPrn;
import com.zendaimoney.thirdpp.trade.util.ThirdPPCacheContainer;

@Service
@Transactional
public class ThirdFieldMapperService {

	// 日誌工具類
	private static final LogPrn logger = new LogPrn(ThirdFieldMapperService.class);

	@Autowired
	private ThirdFieldMapperDao thirdFieldMapperDao;
	
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
	 * 查询第三方平台支持的银行
	 * 
	 * @param thirdPartyPayPlatformCode 第三方平台编码
	 * @return
	 * @throws SQLException
	 */
	public List<ThirdFieldMapper> queryThirdPartyPayPlatformSupportBanks(String thirdPartyPayPlatformCode) throws SQLException {
		return thirdFieldMapperDao.queryThirdPartyPayPlatformSupportBanks(thirdPartyPayPlatformCode);
	}
	
	
	/**
	 * 查询第三方平台支持的银行详情
	 * 
	 * @param thirdPartyPayPlatformCode 第三方平台编码
	 * @param thirdPartyPayPlatformSupportBankCode TPP银行编码
	 * @return
	 * @throws SQLException
	 */
	public ThirdFieldMapper queryThirdPartyPayPlatformSupportBankInfo(String thirdPartyPayPlatformCode, String thirdPartyPayPlatformSupportBankCode) throws SQLException {
		return thirdFieldMapperDao.queryThirdPartyPayPlatformSupportBankInfo(thirdPartyPayPlatformCode, thirdPartyPayPlatformSupportBankCode);
	}
}
