package com.zendaimoney.thirdpp.alarm.common.constant;

public interface StateCode {
	//1:正常发送,2:手机号码错误,3:邮件地址错误,4:客户不存在,5:参数值有误,6:黑名单,7:重复短信,8:email无法发送,9:超时不发送,10:被屏蔽
	public static final String success = "1";// 1:正常发送
	public static final String mobileError = "2";// 2:手机号码错误
	public static final String mailError = "3";// 3:邮件地址错误
	public static final String noExists = "4";// 4:客户不存在
	public static final String paramError = "5";// 5:参数值有误
	public static final String black = "6";// 6:黑名单
	public static final String smsRepeat = "7";// 7:重复短信
	public static final String mailNoSend = "8";// 8:email无法发送
	public static final String timeout = "9";// 9:超时不发送
	public static final String noSend = "6";// 10:被屏蔽,暂时和黑名单一样
}
