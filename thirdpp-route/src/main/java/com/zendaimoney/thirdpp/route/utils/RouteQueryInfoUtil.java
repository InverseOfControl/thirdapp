package com.zendaimoney.thirdpp.route.utils;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ymkj.base.cache.redis.client.ICacheClient;
import com.zendaimoney.thirdpp.route.RouteCacheContainer;
import com.zendaimoney.thirdpp.route.entity.RouteChannelCostEntity;
import com.zendaimoney.thirdpp.route.entity.SysThirdChannelInfoEntity;
import com.zendaimoney.thirdpp.route.entity.ThirdFieldMapperEntity;

/**
 * 缓存信息获取
 * 
 * @author YM10188
 * @version $Id: RouteQueryInfoUtil.java, v 0.1 2017年6月13日 上午10:48:28 YM10188 Exp $
 */
public class RouteQueryInfoUtil {


	// 日志工具类
	public static Log logger = LogFactory.getLog(RouteQueryInfoUtil.class);
    /**
     * 
     * 
     * @param thirdTypeNo 通道编码
     * @param infoCategoryCode 信息类别
     * @return
     */
    public static SysThirdChannelInfoEntity getSysThirdChannelInfoEntity(String thirdTypeNo,String infoCategoryCode){
        //Key 第三方通道码 + "_" + 商户类型
        //商户类型
        String merchantType = RouteCacheContainer.infoCategories.get(infoCategoryCode);
        String key = thirdTypeNo + "_" + merchantType;
        return RouteCacheContainer.thirdChannelInfos.get(key);
    }
    
    /**
     * 
     * 
     * @param thirdTypeNo
     * @param bankCode 银行编码
     * @return
     */
    public static ThirdFieldMapperEntity getThirdFieldMapperEntity(String thirdTypeNo,String bankCode){
     // 第三方通道码 + "_" + 银行编码
        String key = thirdTypeNo + "_" + bankCode;
        return RouteCacheContainer.thirdFieldMappers.get(key);
    }
    
    //获取成本费率列表
    public static List<RouteChannelCostEntity> getCostEntity(String thirdTypeNo){
        return  RouteCacheContainer.channelCosts.get(thirdTypeNo);
    }
    
    //获取商户的沉淀量 （数据库）
    public static BigDecimal getPrecipitationDB(String thirdTypeNo,String infoCategoryCode){
        SysThirdChannelInfoEntity thirdChannel = RouteQueryInfoUtil.getSysThirdChannelInfoEntity(thirdTypeNo, infoCategoryCode);
        if(thirdChannel == null){
            return  new BigDecimal(0);
         }
        // 第三方通道码 + "_" + 商户号
        String key = thirdTypeNo+"_"+thirdChannel.getCertificateNo();
        BigDecimal b = RouteCacheContainer.precipitations.get(key);
        return  b != null?b:new BigDecimal(0);
    }
    
   //获取商户的沉淀量 （redis）
    public static BigDecimal getPrecipitationCache(ICacheClient iCacheClient,String thirdTypeNo,String infoCategoryCode) throws Exception{
        SysThirdChannelInfoEntity thirdChannel = RouteQueryInfoUtil.getSysThirdChannelInfoEntity(thirdTypeNo, infoCategoryCode);
        //通道+商户号
        BigDecimal precipitation = null;
        if(thirdChannel == null){
           return  precipitation = new BigDecimal(0);
        }
        String key = thirdTypeNo+"_"+thirdChannel.getCertificateNo();
        String s = null;
        try {
			
        if(iCacheClient != null){
            s = iCacheClient.get(key);
        }else {
            ZDLogger logger   = LogUtil.getInstance();
            ZDLogDto zdLogDto = new ZDLogDto(RouteQueryInfoUtil.class);
            logger.error(zdLogDto.clear().setTitleInfo("获取商户的沉淀量").setMsg("redis服务异常"));
        }
        if(StringUtil.isEmpty(s)){
            precipitation = new BigDecimal(0);
        }else {
            precipitation = new BigDecimal(s);
        }
        } catch (Exception e) {
        	precipitation = new BigDecimal(0);
        	logger.error("====获取商户的沉淀量失败=====", e);
		}
        return  precipitation;
    }
    
    //获取失败次数 （redis）
    public static Integer getFailTimesCache(ICacheClient iCacheClient,String thirdTypeNo,String cardNo) throws Exception{
        //通道+银行卡
        Integer times = null;
        String key = thirdTypeNo+"_"+cardNo;
        String s = null;
     try{
        if(iCacheClient != null){
            s = iCacheClient.get(key);
        }else {
            ZDLogger logger   = LogUtil.getInstance();
            ZDLogDto zdLogDto = new ZDLogDto(RouteQueryInfoUtil.class);
            logger.error(zdLogDto.clear().setTitleInfo("获取失败次数 ").setMsg("redis服务异常"));
        }
        
        if(StringUtil.isEmpty(s)){
            times = 0;
        }else {
            times = Integer.parseInt(s);
        }
    } catch (Exception e) {
    	times = 0;
		logger.error("====获取失败次数失败=====", e);
	}
        return  times;
    }
    

}
