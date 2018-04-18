package com.zendaimoney.trust.channel.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.annotations.ReqChannelAnnotation;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;

/**
 * Channel通道实现类归集工具类
 * @author mencius
 *
 */
public class ReqChannelActionUtil {
	
	public static final String ITSM_PACKAGE = "com.zendaimoney.trust.channel.sei";
	
	//key分隔符
	public static final String KEY_SEPARATOR = ".";

	/**
	 * 获取带有@ReqChannelAnnotation类，map的key规则: thirdType
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Class> getChannelActionTarget() {
		Map<String, Class> targetMap = new HashMap<String, Class>();
		List<Class> list = ClassUtil.getClassClasses(ITSM_PACKAGE, true);
		for (Class clazz : list) {
			ReqChannelAnnotation annotation = (ReqChannelAnnotation) clazz.getAnnotation(ReqChannelAnnotation.class);
			if (annotation == null) {
				continue;
			}
			
			// 通道编码
			ThirdType thirdType = annotation.thirdType();
			
			// 请求类别
			TrustCategory trustCategory = annotation.trustCategory(); 
			
			
			addTradeTargetMap(targetMap, generateKey(thirdType, trustCategory),clazz);
		}
		return targetMap;
	}

	/**
	 * 单笔交易(查询)处理Action
	 * @param targetMap 
	 * @param key
	 * @param target
	 */
	@SuppressWarnings("rawtypes")
	private static void addTradeTargetMap(Map<String, Class> targetMap, String key, Class target) {
		if (targetMap.get(key) != null) {
			throw new RuntimeException("add action  error. action reqActionAnnotation definition is not unique: " + key + " class : " + target.getClass().getName());
		}
		targetMap.put(key, target);
	}
	
	/**
	 * map key生成器
	 * @param cmbBizType
	 * @param cmbCategory
	 * @return
	 */
	public static String generateKey(ThirdType thirdType, TrustCategory trustCategory){
		return thirdType.getCode() + KEY_SEPARATOR + trustCategory.getCode();
	}
	

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Map<String, Class> targetMap = getChannelActionTarget();
		for (Entry<String, Class> entry : targetMap.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().getName());
		}
	}

}
