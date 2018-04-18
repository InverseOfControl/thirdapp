package com.zendaimoney.trust.channel.sei.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.DispatchAction;
import com.zendaimoney.trust.channel.annotations.ReqChannelAnnotation;
import com.zendaimoney.trust.channel.entity.Request;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.filter.ChannelRequestFilter;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.service.CmbBatchCommandService;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ResponseUtil;

/**
 * 对外暴露：批量(生成/解析)文件处理命令接口
 * @author mencius
 *
 */
@ReqChannelAnnotation(thirdType = ThirdType.CMBPAY, trustCategory = TrustCategory.BATCH_TRADE)
@Component("com.zendaimoney.trust.channel.sei.Impl.CmbBatchCommandServiceImpl")
public class CmbBatchCommandServiceImpl implements
		CmbBatchCommandService {
	
	private static final LogPrn logger = new LogPrn(CmbBatchCommandServiceImpl.class);

	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private DispatchAction dispatchAction;
	
	@Autowired
	private ChannelRequestFilter channelRequestFilter;

	/**
	 * 构建批量报文文件
	 * @param data 报文批数据
	 * @param cmbBizType 招商银行业务类型
	 * @return Response
	 */
	@Override
	public Response build(TrustBizReqVo trustBizReqVo) {
		Response response = null;
		Request request = null;
		// 记录request-初始化状态
		/*try {
			
			// 初始化request
			request = new Request(trustBizReqVo);
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}*/
		
		// 经由业务处理分发器执行生产报文文件操作
		response = dispatchAction.executeBuildReqAction(trustBizReqVo, TrustCategory.BATCH_TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);
		
		return response;
	}
	
	
	/**
	 * 下载报文文件
	 * @param cmbBizType 招商银行业务类型
	 * @return Response
	 */
	@Override
	public Response pull(TrustBizReqVo trustBizReqVo) {
		Response response = null;
		Request request = null;
		// 记录request-初始化状态
		/*try {
			
			// 初始化request
			request = new Request(trustBizReqVo);
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}*/
		
		// 经由业务处理分发器执行批量解析操作
		response = dispatchAction.executePullReqAction(trustBizReqVo, TrustCategory.BATCH_TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);
		
		return response;
	}

	/**
	 * 解析批量报文文件
	 * @param cmbBizType 招商银行业务类型
	 * @return Response
	 */
	@Override
	public Response parse(TrustBizReqVo trustBizReqVo) {
		Response response = null;
		Request request = null;
		// 记录request-初始化状态
		/*try {
			
			// 初始化request
			request = new Request(trustBizReqVo);
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}*/
		
		// 经由业务处理分发器执行批量解析操作
		response = dispatchAction.executeParseReqAction(trustBizReqVo, TrustCategory.BATCH_TRADE);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);
		
		return response;
	}
	
	public void setChannelRequestFilter(
			ChannelRequestFilter channelRequestFilter) {
		this.channelRequestFilter = channelRequestFilter;
	}
	
	public void setDispatchAction(DispatchAction dispatchAction) {
		this.dispatchAction = dispatchAction;
	}
	
	public void setRequestService(RequestService requestService) {
		this.requestService = requestService;
	}

}
