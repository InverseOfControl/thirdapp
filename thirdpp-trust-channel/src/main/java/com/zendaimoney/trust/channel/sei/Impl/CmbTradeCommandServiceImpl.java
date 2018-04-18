package com.zendaimoney.trust.channel.sei.Impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
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
import com.zendaimoney.trust.channel.pub.vo.resp.ChrgResp;
import com.zendaimoney.trust.channel.pub.vo.resp.SchgResp;
import com.zendaimoney.trust.channel.pub.vo.resp.WtdrResp;
import com.zendaimoney.trust.channel.sei.CmbTradeCommandService;
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
@ReqChannelAnnotation(thirdType = ThirdType.CMBPAY, trustCategory = TrustCategory.TRADE)
@Component("com.zendaimoney.trust.channel.sei.Impl.CmbTradeCommandServiceImpl")
public class CmbTradeCommandServiceImpl implements CmbTradeCommandService,
		TrustSubject {

	private static final LogPrn logger = new LogPrn(
			CmbTradeCommandServiceImpl.class);

	@Autowired
	private RequestService requestService;

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	/**
	 * 用户开户
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	@ReqMethodAnnotation(trustBizType = TrustBizType.USRA)
	public Response usraCommand(TrustBizReqVo bizReqVo) throws Exception {
		
		AttachmentResponse<String> attachmentResponse = new AttachmentResponse<>();
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		BeanUtils.copyProperties(attachmentResponse, response);
		return attachmentResponse;
	}

	/**
	 * 文件处理指令(FILE)
	 */
	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.FILE)
	public Response fileCommand(TrustBizReqVo bizReqVo) {
		Response response = null;

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

		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.USRE)
	public Response usreCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.USRB)
	public Response usrbCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	/**
	 * 银行卡绑定结果查询(UBQY)
	 */
	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.UBQY)
	public Response ubqyCommand(TrustBizReqVo bizReqVo) {
		Response response = null;
		//Request request = null;

		// 记录request-初始化状态
		try {

			 // 进行通道过滤处理
			 response = channelRequestFilter.doFilter(bizReqVo);
			
			 // 如果过滤器中存在错误code，则返回中间未知状态
			 if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
			
				 // 将响应CODE设置为交易异常
				 response.setCode(Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode());
				 return response;
			 }
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易处理中-返回码:222222,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
					PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeQueryReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeMidCode(response);

		return response;
	}

	/**
	 * 用户解绑银行卡(USRJ)
	 */
	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.USRJ)
	public Response usrjCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	/**
	 * 用户关户(USRD)
	 */
	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.USRD)
	public Response usrdCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	/**
	 * 三方充值请求(CHRG)
	 */
	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.CHRG)
	public Response chrgCommand(TrustBizReqVo bizReqVo) throws Exception {
		AttachmentResponse<ChrgResp> attachmentResponse = new AttachmentResponse<ChrgResp>();
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		BeanUtils.copyProperties(attachmentResponse, response);
		return attachmentResponse;
	}

	@Override
	public Response scqyCommand(TrustBizReqVo bizReqVo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 自助转账充值请求(SCHG)
	 */
	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.SCHG)
	public Response schgCommand(TrustBizReqVo bizReqVo) throws Exception{
		AttachmentResponse<SchgResp> attachmentResponse = new AttachmentResponse<SchgResp>();
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		BeanUtils.copyProperties(attachmentResponse, response);
		return attachmentResponse;
	}

	/**
	 * 提现请求(WTDR)
	 */
	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.WTDR)
	public Response wtdrCommand(TrustBizReqVo bizReqVo) throws Exception{
		AttachmentResponse<WtdrResp> attachmentResponse = new AttachmentResponse<WtdrResp>();
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		BeanUtils.copyProperties(attachmentResponse, response);
		return attachmentResponse;
	}

	@Override
	public Response wdrsCommand(TrustBizReqVo bizReqVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.WDQY)
	public Response wdqyCommand(TrustBizReqVo bizReqVo) {
		Response response = null;

		// 记录request-初始化状态
		try {

			 // 进行通道过滤处理
			 response = channelRequestFilter.doFilter(bizReqVo);
			
			 // 如果过滤器中存在错误code，则返回中间未知状态
			 if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
			
				 // 将响应CODE设置为交易处理中
				 response.setCode(Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode());
				 return response;
			 }
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易处理中-返回码:222222,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
					PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeQueryReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeMidCode(response);

		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.MCHG)
	public Response mchgCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.MDRW)
	public Response mdrwCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	public Response mintCommand(TrustBizReqVo bizReqVo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 登记散标(PROA)
	 */
	@ReqMethodAnnotation(trustBizType = TrustBizType.PROA)
	@Override
	public Response proaCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	/**
	 * 关闭散标(PROC)
	 */
	@ReqMethodAnnotation(trustBizType = TrustBizType.GROA)
	@Override
	public Response groaCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	/**
	 * 关闭散标(PROC)
	 */
	@ReqMethodAnnotation(trustBizType = TrustBizType.PROC)
	@Override
	public Response procCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	/**
	 * 关闭团(GROC)
	 */
	@ReqMethodAnnotation(trustBizType = TrustBizType.GROC)
	@Override
	public Response grocCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@ReqMethodAnnotation(trustBizType = TrustBizType.FROZ)
	@Override
	public Response frozCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@ReqMethodAnnotation(trustBizType = TrustBizType.UNFR)
	@Override
	public Response unfrCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.INVS)
	@Override
	public Response invsCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.REPY)
	@Override
	public Response repyCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.CASM)
	@Override
	public Response casmCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.MFEE)
	@Override
	public Response mfeeCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.MPAY)
	@Override
	public Response mpayCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.MADV)
	@Override
	public Response madvCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.MREG)
	@Override
	public Response mregCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.GINN)
	@Override
	public Response ginnCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.GOUT)
	@Override
	public Response goutCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.GCHG)
	@Override
	public Response gchgCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.GDRW)
	@Override
	public Response gdrwCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.ETPM)
	@Override
	public Response etpmCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.OADV)
	@Override
	public Response oadvCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.OREG)
	@Override
	public Response oregCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	@ReqMethodAnnotation(trustBizType = TrustBizType.MRSL)
	@Override
	public Response mrslCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.SCCK)
	public Response scckCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.DCCK)
	public Response dcckCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.BCCK)
	public Response bcckCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.BAQY)
	public Response baqyCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);
		return response;
	}

	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.TRQY)
	public Response trqyCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);
		return response;
	}
	
	@Override
	@ReqMethodAnnotation(trustBizType = TrustBizType.RCQY)
	public Response rcqyCommand(TrustBizReqVo bizReqVo) {
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
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeTradeReqAction(bizReqVo,
				TrustCategory.TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);
		return response;
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
			return (Response) getTargetMethod(trustBizReqVo.getTrustBizType())
					.invoke(this, new Object[] { trustBizReqVo });

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


}
