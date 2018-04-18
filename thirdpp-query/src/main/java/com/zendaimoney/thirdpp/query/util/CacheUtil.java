package com.zendaimoney.thirdpp.query.util;

import java.math.BigDecimal;

import com.ymkj.base.cache.redis.client.ICacheClient;
import com.zendaimoney.thirdpp.query.entity.CollectInfo;

public class CacheUtil {
	private static final LogPrn logger = new LogPrn(CacheUtil.class);
	private static String Class = CacheUtil.class.getName();

	/**
	 *  添加金额
	 * 
	 * @param bizReqVo
	 * @param cacheClient
	 * @param amount
	 * @param thirdTypeNo
	 */
	public static void addAmount(ICacheClient cacheClient,CollectInfo collect){
		logger.info(Class + "addAmount Start");
		
		try {
			// 通道编码+商户编码
			String key = collect.getPaySysNo() + "_" + collect.getSpare2();
			if(cacheClient != null){
				
				if (null == collect.getAmount()) {
					collect.setAmount(new BigDecimal("0"));
				}
				
				cacheClient.incrByFloatMonth(key, collect.getAmount().doubleValue());
			} else {
				logger.info("=========Key:"+ key);
	            logger.error("添加金额, redis服务异常");
			}
			
		} catch (Exception e) {
			logger.error("====添加金额异常=====", e);
		}
	    logger.info(Class + "addAmount End");
	}
	
	

	/**
	 * 增加失败次数
	 * 
	 * @param iCacheClient
	 * @param thirdTypeNo
	 * @param cardNo
	 * @param failTimes
	 */
	public static void addFailTimes(ICacheClient iCacheClient,CollectInfo collect) {

		logger.info(Class + "addFailTimes Start");

		// 通道+银行卡
		String key = collect.getPaySysNo() + "_" + collect.getPayerBankCardNo();
		try {
			if (iCacheClient != null) {

				iCacheClient.incrCurrDay(key);
			} else {

				logger.info("=========Key:" + key);
				logger.error("获取失败次数, redis服务异常");
			}
		} catch (Exception e) {
			logger.info("=========Key:" + key);
			logger.error("=====增加失败次数异常======", e);
		}

		logger.info(Class + "addFailTimes End");
	}

}
