package com.zendaimoney.trust.channel.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.proxy.TrustSubject;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ReqChannelActionUtil;

/**
 * 招商银行通道分发器
 * @author mencius
 *
 */
public class DispatchChannel implements ApplicationContextAware{
	
	private static final LogPrn logger = new LogPrn(DispatchChannel.class);
	
	private ApplicationContext applicationContext;

	
	// 通道实现类
	@SuppressWarnings("rawtypes")
	public static Map<String, Class> reqChannelTargetMap;

	public void initMap() {
		
		reqChannelTargetMap = ReqChannelActionUtil.getChannelActionTarget();
	}

	/**
	 * 实例化实现类
	 * @param thirdType
	 * @param trustCategory
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public TrustSubject executeTradeSubject(ThirdType thirdType, TrustCategory trustCategory) {
		
		TrustSubject trustSubject = null;
		String errorMsg = "";
		
		// 目标实现类
		Class target = reqChannelTargetMap.get(ReqChannelActionUtil.generateKey(thirdType, trustCategory));
		if (target == null) {
			errorMsg = "request match action error. action method is not found. thirdType : " + thirdType.getCode() + ",trustCategory: " + trustCategory.getCode();
			logger.error(errorMsg);
			// 通道实现类不存在
			throw new PlatformException(PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR, PlatformErrorCode.CHANNEL_NOT_FOUND_ERROR.getDefaultMessage());
		}
		try {
			
			// 通过Spring实例化通道实现处理类
			trustSubject = (TrustSubject)applicationContext.getBean(target.getName());
			
		} catch (Exception e) {
			
			errorMsg = "instance action error : " + target.getName();
			logger.error(errorMsg,e);
			// 通道实现类初始化创建失败异常
			throw new PlatformException(PlatformErrorCode.CHANNEL_START_ERROR, PlatformErrorCode.CHANNEL_START_ERROR.getDefaultMessage());
		}
		
		return trustSubject;
	}
	

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
