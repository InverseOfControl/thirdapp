package com.zendaimoney.thirdpp.account.util;

import java.util.Map;

import com.zendaimoney.thirdpp.account.action.account.AccountActionTarget;
import com.zendaimoney.thirdpp.account.action.download.DownloadActionTarget;
import com.zendaimoney.thirdpp.account.action.parse.FileParseActionTarget;
import com.zendaimoney.thirdpp.account.filter.bizsys.BizsysFilterTarget;
import com.zendaimoney.thirdpp.account.filter.channel.ChannelFilterTarget;

public class ActionMapperUtil {

	public static Map<String, DownloadActionTarget> downloadActionMap = ActionUtil.getDownloadActionTarget();
	
	public static Map<String, FileParseActionTarget> fileParseActionMap = ActionUtil.getFileParseActionTarget();
	
	public static Map<Integer, ChannelFilterTarget> channelFilterMap = ActionUtil.getChannelFilterTarget();
	
	public static Map<Integer, BizsysFilterTarget> bizsysFilterMap = ActionUtil.getBizsysFilterTarget();
	
	public static Map<String, AccountActionTarget> accountActionMap = ActionUtil.getAccountActionTarget();
	
}
