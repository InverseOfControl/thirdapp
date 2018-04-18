package com.zendaimoney.trust.channel.sei.Impl;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.DispatchAction;
import com.zendaimoney.trust.channel.annotations.ReqChannelAnnotation;
import com.zendaimoney.trust.channel.annotations.ReqMethodAnnotation;
import com.zendaimoney.trust.channel.entity.Request;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.filter.ChannelRequestFilter;
import com.zendaimoney.trust.channel.proxy.TrustSubject;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.sei.CmbQueryCommandService;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ResponseUtil;

/**
 * 资金托管：招商银行命令调用实现类
 * 
 * @author mencius
 * thirdType 通道编码
 * trustCategory 请求类别
 */
@ReqChannelAnnotation(thirdType = ThirdType.CMBPAY, trustCategory = TrustCategory.QUERY)
@Component("com.zendaimoney.trust.channel.sei.Impl.CmbQueryCommandServiceImpl")
public class CmbQueryCommandServiceImpl implements CmbQueryCommandService,
		TrustSubject {

	private static final LogPrn logger = new LogPrn(
			CmbQueryCommandServiceImpl.class);

	@Autowired
	private RequestService requestService;

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;


	public void setDispatchAction(DispatchAction dispatchAction) {
		this.dispatchAction = dispatchAction;
	}

	public void setRequestService(RequestService requestService) {
		this.requestService = requestService;
	}

	public void setChannelRequestFilter(
			ChannelRequestFilter channelRequestFilter) {
		this.channelRequestFilter = channelRequestFilter;
	}

	/**
	 * 代理主题执行器
	 * @param trustBizReqVo
	 * @return
	 */
	@Override
	public Response execute(TrustBizReqVo trustBizReqVo) {

		try {

			// 通过java反射机制 动态执行被代理主题实现类的具体方法
//			return (Response) getTargetMethod(trustBizReqVo.getTrustBizType())
//					.invoke(this, new Object[] { trustBizReqVo });

			return queryCommand(trustBizReqVo);
		} catch (Throwable e) {

			logger.error(e.getMessage(), e);
			throw new PlatformException(PlatformErrorCode.SYSTEM_BUSY,
					PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage());
		}
	}

	/**
	 * 获取通道实现类 的业务调用方法
	 * 
	 * @param trustBizType
	 * @return method
	 */
	public Method getTargetMethod(TrustBizType trustBizType) {

		// 获取当前类的method信息数组
		Method[] methods = this.getClass().getDeclaredMethods();

		// 遍历
		for (Method method : methods) {
			method.setAccessible(true);

			ReqMethodAnnotation reqMethodAnnotation = method
					.getAnnotation(ReqMethodAnnotation.class);

			if (reqMethodAnnotation == null)
				continue;

			if (trustBizType.equals(reqMethodAnnotation.trustBizType())) {
				return method;
			}
		}

		throw new PlatformException(
				PlatformErrorCode.CHANNEL_BIZ_NOT_FOUND_ERROR,
				PlatformErrorCode.CHANNEL_BIZ_NOT_FOUND_ERROR
						.getDefaultMessage());

	}

	@Override
	public Response queryCommand(TrustBizReqVo bizReqVo) {
		
		Response response = null;
		Request request = null;

		// 记录request-初始化状态
		try {

			 // 进行通道过滤处理
			 response = channelRequestFilter.doFilter(bizReqVo);
			
			 // 如果过滤器中存在错误code，则返回中间未知状态
			 if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
			
				 // 将响应CODE设置为交易异常
				 response.setCode(Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode());
				 return response;
			 }

			// 初始化request
			request = new Request(bizReqVo);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.QUERY);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

}
