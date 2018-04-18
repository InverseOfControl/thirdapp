package com.zendaimoney.thirdpp.channel.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zendaimoney.thirdpp.channel.action.Action;
import com.zendaimoney.thirdpp.channel.action.ReqActionTarget;
import com.zendaimoney.thirdpp.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;

public class ReqActionUtil {
	
	//key分隔符
	public static final String KEY_SEPARATOR = ".";

	/**
	 * 获取带有@ReqActionAnnotation类，map的key规则: BizType+KEY_SEPARATOR+ThirdType
	 */
	public static Map<String, ReqActionTarget> getReqActionTarget() {
		Map<String, ReqActionTarget> targetMap = new HashMap<String, ReqActionTarget>();
		Class<? extends Action> actionClass = Action.class;
		List<Class<? extends Action>> list = ClassUtil.getClassPathClasses(ClassUtil.ITSM_PACKAGE, true, actionClass);
		for (Class<? extends Action> clazz : list) {
			ReqActionAnnotation annotation = clazz.getAnnotation(ReqActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			//业务类型
			BizType bizType = annotation.bizType();
			//第三方通道编码
			ThirdType thirdType = annotation.thirdType();
			
			//渠道分类
			ChannelCategory channelCategory = annotation.channelCategory();
			
			ReqActionTarget target = new ReqActionTarget(clazz,bizType,thirdType,channelCategory);
			
			addTargetMap(targetMap,generateKey(bizType,thirdType,channelCategory),target);
		}
		return targetMap;
	}


	private static void addTargetMap(Map<String, ReqActionTarget> targetMap, String key, ReqActionTarget target) {
		if (targetMap.get(key) != null) {
			throw new RuntimeException("add action  error. action reqActionAnnotation definition is not unique: " + target.getBizType().getCode()+KEY_SEPARATOR+target.getThirdType().getCode()
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(key, target);
	}
	
	
	/**
	 * map key生成器
	 * @param bizType
	 * @param thirdType
	 * @return
	 */
	public static String generateKey(BizType bizType,ThirdType thirdType,ChannelCategory channelCategory){
		return bizType.getCode() + KEY_SEPARATOR + thirdType.getCode() + KEY_SEPARATOR + channelCategory.getCode();
	}

	public static void main(String[] args) {
		Map<String, ReqActionTarget> targetMap = getReqActionTarget();
		for (Entry<String, ReqActionTarget> entry : targetMap.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().getActionClazz().getName());
		}
	}

}
