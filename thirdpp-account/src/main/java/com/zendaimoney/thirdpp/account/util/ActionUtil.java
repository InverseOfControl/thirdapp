package com.zendaimoney.thirdpp.account.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zendaimoney.thirdpp.account.action.account.AccountAction;
import com.zendaimoney.thirdpp.account.action.account.AccountActionTarget;
import com.zendaimoney.thirdpp.account.action.download.DownloadAction;
import com.zendaimoney.thirdpp.account.action.download.DownloadActionTarget;
import com.zendaimoney.thirdpp.account.action.parse.FileParseAction;
import com.zendaimoney.thirdpp.account.action.parse.FileParseActionTarget;
import com.zendaimoney.thirdpp.account.annotation.AccountAnnotation;
import com.zendaimoney.thirdpp.account.annotation.FileParseActionAnnotation;
import com.zendaimoney.thirdpp.account.annotation.BizsysFilterAnnotation;
import com.zendaimoney.thirdpp.account.annotation.ChannelFilterAnnotation;
import com.zendaimoney.thirdpp.account.annotation.DownloadActionAnnotation;
import com.zendaimoney.thirdpp.account.filter.bizsys.BizsysFilter;
import com.zendaimoney.thirdpp.account.filter.bizsys.BizsysFilterTarget;
import com.zendaimoney.thirdpp.account.filter.channel.ChannelFilter;
import com.zendaimoney.thirdpp.account.filter.channel.ChannelFilterTarget;

public class ActionUtil {
	
