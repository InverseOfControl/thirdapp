package com.zendaimoney.trust.channel.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.trust.channel.dao.ThirdFieldMapperDao;
import com.zendaimoney.trust.channel.entity.ThirdFieldMapper;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ThirdPPCacheContainer;

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
		//初始化银行卡类型映射数据
		loaddingBankCardTypeMappingInfo();
		
		//加载所有证件类型映射记录
		loaddingIdTypeMappingInfo();
		
		// 加载所以对公对私类型记录
		loaddingPubAndPriMappingInfo();
		
		// 加载所有用户类型(对公、对私)类型映射记录
		loaddingUserTypeMappingInfo();
		
		// 加载所有交易类别映射记录
		loaddingTradeTypeMappingInfo();
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
	 * 加载所有证件类型映射记录
	 */
	private void loaddingIdTypeMappingInfo(){
		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_ID_TYPE);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				String tppToThirdMapVal = thirdFieldMapper.getThirdFieldCode();
				ThirdPPCacheContainer.tppIdTypeToThirdIdTypeMap.put(
						tppToThirdMapKey, tppToThirdMapVal);
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}
	}
	
	/** 
	 * 加载所有对公、对私类型映射记录
	 */
	private void loaddingPubAndPriMappingInfo(){
		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_PUB_AND_PRI_TYPE);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				String tppToThirdMapVal = thirdFieldMapper.getThirdFieldCode();
				ThirdPPCacheContainer.tppPubAndPriTothirdUserTypeMap.put(
						tppToThirdMapKey, tppToThirdMapVal);
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}
	}
	
	/** 
	 * 加载所有用户类型类型映射记录
	 */
	private void loaddingUserTypeMappingInfo(){
		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_USER_TYPE);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				String tppToThirdMapVal = thirdFieldMapper.getThirdFieldCode();
				ThirdPPCacheContainer.tppUserTypeTothirdUserTypeMap.put(
						tppToThirdMapKey, tppToThirdMapVal);
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}
	}
	
	/** 
	 * 加载所有交易类别映射记录
	 */
	private void loaddingTradeTypeMappingInfo(){
		List<ThirdFieldMapper> list = null;

		try {
			list = thirdFieldMapperDao.queryThirdFieldMappersByFieldType(ThirdFieldMapper.FIELD_TYPE_TRADE_TYPE);
			for (ThirdFieldMapper thirdFieldMapper : list) {
				String tppToThirdMapKey = thirdFieldMapper.getThirdPartyType()
						+ thirdFieldMapper.getTppFieldCode();
				String tppToThirdMapVal = thirdFieldMapper.getThirdFieldCode();
				ThirdPPCacheContainer.tppTradeTypeTothirdTradeTypeMap.put(
						tppToThirdMapKey, tppToThirdMapVal);
			}
		} catch (SQLException e) {
			logger.error("===========", e);
		}
	}

}
