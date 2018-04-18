package com.zendaimoney.trust.channel.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.action.BatchActionTarget;
import com.zendaimoney.trust.channel.action.QueryActionAbstract;
import com.zendaimoney.trust.channel.action.QueryActionTarget;
import com.zendaimoney.trust.channel.action.TradeActionAbstract;
import com.zendaimoney.trust.channel.action.TradeActionTarget;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;

/**
 * Action归集工具类
 * @author mencius
 *
 */
public class ReqActionUtil {
	
	//key分隔符
	public static final String KEY_SEPARATOR = ".";

	/**
	 * 获取带有@ReqActionAnnotation类，map的key规则: CmbBizType+KEY_SEPARATOR+CmbCategory
	 */
	public static Map<String, TradeActionTarget> getTradeActionTarget() {
		Map<String, TradeActionTarget> targetMap = new HashMap<String, TradeActionTarget>();
		Class<? extends TradeActionAbstract> actionClass = TradeActionAbstract.class;
		List<Class<? extends TradeActionAbstract>> list = ClassUtil.getClassPathClasses(ClassUtil.ITSM_PACKAGE, true, actionClass);
		for (Class<? extends TradeActionAbstract> clazz : list) {
			ReqActionAnnotation annotation = clazz.getAnnotation(ReqActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			
			//通道编码
			ThirdType thirdType = annotation.thirdType();
			
			//业务类型
			TrustBizType[] cmbBizTypes = annotation.cmbBizType();
			
			//渠道分类
			TrustCategory cmbCategory = annotation.cmbCategory();
			
			for (TrustBizType cmbBizType : cmbBizTypes) {
				
				TradeActionTarget target = new TradeActionTarget(clazz, thirdType, cmbBizType, cmbCategory);
				
				addTradeTargetMap(targetMap,generateKey(cmbBizType, cmbCategory),target);
			}
			
		}
		return targetMap;
	}
	
	
	/**
	 * 获取带有@ReqActionAnnotation类，map的key规则: CmbBizType+KEY_SEPARATOR+CmbCategory
	 */
	public static Map<String, QueryActionTarget> getQueryActionTarget() {
		Map<String, QueryActionTarget> targetMap = new HashMap<String, QueryActionTarget>();
		Class<? extends QueryActionAbstract> actionClass = QueryActionAbstract.class;
		List<Class<? extends QueryActionAbstract>> list = ClassUtil.getClassPathClasses(ClassUtil.ITSM_PACKAGE, true, actionClass);
		for (Class<? extends QueryActionAbstract> clazz : list) {
			ReqActionAnnotation annotation = clazz.getAnnotation(ReqActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			
			//通道编码
			ThirdType thirdType = annotation.thirdType();
			
			//业务类型
			TrustBizType[] cmbBizTypes = annotation.cmbBizType();
			
			//渠道分类
			TrustCategory cmbCategory = annotation.cmbCategory();
			
			for (TrustBizType cmbBizType : cmbBizTypes) {
				
				QueryActionTarget target = new QueryActionTarget(clazz, thirdType, cmbBizType, cmbCategory);
				
				addQueryTargetMap(targetMap,generateKey(cmbBizType, cmbCategory),target);
			}
			
		}
		return targetMap;
	}

	/**
	 * 单笔交易处理Action
	 * @param targetMap 
	 * @param key
	 * @param target
	 */
	private static void addTradeTargetMap(Map<String, TradeActionTarget> targetMap, String key, TradeActionTarget target) {
		if (targetMap.get(key) != null) {
			throw new RuntimeException("add action  error. action reqActionAnnotation definition is not unique: " + target.getCmbBizType().getCode()+KEY_SEPARATOR+target.getCmbCategory().getCode()
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(key, target);
	}
	
	/**
	 * 单笔查询处理Action
	 * @param targetMap 
	 * @param key
	 * @param target
	 */
	private static void addQueryTargetMap(Map<String, QueryActionTarget> targetMap, String key, QueryActionTarget target) {
		if (targetMap.get(key) != null) {
			throw new RuntimeException("add action  error. action reqActionAnnotation definition is not unique: " + target.getCmbBizType().getCode()+KEY_SEPARATOR+target.getCmbCategory().getCode()
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(key, target);
	}
	
	/**
	 * 获取带有@ReqActionAnnotation类，map的key规则: CmbBizType+KEY_SEPARATOR+CmbCategory
	 */
	public static Map<String, BatchActionTarget> getBatchActionTarget() {
		Map<String, BatchActionTarget> targetMap = new HashMap<String, BatchActionTarget>();
		Class<? extends BatchActionAbstract> actionClass = BatchActionAbstract.class;
		List<Class<? extends BatchActionAbstract>> list = ClassUtil.getClassPathClasses(ClassUtil.ITSM_PACKAGE, true, actionClass);
		for (Class<? extends BatchActionAbstract> clazz : list) {
			ReqActionAnnotation annotation = clazz.getAnnotation(ReqActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			
			//通道编码
			ThirdType thirdType = annotation.thirdType();
			
			//业务类型
			TrustBizType[] cmbBizTypes = annotation.cmbBizType();
			
			//渠道分类
			TrustCategory cmbCategory = annotation.cmbCategory();
			
			for (TrustBizType cmbBizType : cmbBizTypes) {
				
				BatchActionTarget target = new BatchActionTarget(clazz, thirdType, cmbBizType, cmbCategory);
				
				addBatchTargetMap(targetMap,generateKey(cmbBizType, cmbCategory),target);
			}
			
		}
		return targetMap;
	}

	
	private static void addBatchTargetMap(Map<String, BatchActionTarget> targetMap, String key, BatchActionTarget target) {
		if (targetMap.get(key) != null) {
			throw new RuntimeException("add action  error. action reqActionAnnotation definition is not unique: " + target.getCmbBizType().getCode()+KEY_SEPARATOR+target.getCmbCategory().getCode()
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(key, target);
	}
	
	/**
	 * map key生成器
	 * @param cmbBizType
	 * @param cmbCategory
	 * @return
	 */
	public static String generateKey(TrustBizType cmbBizType, TrustCategory cmbCategory){
		return cmbBizType.getCode() + KEY_SEPARATOR + cmbCategory.getCode();
	}

	public static void main(String[] args) {
//		Map<String, TradeActionTarget> targetMap = getTradeActionTarget();
//		for (Entry<String, TradeActionTarget> entry : targetMap.entrySet()) {
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue().getActionClazz().getName());
//		}
		
		
		Map<String, QueryActionTarget> targetMap = getQueryActionTarget();
		for (Entry<String, QueryActionTarget> entry : targetMap.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().getActionClazz().getName());
		}
	}

}
