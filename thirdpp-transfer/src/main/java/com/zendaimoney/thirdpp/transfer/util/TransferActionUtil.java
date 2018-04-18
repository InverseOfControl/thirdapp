package com.zendaimoney.thirdpp.transfer.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.transfer.action.Action;
import com.zendaimoney.thirdpp.transfer.action.TransferActionTarget;
import com.zendaimoney.thirdpp.transfer.annotations.TransferActionAnnotation;

public class TransferActionUtil {
	

	/**
	 * 获取带有@ReqActionAnnotation类
	 */
	public static Map<String, TransferActionTarget> getTransferActionTarget() {
		Map<String, TransferActionTarget> targetMap = new HashMap<String, TransferActionTarget>();
		Class<? extends Action> actionClass = Action.class;
		List<Class<? extends Action>> list = ClassUtil.getClassPathClasses(ClassUtil.ITSM_PACKAGE, true, actionClass);
		for (Class<? extends Action> clazz : list) {
			TransferActionAnnotation annotation = clazz.getAnnotation(TransferActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			//业务类型
			BizType bizType = annotation.bizType();
			
			TransferActionTarget target = new TransferActionTarget(clazz,bizType);
			
			addTargetMap(targetMap,bizType.getCode(),target);
		}
		return targetMap;
	}


	private static void addTargetMap(Map<String, TransferActionTarget> targetMap, String key, TransferActionTarget target) {
		if (targetMap.get(key) != null) {
			throw new RuntimeException("add action  error. action transferActionAnnotation definition is not unique: " + target.getBizType().getCode()
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(key, target);
	}
	
	


	public static void main(String[] args) {
		Map<String, TransferActionTarget> targetMap = getTransferActionTarget();
		for (Entry<String, TransferActionTarget> entry : targetMap.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().getActionClazz().getName());
		}
	}

}