	/**
	 * 获取带有@DownloadActionAnnotation类
	 */
	public static Map<String, DownloadActionTarget> getDownloadActionTarget() {
		Map<String, DownloadActionTarget> targetMap = new HashMap<String, DownloadActionTarget>();
		Class<? extends DownloadAction> actionClass = DownloadAction.class;
		List<Class<? extends DownloadAction>> list = ClassUtil.getClassPathClasses(ClassUtil.ACTION_ITSM_PACKAGE, true, actionClass);
		for (Class<? extends DownloadAction> clazz : list) {
			DownloadActionAnnotation annotation = clazz.getAnnotation(DownloadActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			//获取对账文件的方式
			String fetchMethod = annotation.fetchMethod();
			
			DownloadActionTarget target = new DownloadActionTarget(clazz,fetchMethod);
			
			addDownloadTargetMap(targetMap,fetchMethod,target);
		}
		return targetMap;
	}
	
	/**
	 * 获取带有@AccountActionAnnotation类
	 */
	public static Map<String, FileParseActionTarget> getFileParseActionTarget() {
		Map<String, FileParseActionTarget> targetMap = new HashMap<String, FileParseActionTarget>();
		Class<? extends FileParseAction> actionClass = FileParseAction.class;
		List<Class<? extends FileParseAction>> list = ClassUtil.getClassPathClasses(ClassUtil.ACTION_ITSM_PACKAGE, true, actionClass);
		for (Class<? extends FileParseAction> clazz : list) {
			FileParseActionAnnotation annotation = clazz.getAnnotation(FileParseActionAnnotation.class);
			if (annotation == null) {
				continue;
			}
			//获取对账文件的方式
			String fileSuffix = annotation.fileSuffix();
			FileParseActionTarget target = new FileParseActionTarget(clazz,fileSuffix);
			addAccountTargetMap(targetMap,fileSuffix,target);
		}
		return targetMap;
	}
	
	public static Map<Integer, ChannelFilterTarget> getChannelFilterTarget() {
		Map<Integer, ChannelFilterTarget> targetMap = new HashMap<Integer, ChannelFilterTarget>();
		Class<? extends ChannelFilter> filterClass = ChannelFilter.class;
		List<Class<? extends ChannelFilter>> list = ClassUtil.getClassPathClasses(ClassUtil.FILTER_ITSM_PACKAGE, true, filterClass);
		for (Class<? extends ChannelFilter> clazz : list) {
			ChannelFilterAnnotation annotation = clazz.getAnnotation(ChannelFilterAnnotation.class);
			if (annotation == null) {
				continue;
			}
			//获取对账文件的方式
			int filterStep = annotation.filterStep();
			ChannelFilterTarget target = new ChannelFilterTarget(clazz,filterStep);
			addChannelFilterTargetMap(targetMap,filterStep,target);
		}
		return targetMap;
	}
	
	public static Map<Integer, BizsysFilterTarget> getBizsysFilterTarget() {
		Map<Integer, BizsysFilterTarget> targetMap = new HashMap<Integer, BizsysFilterTarget>();
		Class<? extends BizsysFilter> filterClass = BizsysFilter.class;
		List<Class<? extends BizsysFilter>> list = ClassUtil.getClassPathClasses(ClassUtil.FILTER_ITSM_PACKAGE, true, filterClass);
		for (Class<? extends BizsysFilter> clazz : list) {
			BizsysFilterAnnotation annotation = clazz.getAnnotation(BizsysFilterAnnotation.class);
			if (annotation == null) {
				continue;
			}
			//获取对账文件的方式
			int filterStep = annotation.filterStep();
			BizsysFilterTarget target = new BizsysFilterTarget(clazz,filterStep);
			addBizsysFilterTargetMap(targetMap,filterStep,target);
		}
		return targetMap;
	}
	
	public static Map<String, AccountActionTarget> getAccountActionTarget() {
		Map<String, AccountActionTarget> targetMap = new HashMap<String, AccountActionTarget>();
		Class<? extends AccountAction> accountClass = AccountAction.class;
		List<Class<? extends AccountAction>> list = ClassUtil.getClassPathClasses(ClassUtil.ACTION_ITSM_PACKAGE, true, accountClass);
		for (Class<? extends AccountAction> clazz : list) {
			AccountAnnotation annotation = clazz.getAnnotation(AccountAnnotation.class);
			if (annotation == null) {
				continue;
			}
			//获取对账文件的方式
			String bizType = annotation.bizType();
			AccountActionTarget target = new AccountActionTarget(clazz,bizType);
			addAccountActionTargetMap(targetMap,bizType,target);
		}
		return targetMap;
	}


	private static void addDownloadTargetMap(Map<String, DownloadActionTarget> targetMap, String fetchMethod, DownloadActionTarget target) {
		if (targetMap.get(fetchMethod) != null) {
			throw new RuntimeException("add action  error. action ActionAnnotation definition is not unique: " + fetchMethod
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(fetchMethod, target);
	}
	
	private static void addAccountTargetMap(Map<String, FileParseActionTarget> targetMap, String fileSuffix, FileParseActionTarget target) {
		if (targetMap.get(fileSuffix) != null) {
			throw new RuntimeException("add action  error. action ActionAnnotation definition is not unique: " + fileSuffix
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(fileSuffix, target);
	}
	
	private static void addChannelFilterTargetMap(Map<Integer, ChannelFilterTarget> targetMap, int filterStep, ChannelFilterTarget target) {
		if (targetMap.get(filterStep) != null) {
			throw new RuntimeException("add action  error. action ActionAnnotation definition is not unique: " + filterStep
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(filterStep, target);
	}
	
	private static void addBizsysFilterTargetMap(Map<Integer, BizsysFilterTarget> targetMap, int filterStep, BizsysFilterTarget target) {
		if (targetMap.get(filterStep) != null) {
			throw new RuntimeException("add action  error. action ActionAnnotation definition is not unique: " + filterStep
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(filterStep, target);
	}
	
	private static void addAccountActionTargetMap(Map<String, AccountActionTarget> targetMap, String bizType, AccountActionTarget target) {
		if (targetMap.get(bizType) != null) {
			throw new RuntimeException("add action  error. action ActionAnnotation definition is not unique: " + bizType
					+ " class : " + target.getClass().getName());
		}
		targetMap.put(bizType, target);
	}
	
}
