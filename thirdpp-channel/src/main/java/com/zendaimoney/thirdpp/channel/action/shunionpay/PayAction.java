package com.zendaimoney.thirdpp.channel.action.shunionpay;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.action.Action;
import com.zendaimoney.thirdpp.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.communication.shunionpay.ShunionpayCommunication;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.shunionpay.pay.trade.PayReq;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.shunionpay.pay.trade.PayResp;
import com.zendaimoney.thirdpp.channel.entity.MessageInfo;
import com.zendaimoney.thirdpp.channel.entity.PayInfo;
import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.entity.TradeResult;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.PayReqVo;
import com.zendaimoney.thirdpp.channel.service.MessageService;
import com.zendaimoney.thirdpp.channel.service.PayInfoService;
import com.zendaimoney.thirdpp.channel.service.RequestService;
import com.zendaimoney.thirdpp.channel.service.TradeResultService;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.Constants.MessageStatus;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayConstants;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayConstants.ShunionpayStatus;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 银联代付
 * @author mencius
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.SHUNIONPAY, bizType = BizType.BROKER_PAY, channelCategory = ChannelCategory.TRADE)
@Component("com.zendaimoney.thirdpp.channel.action.shunionpay.PayAction")
public class PayAction extends Action {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(PayAction.class);
	
	//请求流水service
	@Autowired
	private RequestService requestService;

	// 报文service
	@Autowired
	private MessageService messageService;
	
	// 交易结果service
	@Autowired
	private TradeResultService tradeResultService;

	// 代付交易明细service
	@Autowired
	private PayInfoService payInfoService;
	
	@Autowired
	private ShunionpayCommunication shunionpayCommunication;
	
