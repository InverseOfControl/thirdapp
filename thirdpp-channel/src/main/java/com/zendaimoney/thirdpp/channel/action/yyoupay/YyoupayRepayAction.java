package com.zendaimoney.thirdpp.channel.action.yyoupay;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.action.Action;
import com.zendaimoney.thirdpp.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.communication.yyoupay.YyoupayCommunication;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.yyoupay.collect.repay.RepayReq;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.yyoupay.collect.repay.RepayResp;
import com.zendaimoney.thirdpp.channel.entity.CollectInfo;
import com.zendaimoney.thirdpp.channel.entity.MessageInfo;
import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.channel.entity.TradeResult;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.service.CollectInfoService;
import com.zendaimoney.thirdpp.channel.service.MessageService;
import com.zendaimoney.thirdpp.channel.service.RequestService;
import com.zendaimoney.thirdpp.channel.service.TradeResultService;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.Constants.MessageStatus;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.yyoupay.YyoupayUtil;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 用友还款业务处理Action
 * @author mencius
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.YONGYOUUNIONPAY, bizType = BizType.BROKER_COLLECT, channelCategory = ChannelCategory.TRADE)
@Component("com.zendaimoney.thirdpp.channel.action.yyoupay.YyoupayRepayAction")
public class YyoupayRepayAction extends Action {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(YyoupayRepayAction.class);
	
	@Autowired
	private YyoupayCommunication yyoupayCommunication;

	@Autowired
	private RequestService requestService;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private TradeResultService tradeResultService;

	@Autowired
	private CollectInfoService collectInfoService;

	public YyoupayRepayAction() {
		super();
	}
	
