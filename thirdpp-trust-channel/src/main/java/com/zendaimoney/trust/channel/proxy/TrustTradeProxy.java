package com.zendaimoney.trust.channel.proxy;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.DispatchChannel;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.LogPrn;

/**
 * 代理模式对象
 * @author mencius
 *
 */
public class TrustTradeProxy implements TrustSubject {
	
	private static final LogPrn logger = new LogPrn(TrustTradeProxy.class);

	// 代理主题抽象类
	private TrustSubject trustTradeSubject;
	
	/**
	 * 代理模式对象 构造器(动态代理)
	 * @param cmbBizReqVo
	 * @param dispatchChannel
	 */
	public TrustTradeProxy(ThirdType thirdType, DispatchChannel dispatchChannel, TrustCategory trustCategory) {
		logger.info("代理动态创建被代理的实现对象");
		// 被代理主题实现类
		this.trustTradeSubject = dispatchChannel.executeTradeSubject(thirdType, trustCategory);
	}
	
	/**
	 * 代理模式对象 处理入口
	 */
	@Override
	public Response execute(TrustBizReqVo cmbBizReqVo) {
		
		return trustTradeSubject.execute(cmbBizReqVo);
	}
	
	
}
