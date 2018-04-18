package com.zendaimoney.thirdpp.notify.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.notify.action.Action;
import com.zendaimoney.thirdpp.notify.action.BizActionTarget;
import com.zendaimoney.thirdpp.notify.annotations.BizActionAnnotation;

public class BizActionUtil {

	/**
	 * 获取带有@ReqActionAnnotation类，map的key规则: BizType
	 */
	public static Map<String, BizActionTarget> getBizActionTarget() {
		Map<String, BizActionTarget> targetMap = new HashMap<String, BizActionTarget>();
		Class<? extends Action> actionClass = Action.class;
		List<Class<? extends Action>> list = ClassUtil.getClassPathClasses(
				ClassUtil.ITSM_PACKAGE, true, actionClass);
		for (Class<? extends Action> clazz : list) {
			BizActionAnnotation annotation = clazz
					.getAnnotation(BizActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			// 业务类型
			BizType bizType = annotation.bizType();

			BizActionTarget target = new BizActionTarget(clazz, bizType);

			addTargetMap(targetMap, generateKey(bizType), target);
		}
		return targetMap;
	}

	private static void addTargetMap(Map<String, BizActionTarget> targetMap,
			String key, BizActionTarget target) {
		if (targetMap.get(key) != null) {
			throw new RuntimeException(
					"add action  error. action reqActionAnnotation definition is not unique: "
							+ target.getBizType().getCode() + " class : "
							+ target.getClass().getName());
		}
		targetMap.put(key, target);
	}

	/**
	 * map key生成器
	 * 
	 * @param bizType
	 * @param thirdType
	 * @return
	 */
	public static String generateKey(BizType bizType) {
		return bizType.getCode();
	}

	public static void main(String[] args) {
		Map<String, BizActionTarget> targetMap = getBizActionTarget();
		for (Entry<String, BizActionTarget> entry : targetMap.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().getActionClazz().getName());
		}
	}

}