	@Override
	protected ReqDto preProcess(BizReqVo vo) throws PlatformException {
		ReqDto dto = null;
		Request request = null;
		try {
			dto = new PayReq(vo);
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“请求报文解析失败”
			request = new Request(vo.getChannelReqId(),
					MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.VO_2_DTO_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return dto;
	}

	@Override
	protected Message buildMessage(ReqDto dto, BizReqVo vo)
			throws PlatformException {
		Message message = new Message();
		Request request = null;
		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = null;
		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = null;

		PayReq payReq = (PayReq) dto; 
		try {
			infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap.get(vo
					.getInfoCategoryCode());

			channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo
					.getThirdType().getCode() + infoCategory.getMerchantType());
			// 报文请求地址
			message.setUrl(ConfigUtil.getInstance().getShunionpayConfig().getPayingUrl());

			// 组装签名前报文
			String signMessage = ShunionpayUtil
					.buildSignMessage(
							ShunionpayConstants.shunionpaypayingSignPropertyNameArray,
							ShunionpayUtil.xmlToMap(dto.encode()));
			
			// 获取银联通道证书签名路径
			String path= ConfigUtil.getInstance().getShunionpayConfig().getKeyPath() + channelInfo.getCertName();
			
			// 签名之后的报文
			String sign = ShunionpayUtil.Sign(signMessage, channelInfo, path);
			payReq.setChkValue(sign);

			// 请求报文，需要加密
			message.setRequestInfo(payReq.encode());

			// 请求参数对象Map
			Map<String, String> paraMap = ShunionpayUtil.xmlToMap(message
					.getRequestInfo());
			message.setMessageMap(paraMap);

			// 把TPP通道reqid放到Message.提供respHandler方法中
			message.setChannelReqId(dto.getChannelReqId());

			// 设置交易流水号
			message.setTradeSn(((PayReqVo) vo).getTradeFlow());
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“请求报文解析失败”
			request = new Request(dto.getChannelReqId(),
					MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.DTO_ENCODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return message;
	}

	@Override
	protected Message process(BizReqVo vo, ReqDto dto) throws PlatformException {
		Message message = null;
		Request request = null;
		MessageInfo messageInfo = null;
		// 拼装报文
		message = buildMessage(dto, vo);
		// 准备发送报文前相关处理(修改Request状态-请求报文已发送、记录发送报文)
		try {
			// 设置request-状态为“请求报文已发送”
			request = new Request(dto.getChannelReqId(),
					MessageStatus.SEND_REQUEST.getCode());
			requestService.update(request);
			
			PayReqVo payReqVo = (PayReqVo) vo;
			// 记录请求报文
			messageInfo = new MessageInfo(dto.getChannelReqId(),
					MessageInfo.MSG_TYPE_Q, message.getRequestInfo(), dto
							.getThirdType().getCode(), payReqVo.getTradeFlow());
			
			// 如果报文需要记录的话
			if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance()
					.getShunionpayConfig().getMsgInStorage())) {
				
				messageService.insert(messageInfo);
				// 记录日志
			} else {
				logger.info("channelCategory:" + ChannelCategory.TRADE
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}
		} catch (Exception e) {
			logger.error("====", e);
			throw new PlatformException(PlatformErrorCode.DB_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		// 把报文发送第三方
		try {
			message = shunionpayCommunication.sendMessageByHttp(message);
		} catch (Exception e) {
			logger.error("=================", e);
			// 设置http请求发送弄个异常状态码
			message.setHttpStatusCode(Constants.CommunicationStatus.HTTP_SEND_EXCEPTION
					.getCode());
			return message;
		}

		return message;
	}

	@Override
	protected Response respHandler(Message message, BizReqVo bizReqVo)
			throws PlatformException {
		PayResp dto = null;
		// 如果http請求操作成功的話
		if (Constants.CommunicationStatus.HTTP_OK_STATUS.getCode().equals(
				message.getHttpStatusCode())) {
			// 如果是报文解析异常
			try {
				dto = parseMessage(message, bizReqVo);
			} catch (PlatformException e) {
				// 设置状态:响应报文解析错误
				message.setHttpStatusCode(Constants.CommunicationStatus.PARSE_MESSAGE_ERROR
						.getCode());
				// 初始化异常信息
				message.setMessage(Constants.CommunicationStatus.PARSE_MESSAGE_ERROR
						.getDesc() + ",状态码:" + message.getHttpStatusCode());
				return parseMessageFailHandler(message, bizReqVo);
			}
			return communicationSuccessHandler(dto, message, bizReqVo);
			// 如果http请求发送异常
		} else if (Constants.CommunicationStatus.HTTP_SEND_EXCEPTION.getCode()
				.equals(message.getHttpStatusCode())) {
			// 初始化异常信息
			message.setMessage(Constants.CommunicationStatus.HTTP_SEND_EXCEPTION
					.getDesc() + ",状态码:" + message.getHttpStatusCode());
			return communicationThrowExceptionHandler(message, bizReqVo);
			// 调用第三方HTTP接口返回状态码不正确
		} else {
			// 初始化异常信息
			message.setMessage(Constants.CommunicationStatus.HTTP_NOT_OK_STATUS
					.getDesc() + ",状态码:" + message.getHttpStatusCode());
			return communicationFailHandler(message, bizReqVo);
		}
	}

	@Override
	protected PayResp parseMessage(Message message, BizReqVo bizReqVo)
			throws PlatformException {
		Request request = null;
		PayResp dto = null;
		try {
			dto = new PayResp();
			dto = dto.decode(message.getResponseInfo());
			// 把业务系统编码设置到返回dto中
			dto.setBizSys(bizReqVo.getBizSys());
			// 把业务类型设置到返回dto中
			dto.setBizType(bizReqVo.getBizType());
			// 把第三方系统编码设置到返回dto中
			dto.setThirdType(bizReqVo.getThirdType());
			// 把通道操作流水号设置到返回dto中
			dto.setChannelReqId(bizReqVo.getChannelReqId());
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“响应报文解析失败”
			request = new Request(message.getChannelReqId(),
					MessageStatus.RESPONSE_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.DTO_DECODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return dto;
	}

	@Override
	protected Response communicationFailHandler(Message message,
			BizReqVo bizReqVo) {
		// 返回调用方为交易处理中，对于这种状态来说需要TPP下一次明确告知对方(通过推送状态方式)该交易是否成功
		Response response = new Response(
				Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		Request request = null;

		// 发送报文后相关处理(修改Request状态-请求报文发送失败)
		try {
			// 设置request-状态为“请求报文发送失败”
			request = new Request(message.getChannelReqId(),
					MessageStatus.SEND_REQUEST_ERROR.getCode(),
					message.getMessage());
			requestService.update(request);
		} catch (Exception e) {
			logger.error("==============", e);

		}
		return response;
	}

	@Override
	protected Response communicationSuccessHandler(RespDto dto,
			Message message, BizReqVo bizReqVo) {
		
		Response response = new Response(
				Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		PayResp payResp = (PayResp) dto;

		Request request = null;
		MessageInfo messageInfo = null;
		PayReqVo payReqVo = null;
		// 发送报文后相关处理(修改Request状态-响应报文已收到、记录响应报文)
		try {
			// 设置request-状态为“收到响应报文”
			request = new Request(message.getChannelReqId(),
					MessageStatus.RECEIVED_RESPONSE.getCode());
			requestService.update(request);
			
			payReqVo = (PayReqVo) bizReqVo;
			// 记录响应报文
			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(), dto
							.getThirdType().getCode(), payReqVo.getTradeFlow());
			// 如果报文需要记录的话
			if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance()
					.getShunionpayConfig().getMsgInStorage())) {
				
				messageService.insert(messageInfo);
			} else {
				logger.info("channelCategory:" + ChannelCategory.TRADE
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}

		} catch (Exception e) {
			logger.error("==============", e);

		}
		
		
		// 交易状态属于失败态：6,9
		String[] failStatcode = Constants.ShunionpayConstants.PAY_STAT_FAIL_CODE.getCode().split(",");
		// 响应状态为失败态：0100,0101,0102,0103,0104
		String[] failRetCode = Constants.ShunionpayConstants.PAY_RESPONSE_FAIL_CODE.getCode().split(",");
		
		// 待更新
		PayInfo updatePayInfo = null;
		// 交易结果
		TradeResult tradeResult = null;
		
		try {
			// header通道返回码
			String retCode = payResp.getResponseCode();

			// 通道返回信息
			String retMsg = "";
			// 通道返回流水号
			String payTransFlow = "";

			// body通道返回码
			String bodyRetCode = payResp.getStat();
			
			// 如果响应状态为 接收成功
			if (Constants.ShunionpayConstants.PAY_RESPONSE_SUC_CODE.getCode().equals(retCode)) {
				
				// 如果交易状态为成功 s
				if (Constants.ShunionpayConstants.PAY_STAT_SUC_CODE.getCode().equals(bodyRetCode)) {
					
					// 根据交易状态码获得银联代付的状态枚举值
					retMsg = ShunionpayStatus.CODE_0000S.getDesc(); // 转换为银联定义的状态描述
					
					// 返回状态为-交易成功态
					response.setCode(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode());
					response.setMsg(retMsg);
					// 交易记录状态
					updatePayInfo = new PayInfo(message.getTradeSn(),
							Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(),
							payTransFlow, "", bodyRetCode,
							CalendarUtils.parsefomatCalendar(
									Calendar.getInstance(),
									CalendarUtils.LONG_FORMAT),
							PayInfo.NOTIFY_QUERY_STATUS_NO,
							PayInfo.NOTIFY_MERGE_STATUS_NO);
					// 初始化交易结果
					tradeResult = new TradeResult(payResp.getThirdType().getCode(),
							message.getTradeSn(), payTransFlow, payResp.getBizType()
									.getCode(), bodyRetCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(),
							message.getChannelReqId());
					
					
					// 得到最终结果处理
					finalResultHandler(tradeResult, updatePayInfo, message,
							response, bizReqVo);
				// 如果交易状态为失败
				} else if (Arrays.asList(failStatcode).contains(bodyRetCode)){
					
					// 根据交易状态码获得银联代付的状态枚举值
					ShunionpayStatus shunionpayStatus = ShunionpayStatus.get(retCode + bodyRetCode);
					retMsg = (shunionpayStatus == null ? Constants.TppConstants.TRADE_STATE_FAILER
							.getDesc() : shunionpayStatus.getDesc()); // 转换为银联定义的状态描述
					
					// 返回状态为-交易失败
					response.setCode(Constants.TppConstants.TRADE_STATE_FAILER
							.getCode());
					// 设置第三方通道返回错误信息
					response.setMsg((retMsg != null ? StringUtils.substring(retMsg,
							0, 150) : ""));
					
					// 交易记录状态
					updatePayInfo = new PayInfo(message.getTradeSn(),
							Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
							payTransFlow, (retMsg != null ? StringUtils.substring(
									retMsg, 0, 150) : ""), bodyRetCode,
							CalendarUtils.parsefomatCalendar(
									Calendar.getInstance(),
									CalendarUtils.LONG_FORMAT),
									PayInfo.NOTIFY_QUERY_STATUS_NO,
									PayInfo.NOTIFY_MERGE_STATUS_NO);
					// 初始化交易结果
					tradeResult = new TradeResult(payResp.getThirdType().getCode(),
							message.getTradeSn(), payTransFlow, payResp.getBizType()
									.getCode(), bodyRetCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
							message.getChannelReqId());
					
					// 得到最终结果处理
					finalResultHandler(tradeResult, updatePayInfo, message,
							response, bizReqVo);
					
				// 其他均为处理中
				} else {
					
					// 根据交易状态码获得银联代付的状态枚举值
					ShunionpayStatus shunionpayStatus = ShunionpayStatus.get(retCode + bodyRetCode);
					retMsg = (shunionpayStatus == null ? Constants.TppConstants.TRADE_STATE_MIDDLE.getDesc() : shunionpayStatus.getDesc()); // 转换为银联定义的状态描述
					
					// 返回状态为-交易中间态
					response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
							.getCode());
					// 设置第三方交易状态码
					response.setBankRepCode(bodyRetCode);
					// 设置第三方交易状态描述
					response.setMsg(retMsg);
					// 初始化交易结果
					tradeResult = new TradeResult(payResp.getThirdType().getCode(),
							message.getTradeSn(), payTransFlow, payResp.getBizType()
									.getCode(), bodyRetCode, (retMsg != null ? StringUtils.substring(retMsg, 0, 150) : ""),
							Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
							message.getChannelReqId());
					
					// 得到中间结果处理
					midResultHandler(tradeResult, message, bizReqVo);
				}
			
			} else if (Arrays.asList(failRetCode).contains(retCode)) {
				
				
				// 返回状态为-交易失败
				response.setCode(Constants.TppConstants.TRADE_STATE_FAILER
						.getCode());
				
				// 根据交易状态码获得银联代付的状态枚举值
				ShunionpayStatus shunionpayStatus = ShunionpayStatus.get(retCode);
				retMsg = (shunionpayStatus == null ? "" : shunionpayStatus.getDesc()); // 转换为银联定义的状态描述
				
				// 设置第三方通道返回错误信息
				response.setMsg((retMsg != null ? StringUtils.substring(retMsg,
						0, 150) : ""));
				
				// 交易记录状态
				updatePayInfo = new PayInfo(message.getTradeSn(),
						Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
						payTransFlow, (retMsg != null ? StringUtils.substring(
								retMsg, 0, 150) : ""), bodyRetCode,
						CalendarUtils.parsefomatCalendar(
								Calendar.getInstance(),
								CalendarUtils.LONG_FORMAT),
								PayInfo.NOTIFY_QUERY_STATUS_NO,
								PayInfo.NOTIFY_MERGE_STATUS_NO);
				// 初始化交易结果
				tradeResult = new TradeResult(payResp.getThirdType().getCode(),
						message.getTradeSn(), payTransFlow, payResp.getBizType()
								.getCode(), bodyRetCode,
						(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
								: ""),
						Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
						message.getChannelReqId());
				
				// 得到最终结果处理
				finalResultHandler(tradeResult, updatePayInfo, message,
						response, bizReqVo);
			// 如果响应状态为：待查询
			} else {
				// 返回状态为-交易中间态
				response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
						.getCode());
				
				// 根据交易状态码获得银联代付的状态枚举值
				ShunionpayStatus shunionpayStatus = ShunionpayStatus.get(retCode + bodyRetCode);
				retMsg = (shunionpayStatus == null ? Constants.TppConstants.TRADE_STATE_MIDDLE.getDesc() : shunionpayStatus.getDesc()); // 转换为银联定义的状态描述
				
				// 设置第三方通道返回错误信息
				response.setMsg(retMsg);
				
				response.setBankRepCode(bodyRetCode);
				
				// 初始化交易结果
				tradeResult = new TradeResult(payResp.getThirdType().getCode(),
						message.getTradeSn(), payTransFlow, payResp.getBizType()
								.getCode(), bodyRetCode, (retMsg != null ? StringUtils.substring(retMsg, 0, 150) : ""),
						Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
						message.getChannelReqId());
				
				// 得到中间结果处理
				midResultHandler(tradeResult, message, bizReqVo);
			}
			
		} catch (Exception e) {
			logger.error("==============", e);
		}
		return response;
	}

	@Override
	protected Response parseMessageFailHandler(Message message,
			BizReqVo bizReqVo) {
		// 返回调用方为交易处理中，对于这种状态来说需要TPP下一次明确告知对方(通过推送状态方式)该交易是否成功
		Response response = new Response(
				Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		return response;
	}

	@Override
	protected Response communicationThrowExceptionHandler(Message message,
			BizReqVo bizReqVo) {
		// 返回调用方为交易处理中，对于这种状态来说需要TPP下一次明确告知对方(通过推送状态方式)该交易是否成功
		Response response = new Response(
				Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		Request request = null;

		// 发送报文后相关处理(修改Request状态-请求报文发送失败)
		try {
			// 设置request-状态为“请求报文发送失败”
			request = new Request(message.getChannelReqId(),
					MessageStatus.SEND_REQUEST_ERROR.getCode(),
					message.getMessage());
			requestService.update(request);
		} catch (Exception e) {
			logger.error("==============", e);
		}

		return response;
	}
	
	
	/**
	 * 交易结果最终态的业务处理
	 * @param tradeResult
	 * @param payInfo
	 * @param message
	 * @param response
	 * @param bizReqVo
	 */
	private void finalResultHandler(TradeResult tradeResult,
			PayInfo payInfo, Message message, Response response,
			BizReqVo bizReqVo) {
		try {
			logger.info("ReqID:" + tradeResult.getReqId() + ",暂未入MQ队列");
			tradeResultService.finalResultHandler(tradeResult, payInfo);
		} catch (Exception e) {
			// 如果本次失败的话，返回业务端状态为-交易处理中，并同时写入队列
			// 返回状态为-交易中间态
			response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
					.getCode());
			response.setMsg(Constants.TppConstants.TRADE_STATE_MIDDLE
					.getDesc());
			
			
//			// 如果最终结果记录失败的话，则写入MONGODB通过查询最终更新状态
//			try {
//				PayReqVo payReqVo = (PayReqVo) bizReqVo;
//				payInfoService.sendNotifyQueryMsg(payReqVo);
//			} catch (Exception e1) {
//				logger.error("==============", e1);
//			}
			logger.error("交易最终结果处理异常", e);
		}
		
	}

	/**
	 * 交易结果为中间态的业务处理
	 * @param tradeResult
	 * @param message
	 * @param bizReqVo
	 */
	private void midResultHandler(TradeResult tradeResult, Message message,
			BizReqVo bizReqVo) {
		try {
			tradeResultService.insert(tradeResult);
		} catch (Exception e) {
			logger.error("交易中间结果处理异常", e);
		}
		
		logger.info("ReqID:" + tradeResult.getReqId() + ",暂未入mongo库");
		
		// 写MONGODB-该状态需要通过查询才能确定
//		try {
//			PayReqVo payReqVo = (PayReqVo) bizReqVo;
//			payInfoService.sendNotifyQueryMsg(payReqVo);
//		} catch (Exception e) {
//			logger.error("==============", e);
//		}
	}

}
