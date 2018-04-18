package com.zendaimoney.thirdpp.channel.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.itrus.cryptorole.bc.RecipientBcImpl;
import com.itrus.cryptorole.bc.SenderBcImpl;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;

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
	public static final Map<String, String> tppUserTypeTothirdUserTypeMap = new HashMap<String, String>();

	/**
	 * 第三方快捷通加密对象
	 */
	public static final Map<String,SenderBcImpl> kjtSenderMap = new HashMap<>();

	/**
	 * 第三方快捷通解密对象
	 */
	//public static final Map<String,RecipientBcImpl> kjtRecipientMap = new HashMap<>();


}
