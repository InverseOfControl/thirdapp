package com.zendaimoney.thirdpp.trade.util;

import java.util.Map;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.trade.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.CollectReqVo;
import com.zendaimoney.thirdpp.trade.util.Constants.ChannelRulesConstants;

public class ChannelRulesUtils {
	
	// 必须执行业务系统的划扣通道
	private static String BIZSYS_CAHNNEL_REQUIRED_YES = "1";
	
	/**
	 * 根据通道规则获取支付通道编码
	 * @param channelrules
	 * @param map
	 * @param vo
	 * @return
	 */
	public static String getPaymentChannel(SysInfoCategory sysInfoCategory,Map<String, String> map, CollectReqVo vo){
		String channelrules = sysInfoCategory.getChannelRules();
		String paymentChannel = null;
		// 是否强制执行业务系统的指定通道(业务系统控制)
		if(BIZSYS_CAHNNEL_REQUIRED_YES.equals(vo.getSpare1())){
			paymentChannel = getPaymentChannelByBizSys(vo);
		// 按照业务系统指定通道策略(TPP配置控制)	
		}else if(ChannelRulesConstants.CHANNEL_RULES_BIZSYS.getCode().equals(channelrules)){
			paymentChannel = getPaymentChannelByBizSys(vo);
		// 按照银行指定通道策略(TPP配置控制)	
		}else if(ChannelRulesConstants.CHANNEL_RULES_BANKS.getCode().equals(channelrules)){
			paymentChannel = getPaymentChannelByBanks(sysInfoCategory,map,vo);
		// 按路由规则指定通道策略(TPP配置控制)	 通道编码：999	
		}else if(ChannelRulesConstants.CHANNEL_RULES_ROUTE.getCode().equals(channelrules)){
			paymentChannel = ThirdType.ROUTEPAY.getCode();
		}else{
			paymentChannel = null;
		}
		
		return paymentChannel;
	}
	/**
	 * 按照业务系统指定通道
	 * @param vo
	 * @return
	 */
	public static String getPaymentChannelByBizSys(CollectReqVo vo){
		return vo.getPaySysNo();
	}
	/**
	 * 按照银行指定通道
	 * @param sysInfoCategory
	 * @param map
	 * @param vo
	 * @return
	 */
	public static String getPaymentChannelByBanks(SysInfoCategory sysInfoCategory,Map<String, String> map,CollectReqVo vo){
		String paySysNo = null;
		String paymentChannel = map.get(vo.getPayerBankCode());
		// 银行通道配置是否为空
		if(paymentChannel!=null && !paymentChannel.trim().equals("")){
			paySysNo = paymentChannel;
		}
		// 业务系统传的通道值是否为空
		if(paySysNo==null){
			paySysNo =  vo.getPaySysNo();
		}
		// 信息类别配置通道是否为空
		if(paySysNo==null || "".equals(paySysNo.trim())){
			paySysNo = sysInfoCategory.getPaymentChannel();
		}
		return paySysNo;
	}

}
