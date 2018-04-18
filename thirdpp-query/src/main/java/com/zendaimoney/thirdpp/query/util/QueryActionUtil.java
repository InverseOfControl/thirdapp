package com.zendaimoney.thirdpp.query.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.query.action.Action;
import com.zendaimoney.thirdpp.query.action.annotations.QueryActionAnnotation;
import com.zendaimoney.thirdpp.query.thread.QueryActionTarget;

public class QueryActionUtil {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(QueryActionUtil.class);
		
	public static Map<String, QueryActionTarget> targetMap = new HashMap<String, QueryActionTarget>();
	
	/**
	 * 获取带有@ReqActionAnnotation类
	 */
	@SuppressWarnings("rawtypes")
	public void init() {
		
		logger.info("Action加载开始...");
		Class<? extends Action> actionClass = Action.class;
		List<Class<? extends Action>> list = ClassUtil.getClassPathClasses(ClassUtil.ITSM_PACKAGE, true, actionClass);
		for (Class<? extends Action> clazz : list) {
			QueryActionAnnotation annotation = clazz.getAnnotation(QueryActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			//业务类型
			BizType bizType = annotation.bizType();
			
			QueryActionTarget target = new QueryActionTarget(clazz,bizType);
			
			addTargetMap(bizType.getCode(),target);
			
			logger.info(target.getActionClazz());
		}
		
		for (Entry<String, QueryActionTarget> entry : targetMap.entrySet()) {
			logger.info(entry.getKey() + ":");
			logger.info(entry.getValue().getActionClazz().getName());
		}
		logger.info("Action加载完成...");
		
	}


	private static void addTargetMap(String key, QueryActionTarget target) {
		if (targetMap.get(key) != null) {
			throw new RuntimeException("add action  error. action transferActionAnnotation definition is not unique: " + target.getBizType().getCode()
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(key, target);
	}
	
	public static Map<String, QueryActionTarget> getTargetMap() {
		return targetMap;
	}


	public static void main(String[] args) {
		
		QueryActionUtil util = new QueryActionUtil();
		util.init();
		for (Entry<String, QueryActionTarget> entry : targetMap.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().getActionClazz().getName());
		}
	}

}
