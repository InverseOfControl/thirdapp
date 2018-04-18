package com.zendaimoney.trust.channel.sei.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.DispatchChannel;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.proxy.TrustTradeProxy;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.service.TrustChannelService;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.LogPrn;

/**
 * 资金托管：对外暴露接口实现类
 * @author mencius
 *
 */
public class TrustChannelServiceImpl implements TrustChannelService {
	
	private static final LogPrn logger = new LogPrn(TrustChannelServiceImpl.class);
	
	/**
	 * 通道分发器
	 */
	@Autowired
	private DispatchChannel dispatchChannel;
	
	/**
	 * 交易命令
	 */
	@Override
	public Response tradeCommand(TrustBizReqVo trustBizReqVo) {
		
		Response response = null;
		
		try {
			
			// 请求类型：交易(动态生成代理)
			TrustTradeProxy trustTradeProxy = new TrustTradeProxy(trustBizReqVo.getThirdType(), dispatchChannel, TrustCategory.TRADE);
			
			response = trustTradeProxy.execute(trustBizReqVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.TRUST_PROXY_ERROR.getDefaultMessage());
			return response;
		}
		
		return response;
	}

	/**
	 * 查询命令
	 */
	@Override
	public Response queryCommand(TrustBizReqVo trustBizReqVo) {
		
		Response response = null;
		
		try {
			
			// 请求类型：查询(动态生成代理)
			TrustTradeProxy trustTradeProxy = new TrustTradeProxy(trustBizReqVo.getThirdType(), dispatchChannel, TrustCategory.QUERY);
			
			response = trustTradeProxy.execute(trustBizReqVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.TRUST_PROXY_ERROR.getDefaultMessage());
			return response;
		}
		
		return response;
	}
	
	/**
	 * 批量处理命令
	 */
	@Override
	public Response batchCommand(TrustBizReqVo trustBizReqVo) {
		
		Response response = null;
		
		try {
			
			// 请求类型：批量(动态生成代理)
			TrustTradeProxy trustTradeProxy = new TrustTradeProxy(trustBizReqVo.getThirdType(), dispatchChannel, TrustCategory.BATCH_TRADE);
			
			response = trustTradeProxy.execute(trustBizReqVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.TRUST_PROXY_ERROR.getDefaultMessage());
			return response;
		}
		
		return response;
	}

	public void setDispatchChannel(DispatchChannel dispatchChannel) {
		this.dispatchChannel = dispatchChannel;
	}
}
