package com.zendaimoney.thirdpp.transfer.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.zendaimoney.thirdpp.transfer.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.transfer.entity.SysThirdChannelInfo;

public class ThirdPPCacheContainer {
	
	/**
	 * TPP系统信息类别
	 * */
	public static final Map<String, SysInfoCategory> sysInfoCategoryMap = new HashMap<String, SysInfoCategory>();
	
	/**
	 * TPP系统第三方通道信息
	 * */
	public static final Map<String, SysThirdChannelInfo> sysThirdChannelInfoMap = new HashMap<String, SysThirdChannelInfo>();

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
	 * TPP银行编码映射到第三方支付银行额度(代收业务)
	 * */
	public static final Map<String, BigDecimal> tppBankCodeToThirdBankCollectMaxMoneyMap = new HashMap<String, BigDecimal>();

	/**
	 * TPP银行编码映射到第三方支付银行额度(代付业务)
	 * */
	public static final Map<String, BigDecimal> tppBankCodeToThirdBankPayMaxMoneyMap = new HashMap<String, BigDecimal>();

	/**
	 * TPP银行编码映射到第三方支付银行额度(快捷支付业务)
	 * */
	public static final Map<String, BigDecimal> tppBankCodeToThirdBankQucikPayMaxMoneyMap = new HashMap<String, BigDecimal>();

	/**
	 * TPP系统查询模块配置信息
	 * */
	public static final Map<String, String> sysQueryInfoMap = new HashMap<String, String>();
}
