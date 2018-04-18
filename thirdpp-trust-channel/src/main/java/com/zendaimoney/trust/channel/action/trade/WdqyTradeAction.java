package com.zendaimoney.trust.channel.action.trade;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.QueryActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.communication.Message;
import com.zendaimoney.trust.channel.entity.CashDraw;
import com.zendaimoney.trust.channel.entity.MessageDto;
import com.zendaimoney.trust.channel.entity.MessageInfo;
import com.zendaimoney.trust.channel.entity.Request;
import com.zendaimoney.trust.channel.entity.TradeResult;
import com.zendaimoney.trust.channel.entity.cmb.Header;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.req.WdqyReq;
import com.zendaimoney.trust.channel.pub.vo.resp.CommonResp;
import com.zendaimoney.trust.channel.service.MessageService;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.service.TradeResultService;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.Constants.CmbStatus;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.ExceptionUtil;
import com.zendaimoney.trust.channel.util.JSONHelper;
import com.zendaimoney.trust.channel.util.LogPrn;

/**
 * 提现请求查询-交易
 * @author 00233197
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbBizType = TrustBizType.WDQY, cmbCategory = TrustCategory.TRADE)
@Component("com.zendaimoney.trust.channel.action.trade.WdqyTradeAction")
public class WdqyTradeAction extends QueryActionAbstract {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(WdqyTradeAction.class);
	
	@Autowired
	private RequestService requestService;

	// 报文service
	@Autowired
	private MessageService messageService;
	
	// 交易结果service
	@Autowired
	private TradeResultService tradeResultService;
	
	@Override
	protected TrustBizReqVo preProcess(TrustBizReqVo vo)
			throws PlatformException {
		
		WdqyReq wdqyReq = null;
		//Request request = null;
		try {
			// 请求对象转换
			wdqyReq = (WdqyReq) vo;
		} catch (Exception e) {
			
			logger.error("====", e);
			// 设置request-状态为“请求报文解析失败”
//			request = new Request(vo.getChannelReqId(),
//					MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(),
//					ExceptionUtil.getExceptionPartyMessage(e));
//			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.VO_2_DTO_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return wdqyReq;
	}

	/**
	 * 解析报文体
	 */
	@Override
	protected MessageDto parseMessage(Message message, TrustBizReqVo bizReqVo)
			throws PlatformException {
		MessageDto messageDto = null;
		CommonResp wdqyResp = null;
		//Request request = null;
		Header header = null;
		try {
			
			// 报文头
			header = parseHeadMessage(message, bizReqVo);
			
			// 通讯返回码，例如暂停交易，报文校验错等。非CMBMB99时报文体直接原接收报文体返回，不做任何处理
			if (Constants.CmbStatus.CMBMB99.getCode().equals(header.getRetCode())) {
				
				wdqyResp = new CommonResp();
				// 解析响应报文
				ConvertUtils.messageToObj(message, wdqyResp);
			}
			
			// 解析完之后 将报文头和报文体赋值在报文传输对象上
			messageDto = new MessageDto(header, wdqyResp);
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“响应报文解析失败”
//			request = new Request(message.getChannelReqId(),
//					MessageStatus.RESPONSE_PARSE_ERROR.getCode(),
//					ExceptionUtil.getExceptionPartyMessage(e));
//			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.CMB_PARSE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return messageDto;
	}

	
	/**
	 * 接收socket响应失败
	 */
	@Override
	protected Response communicationResponseFailHandler(Message message,
			TrustBizReqVo bizReqVo) {
		// 返回调用方为交易失败
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		//Request request = null;

		// 发送报文后相关处理(修改Request状态-请求报文发送失败)
		try {
			// 设置request-状态为“请求报文发送失败”
//			request = new Request(message.getChannelReqId(),
//					MessageStatus.SEND_REQUEST_ERROR.getCode(),
//					message.getMessage());
//			requestService.update(request);
		} catch (Exception e) {
			logger.error("==============", e);
		}
		return response;
	}

	@Override
	protected Response communicationSuccessHandler(MessageDto messageDto,
			Message message, TrustBizReqVo bizReqVo) {
		// 初始化响应结果
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		Header header = messageDto.getHeader();
		CommonResp resp = (CommonResp) messageDto.getRespVO();
		//Request request = null;
		
		CashDraw cashDraw = null;
		
		// 交易结果
		TradeResult tradeResult = null;
		
		try {
			
			// header通道返回码
			String retCode = (header == null ? "" : header.getRetCode());
			
			// 交易状态码
			String tradeCode = (resp == null ? "" : resp.getRetCode());
			
			// 通道返回信息
			String retMsg = (resp == null ? "" : resp.getMsg());
			
			// 设置银行返回码
			setBankResponseCode(response,retCode,tradeCode);
			
			// 首先判断报文头 响应码是否为 CMBMB99，响应成功
			if (Constants.CmbStatus.CMBMB99.getCode().equals(retCode)) {
				
				// 成功(交易状态码为 CMBMB99)
				if (Constants.CmbStatus.CMBMB99.getCode().equals(tradeCode)) {
					response.setCode(Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode());
					response.setMsg(StringUtils.isBlank(retMsg) ? Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc() : retMsg);
					
					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(), "", bizReqVo.getBizType().getCode(), tradeCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode(),
							message.getChannelReqId());
					
					// 提现处理对象
					cashDraw = new CashDraw(bizReqVo.getTradeFlow(), Constants.DRAW_STATUS_SUCCESS, null, null, null, null,null);
					// 业务更新处理对象
					finalResultHandler(null, cashDraw, message, response, bizReqVo);
					
					
					// 交易处理中
				} else if(StringUtils.isBlank(tradeCode) || Constants.CmbStatus.CMBMBXX.getCode().equals(tradeCode)
						|| Constants.CmbStatus.CMBMB05.getCode().equals(tradeCode)) {
					
					CmbStatus cmbStatus = Constants.CmbStatus.get(tradeCode);
					response.setCode(Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode());
					response.setMsg(StringUtils.isBlank(retMsg) ? (cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_MIDDLE.getDesc() : cmbStatus.getDesc()) : retMsg);
					
					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(), "", bizReqVo.getBizType().getCode(), tradeCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
							message.getChannelReqId());
					// 业务更新处理对象
					//midResultHandler(null, message, bizReqVo);
				} else {
					
					CmbStatus cmbStatus = Constants.CmbStatus.get(tradeCode);
					response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
					response.setMsg(StringUtils.isBlank(retMsg) ? (cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_FAILER.getDesc() : cmbStatus.getDesc()) : retMsg);
					
					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(), "", bizReqVo.getBizType().getCode(), tradeCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
							message.getChannelReqId());
					// 提现处理对象
					cashDraw = new CashDraw(bizReqVo.getTradeFlow(), Constants.DRAW_STATUS_FAIL, null, null, null, null,null);
					// 业务更新处理对象
					finalResultHandler(null, cashDraw, message, response, bizReqVo);
					
				}
			} else {
				CmbStatus cmbStatus = Constants.CmbStatus.get(retCode);
				response.setCode(Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode());
				response.setMsg(cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_MIDDLE.getDesc() : cmbStatus.getDesc());
				
				tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
						message.getTradeSn(), "", bizReqVo.getBizType().getCode(), retCode,
						(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
								: ""),
						Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
						message.getChannelReqId());
				// 业务更新处理对象
				//midResultHandler(null, message, bizReqVo);
			}
			
		} catch (Exception e) {
			logger.error("==============", e);
		}
		logger.info("查询结果:"+tradeResult.toString());
		return response;
	}

	/**
	 * 交易失败的处理
	 */
	@Override
	protected Response parseMessageFailHandler(Message message,
			TrustBizReqVo bizReqVo) {
		// 返回调用方为失败
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		return response;
	}
	
	
	/**
	 * 通信失败的处理
	 */
	@Override
	protected Response communicationThrowExceptionHandler(Message message,
			TrustBizReqVo bizReqVo) {
		// 返回调用方为交易失败
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		Request request = null;

		// 发送报文后相关处理(修改Request状态-请求报文发送失败)
		try {
			// 设置request-状态为“请求报文发送失败”
//			request = new Request(message.getChannelReqId(),
//					MessageStatus.SEND_REQUEST_ERROR.getCode(),
//					message.getMessage());
//			requestService.update(request);
		} catch (Exception e) {
			logger.error("==============", e);
		}

		return response;
	}
	
	
	/**
	 * 交易结果最终态的业务处理
	 * @param tradeResult
	 * @param message
	 * @param response
	 * @param bizReqVo
	 */
	private void finalResultHandler(TradeResult tradeResult, CashDraw cashDraw, Message message, Response response,
			TrustBizReqVo bizReqVo) {
		try {
			tradeResultService.finalResultHandler(tradeResult, cashDraw);
		} catch (Exception e) {
			// 返回状态为-交易中间态
			response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER
					.getCode());
			response.setMsg(Constants.CmbConstants.TRADE_STATE_FAILER
					.getDesc());
			logger.error("交易最终结果处理异常", e);
		}
	}
	
	
	/**
	 * 中间结果处理
	 * 
	 * @param tradeResult
	 * @param message
	 * @param response
	 */
	private void midResultHandler(TradeResult tradeResult, Message message,
			TrustBizReqVo bizReqVo) {
		try {
			tradeResultService.insert(tradeResult);
		} catch (Exception e) {
			logger.error("交易中间结果处理异常", e);
		}
	}
	
}
