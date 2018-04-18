package com.zendaimoney.trust.channel.util;

import java.util.HashMap;
import java.util.Map;

import com.zendaimoney.trust.channel.entity.SysInfoCategory;
import com.zendaimoney.trust.channel.entity.SysThirdChannelInfo;

public class ThirdPPCacheContainer {

	/**
	 * TPP银行编码映射到第三方银行编码
	 * */
	public static final Map<String, String> tppBankCodeToThirdBankCodeMap = new HashMap<String, String>();

	/**
	 * TPP银行编码映射到第三方银行名称
	 * */
	public static final Map<String, String> tppBankCodeToThirdBankNameMap = new HashMap<String, String>();

	/**
	 * 第三方银行编码映射到TPP银行编码
	 * */
	public static final Map<String, String> thirdBankCodeToTppBankCodeMap = new HashMap<String, String>();
	/**
	 * TPP银行名称映射TPP银行编码
	 * */
	public static final Map<String, String> thirdBankNameToTppBankCodeMap = new HashMap<String, String>();

	/**
	 * TPP币种映射到第三方币种
	 * */
	public static final Map<String, String> tppCurrencyToThirdCurrencyMap = new HashMap<String, String>();

	/**
	 * 第三方币种映射到TPP币种
	 * */
	public static final Map<String, String> thirdCurrencyToTppCurrencyMap = new HashMap<String, String>();

	/**
	 * TPP银行卡类型映射到第三方银行卡类型
	 * */
	public static final Map<String, String> tppBankCardTypeToThirdBankCardTypeMap = new HashMap<String, String>();

	/**
	 * 第三方银行卡类型映射到TPP银行卡类型
	 * */
	public static final Map<String, String> thirdBankCardTypeToTppBankCardTypeMap = new HashMap<String, String>();

	
	/**
	 * TPP证件类型映射到第三方证件类型
	 * */
	public static final Map<String, String> tppIdTypeToThirdIdTypeMap = new HashMap<String, String>();
	
	
	/**
	 * TPP系统信息类别
	 * */
	public static final Map<String, SysInfoCategory> sysInfoCategoryMap = new HashMap<String, SysInfoCategory>();
	
	/**
	 * TPP系统第三方通道信息
	 * */
	public static final Map<String, SysThirdChannelInfo> sysThirdChannelInfoMap = new HashMap<String, SysThirdChannelInfo>();
	
	
	/**
	 * TPP系统查询模块配置信息
	 * */
	public static final Map<String, String> sysQueryInfoMap = new HashMap<String, String>();
	
	
	/**
	 * 第三方银行状态信息
	 * */
	public static final Map<String, Integer> thirdBankStatusMap = new HashMap<String, Integer>();
	
	/**
	 * TPP对公对私类型映射到第三方对公对私类型
	 * */
	public static final Map<String, String> tppPubAndPriTothirdUserTypeMap = new HashMap<String, String>();
	
	/**
	 * TPP用户类型映射到第三方用户类型
	 * */
	public static final Map<String, String> tppUserTypeTothirdUserTypeMap = new HashMap<String, String>();
	
	/**
	 * 指令映射到交易类型
	 * */
	public static final Map<String, String> tppTradeTypeTothirdTradeTypeMap = new HashMap<String, String>();


}