	@Override
	protected ReqDto preProcess(BizReqVo vo) throws PlatformException {
		ReqDto dto = null;
		Request request = null;
		try {
			dto = new RepayReq(vo);
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

		RepayReq req = (RepayReq) dto;
		try {
			
			Map<String,String> paraMap = new HashMap<String,String>();
			// 报文请求地址
			message.setUrl(ConfigUtil.getInstance().getYyoupayConfig().getYyoupaRepayUrl());
			
			// 
			req.setRequestinfo(YyoupayUtil.repaySign(req, paraMap));
			message.setMessageMap(paraMap);
			
			// 签名之后的报文
			message.setRequestInfo(req.encode());

			// 把TPP通道reqid放到Message.提供respHandler方法中
			message.setChannelReqId(dto.getChannelReqId());

			// 设置交易流水号
			message.setTradeSn(((CollectReqVo) vo).getTradeFlow());
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
		// 更新商户号到代扣明细表
		updateCollectMerId(dto);
		// 拼装报文
		message = buildMessage(dto, vo);
		// 准备发送报文前相关处理(修改Request状态-请求报文已发送、记录发送报文)
		try {
			// 设置request-状态为“请求报文已发送”
			request = new Request(dto.getChannelReqId(),
					MessageStatus.SEND_REQUEST.getCode());
			requestService.update(request);
			
			CollectReqVo repayReqVo = (CollectReqVo) vo;
			// 记录请求报文
			messageInfo = new MessageInfo(dto.getChannelReqId(),
					MessageInfo.MSG_TYPE_Q, message.getRequestInfo(), dto
							.getThirdType().getCode(), repayReqVo.getTradeFlow());
			
			// 如果报文需要记录的话
			if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance()
					.getYyoupayConfig().getMsgInStorage())) {
				
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
			message = yyoupayCommunication.sendMessageByHttp(message);
		} catch (Exception e) {
			logger.error("=================", e);
			// 设置http请求发送弄个异常状态码
			message.setHttpStatusCode(Constants.CommunicationStatus.HTTP_SEND_EXCEPTION.getCode());
			return message;
		}
		return message;
	}

	@Override
	protected Response respHandler(Message message, BizReqVo bizReqVo)
			throws PlatformException {
		RepayResp dto = null;
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
	protected RepayResp parseMessage(Message message, BizReqVo bizReqVo)
			throws PlatformException {
		Request request = null;
		RepayResp dto = null;
		try {
			dto = new RepayResp();
			
			// 如果响应报文为空，则解析报文失败
			if (StringUtils.isBlank(message.getResponseInfo()) || Constants.NULL.equalsIgnoreCase(message.getResponseInfo().trim())) {
				throw new PlatformException(PlatformErrorCode.THIRD_RESPONSE_NO_MESSAGE_ERROR, PlatformErrorCode.THIRD_RESPONSE_NO_MESSAGE_ERROR.getDefaultMessage());
			}
			YyoupayUtil.checkRepayRespSign(message.getResponseInfo(), dto);
			// 把业务系统编码设置到返回dto中
			dto.setBizSys(bizReqVo.getBizSys());
			// 把业务类型设置到返回dto中
			dto.setBizType(bizReqVo.getBizType());
			// 把第三方系统编码设置到返回dto中
			dto.setThirdType(bizReqVo.getThirdType());
			// 把通道操作流水号设置到返回dto中
			dto.setChannelReqId(bizReqVo.getChannelReqId());
			
			message.setResponseInfo(dto.encode(null));
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
		
		// 写MONGODB-该状态需要通过查询才能确定
		try {
			CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
			collectInfoService.sendNotifyQueryMsg(collectReqVo);
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
		RepayResp res = (RepayResp) dto;

		Request request = null;
		MessageInfo messageInfo = null;
		CollectReqVo RepayReqVo = null;
		// 发送报文后相关处理(修改Request状态-响应报文已收到、记录响应报文)
		try {
			// 设置request-状态为“收到响应报文”
			request = new Request(message.getChannelReqId(),
					MessageStatus.RECEIVED_RESPONSE.getCode());
			requestService.update(request);
			
		    RepayReqVo = (CollectReqVo) bizReqVo;
			// 记录响应报文
			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(), dto
							.getThirdType().getCode(), RepayReqVo.getTradeFlow());
			// 如果报文需要记录的话
			if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance()
					.getYyoupayConfig().getMsgInStorage())) {
				
				messageService.insert(messageInfo);
			} else {
				logger.info("channelCategory:" + ChannelCategory.TRADE
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}

		} catch (Exception e) {
			logger.error("==============", e);

		}

		// 待更新
		CollectInfo updateCollectInfo = null;
				
		// 业务逻辑处理
		
		// 状态码属于失败态：001,002,003,004
		String[] failRetcode = Constants.YyoupayConstants.YYOU_REPAY_FAIL_CODE.getCode().split(",");
		

		// 交易结果
		TradeResult tradeResult = null;

		try {
			// header通道返回码
			String retCode = res.getRetCode();

			// 通道返回信息
			String retMsg = res.getRetMessage();
			// 通道返回流水号
			String payTransFlow = "";

			// body通道返回码
			String bodyRetCode = retCode;

			// 如果交易通道返回成功(返回码：000)
			if (Constants.YyoupayConstants.YYOU_REPAY_SUCCESS_CODE.getCode().equals(retCode)) {
				// 返回状态为-交易成功
				response.setCode(Constants.TppConstants.TRADE_STATE_SUCCESS
						.getCode());

				// 交易记录状态
				updateCollectInfo = new CollectInfo(message.getTradeSn(),
						Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(),
						payTransFlow, null, bodyRetCode,
						CalendarUtils.parsefomatCalendar(
								Calendar.getInstance(),
								CalendarUtils.LONG_FORMAT),
						CollectInfo.NOTIFY_QUERY_STATUS_NO,
						CollectInfo.NOTIFY_MERGE_STATUS_YES);
				// 初始化交易结果
				tradeResult = new TradeResult(res.getThirdType().getCode(),
						message.getTradeSn(), payTransFlow, res.getBizType()
								.getCode(), bodyRetCode,
						(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
								: ""),
						Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(),
						message.getChannelReqId());
				
				
				// 得到最终结果处理
				finalResultHandler(tradeResult, updateCollectInfo, message,
						response, bizReqVo);
				
				// (001~004)还款失败
			} else if (Arrays.asList(failRetcode).contains(retCode)){
				
				// 返回状态为-交易失败
				response.setCode(Constants.TppConstants.TRADE_STATE_FAILER
						.getCode());
				// 设置第三方通道返回错误信息
				response.setMsg((retMsg != null ? StringUtils.substring(retMsg,
						0, 150) : ""));
				
				// 交易记录状态
				updateCollectInfo = new CollectInfo(message.getTradeSn(),
						Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
						payTransFlow, (retMsg != null ? StringUtils.substring(
								retMsg, 0, 150) : ""), bodyRetCode,
						CalendarUtils.parsefomatCalendar(
								Calendar.getInstance(),
								CalendarUtils.LONG_FORMAT),
						CollectInfo.NOTIFY_QUERY_STATUS_NO,
						CollectInfo.NOTIFY_MERGE_STATUS_YES);
				// 初始化交易结果
				tradeResult = new TradeResult(res.getThirdType().getCode(),
						message.getTradeSn(), payTransFlow, res.getBizType()
								.getCode(), bodyRetCode,
						(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
								: ""),
						Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
						message.getChannelReqId());
				
				// 得到最终结果处理
				finalResultHandler(tradeResult, updateCollectInfo, message,
						response, bizReqVo);
				
				// 其他认为中间态
			} else {
				// 返回状态为-交易中间态
				response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
						.getCode());
				// 初始化交易结果
				tradeResult = new TradeResult(res.getThirdType().getCode(),
						message.getTradeSn(), payTransFlow, res.getBizType()
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
		
		// 写MONGODB-该状态需要通过查询才能确定
		try {
			CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
			collectInfoService.sendNotifyQueryMsg(collectReqVo);
		} catch (Exception e) {
			logger.error("==============", e);
		}
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
		
		// 写MONGODB-该状态需要通过查询才能确定
		try {
			CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
			collectInfoService.sendNotifyQueryMsg(collectReqVo);
		} catch (Exception e) {
			logger.error("==============", e);
		}

		return response;
	}
	
	
	/**
	 * 交易最终结果处理
	 * 
	 * @param tradeResult
	 * @param collectInfo
	 */
	private void finalResultHandler(TradeResult tradeResult,
			CollectInfo collectInfo, Message message, Response response,
			BizReqVo bizReqVo) {
		try {
			tradeResultService.finalResultHandler(tradeResult, collectInfo);
		} catch (Exception e) {
			// 如果本次失败的话，返回业务端状态为-交易处理中，并同时写入队列
			// 返回状态为-交易中间态
			response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
					.getCode());
			// 如果最终结果记录失败的话，则写入MONGODB通过查询最终更新状态
			try {
				CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
				collectInfoService.sendNotifyQueryMsg(collectReqVo);
			} catch (Exception e1) {
				logger.error("==============", e1);
			}
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
			BizReqVo bizReqVo) {
		try {
			tradeResultService.insert(tradeResult);
		} catch (Exception e) {
			logger.error("交易中间结果处理异常", e);
		}
		// 写MONGODB-该状态需要通过查询才能确定
		try {
			CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
			collectInfoService.sendNotifyQueryMsg(collectReqVo);
		} catch (Exception e) {
			logger.error("==============", e);
		}
	}
	/**
	 * 更新代扣明细
	 * 
	 * @param tradeResult
	 * @param collectInfo
	 */
	private void updateCollectMerId(ReqDto dto) {
		RepayReq req = (RepayReq)dto;
		CollectInfo collectInfo = new CollectInfo(req.getFlow(),req.getTpCode());
		try {
			tradeResultService.finalResultHandler(null, collectInfo);
		} catch (Exception e) {
			logger.error("更新代扣商户号信息异常:", e);
			throw new PlatformException(PlatformErrorCode.DB_ERROR,
					ExceptionUtil.getExceptionMessage(e));
			
		}
	}
}
