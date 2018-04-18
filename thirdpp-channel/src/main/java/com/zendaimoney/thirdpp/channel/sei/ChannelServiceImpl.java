package com.zendaimoney.thirdpp.channel.sei;

import com.zendaimoney.thirdpp.channel.action.DispatchAction;
import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.filter.BankRequestFilter;
import com.zendaimoney.thirdpp.channel.filter.ChannelRequestFilter;
import com.zendaimoney.thirdpp.channel.pub.service.IChannelService;
import com.zendaimoney.thirdpp.channel.pub.vo.*;
import com.zendaimoney.thirdpp.channel.service.RequestService;
import com.zendaimoney.thirdpp.channel.util.*;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;

public class ChannelServiceImpl implements IChannelService {

	private static final LogPrn logger = new LogPrn(ChannelServiceImpl.class);

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ChannelRequestFilter channelRequestFilter;
	
	@Autowired
	private BankRequestFilter bankRequestFilter ;
	
	@Override
	public Response collectCommond(CollectReqVo collectReqVo) {

		Response response = null;
		Request request = null;

		// 记录request-初始化状态
		try {
			
			// 进行通道过滤处理 
			response = channelRequestFilter.doFilter(collectReqVo);
			// 如果过滤器中存在错误code，则返回中间未知状态
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				
				// 将响应CODE设置为交易异常
				response.setCode(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());
				
				return response;
			}
			
			
			response = bankRequestFilter.bankFilter(collectReqVo, collectReqVo.getPayerBankCode());
			// 如果过滤器中存在错误code，则返回中间未知状态
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				
				// 将响应CODE设置为交易异常
				response.setCode(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());
				
				return response;
			}
						
			// 初始化request
			request = new Request(collectReqVo);
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeReqAction(collectReqVo,
				ChannelCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	/**
	 * 协议支付签约短信触发
	 * @param signMsgReqVo
	 * @return
	 */
	@Override
	public Response signMessageCommond(SignMsgReqVo signMsgReqVo){
		Response response = null;
		Request request = null;

		try{
			// 进行通道过滤处理
			response = channelRequestFilter.doFilter(signMsgReqVo);
			// 如果过滤器中存在错误code，则返回中间未知状态
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				// 将响应CODE设置为交易异常
				response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());

				return response;
			}

			// 初始化request
			request = new Request(signMsgReqVo);
			requestService.insert(request);
		}catch (Exception e){
			logger.error("协议支付签约短信触发异常", e);
			// 返回交易异常-返回码:333333,必须返回该码
			return new Response(Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
		}

		response = dispatchAction.executeReqAction(signMsgReqVo, ChannelCategory.SIGN_MESSAGE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	/**
	 * 协议支付签约
	 * @param signReqVo
	 * @return
	 */
	@Override
	public Response signCommond(SignReqVo signReqVo){
		Response response = null;
		Request request = null;

		try{
			// 进行通道过滤处理
			response = channelRequestFilter.doFilter(signReqVo);
			// 如果过滤器中存在错误code，则返回中间未知状态
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				// 将响应CODE设置为交易异常
				response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());

				return response;
			}

			// 初始化request
			request = new Request(signReqVo);
			requestService.insert(request);
		}catch (Exception e){
			logger.error("协议支付签约异常", e);
			// 返回交易异常-返回码:333333,必须返回该码
			return new Response(Constants.TppConstants.TRADE_STATE_FAILER.getCode(), PlatformErrorCode.DB_ERROR.getDefaultMessage());
		}

		response = dispatchAction.executeReqAction(signReqVo, ChannelCategory.SIGN);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	public Response queryCommond(QueryReqVo queryReqVo) {
		Response response = null;
		Request request = null;
					
		try {
			
			// 进行通道过滤处理 
			response = channelRequestFilter.doFilter(queryReqVo);
			// 如果过滤器中存在错误code，则返回中间未知状态
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				
				// 将响应CODE设置为中间状态
				response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE.getCode());
				return response;
			}
			
			// 初始化request
			request = new Request(queryReqVo);
			logger.info("channelCategory:" + ChannelCategory.QUERY + ",request:"
					+ JSONHelper.bean2json(request));
			response = dispatchAction.executeReqAction(queryReqVo,
					ChannelCategory.QUERY);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易处理中-返回码:222222,必须返回该码
			response = new Response(
					Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
					ExceptionUtil.getExceptionMessage(e));
			return response;
		}

		// 将非交易成功、交易失败两种状态统一转化成交易处理中状态
		response = ResponseUtil.convert2UniqeMidCode(response);

		return response;
	}

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
	
	public void setBankRequestFilter(BankRequestFilter bankRequestFilter) {
		this.bankRequestFilter = bankRequestFilter;
	}

	/**
	 * 代付
	 */
	@Override
	public Response payCommond(PayReqVo payReqVo) {

		Response response = null;
		Request request = null;

		// 记录request-初始化状态
		try {
			
			// 进行通道过滤处理 
			response = channelRequestFilter.doFilter(payReqVo);
			// 如果过滤器中存在错误code，则返回中间未知状态
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				
				// 将响应CODE设置为交易异常
				response.setCode(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());
				
				logger.info("response code:" + response.getCode() + " msg:" + response.getMsg() + ",tradeFlow:" + payReqVo.getTradeFlow());
				return response;
			}
			
			// 初始化request
			request = new Request(payReqVo);
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			
			logger.info("response code:" + response.getCode() + " msg:" + response.getMsg() + ",tradeFlow:" + payReqVo.getTradeFlow());
			return response;
		}
		response = dispatchAction.executeReqAction(payReqVo,
				ChannelCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}


}
