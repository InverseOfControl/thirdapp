package com.zendaimoney.thirdpp.channel.action.yyoupay;

import java.util.Arrays;
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
import com.zendaimoney.thirdpp.channel.dto.req.yyoupay.collect.query.RepayQueryReq;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.yyoupay.collect.query.RepayQueryResp;
import com.zendaimoney.thirdpp.channel.entity.MessageInfo;
import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.channel.service.CollectInfoService;
import com.zendaimoney.thirdpp.channel.service.MessageService;
import com.zendaimoney.thirdpp.channel.service.RequestService;
import com.zendaimoney.thirdpp.channel.service.TradeResultService;
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
 * 用友查询业务处理Action
 * @author mencius
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.YONGYOUUNIONPAY, bizType = BizType.BROKER_COLLECT, channelCategory = ChannelCategory.QUERY)
@Component("com.zendaimoney.thirdpp.channel.action.yyoupay.YyoupayQueryAction")
public class YyoupayQueryAction extends Action {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(YyoupayQueryAction.class);
	
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

	public YyoupayQueryAction() {
		super();
	}
	
	@Override
	protected ReqDto preProcess(BizReqVo vo) throws PlatformException {
		ReqDto dto = null;
		Request request = null;
		try {
			dto = new RepayQueryReq(vo);
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

		RepayQueryReq req = (RepayQueryReq) dto;
		try {
			
			Map<String,String> paraMap = new HashMap<String,String>();
			// 报文请求地址
			message.setUrl(ConfigUtil.getInstance().getYyoupayConfig().getYyoupaRepayUrl());
			
			// 
			req.setRequestinfo(YyoupayUtil.repayQuerySign(req, paraMap));
			message.setMessageMap(paraMap);
			
			// 签名之后的报文
			message.setRequestInfo(req.encode());

			// 把TPP通道reqid放到Message.提供respHandler方法中
			message.setChannelReqId(dto.getChannelReqId());

			// 设置交易流水号
			message.setTradeSn(((QueryReqVo) vo).getTradeFlow());
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
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",request:" + JSONHelper.bean2json(request));
			QueryReqVo queryReqVo = (QueryReqVo) vo;
			// 记录请求报文
			messageInfo = new MessageInfo(dto.getChannelReqId(),
					MessageInfo.MSG_TYPE_Q, message.getRequestInfo(), dto
							.getThirdType().getCode(), queryReqVo.getTradeFlow());
			
			// 记录报文
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
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
		RepayQueryResp dto = null;
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
	protected RepayQueryResp parseMessage(Message message, BizReqVo bizReqVo)
			throws PlatformException {
		Request request = null;
		RepayQueryResp dto = null;
		try {
			// 如果响应报文为空，则解析报文失败
			if (StringUtils.isBlank(message.getResponseInfo()) || Constants.NULL.equalsIgnoreCase(message.getResponseInfo().trim())) {
				throw new PlatformException(PlatformErrorCode.THIRD_RESPONSE_NO_MESSAGE_ERROR, PlatformErrorCode.THIRD_RESPONSE_NO_MESSAGE_ERROR.getDefaultMessage());
			}
			
			dto = new RepayQueryResp();
			YyoupayUtil.checkQueryRespSign(message.getResponseInfo(), dto);
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
		RepayQueryResp res = (RepayQueryResp) dto;

		Request request = null;
		MessageInfo messageInfo = null;
		QueryReqVo queryReqVo = null;
		// 发送报文后相关处理(修改Request状态-响应报文已收到、记录响应报文)
		try {
			// 设置request-状态为“收到响应报文”
			request = new Request(message.getChannelReqId(),
					MessageStatus.RECEIVED_RESPONSE.getCode());
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",request:" + JSONHelper.bean2json(request));
			
			queryReqVo = (QueryReqVo) bizReqVo;
			// 记录响应报文
			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(), dto
							.getThirdType().getCode(), queryReqVo.getTradeFlow());
			
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));

		} catch (Exception e) {
			logger.error("==============", e);

		}

		// 业务逻辑处理
		// 状态码属于失败态：001,002,003,004
		String[] failRetcode = Constants.YyoupayConstants.YYOU_REPAY_FAIL_CODE.getCode().split(",");

		try {
			// header通道返回码
			String retCode = res.getRetCode();

			// 通道返回信息
			String retMsg = res.getRetMessage();

			// body通道返回码
			String bodyRetCode = res.getState();
			
			// 返回码002 未找到流水信息 - 交易失败
			if (Constants.YyoupayConstants.YYOU_QUERY_FAIL_RETCODE.getCode().equals(retCode)) {
				// 返回状态为-交易失败
				response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
				// 设置第三方通道返回错误信息
				response.setMsg(Constants.YyoupayConstants.YYOU_QUERY_FAIL_RETCODE.getDesc());
				
				// 设置银行返回码
				response.setBankRepCode(bodyRetCode);
			} else {
				
				// 如果交易通道返回成功(响应码和交易码都成功)
				if (Constants.YyoupayConstants.YYOU_REPAY_SUCCESS_CODE.getCode().equals(retCode) && Constants.YyoupayConstants.YYOU_REPAY_SUCCESS_CODE.getCode().equals(bodyRetCode)) {
					// 返回状态为-交易成功
					response.setCode(Constants.TppConstants.TRADE_STATE_SUCCESS
							.getCode());
					// 设置银行返回码
					response.setBankRepCode(bodyRetCode);
					
					// 如果交易通道返回成功(响应码 000 和交易码失败)
				} else if (Constants.YyoupayConstants.YYOU_REPAY_SUCCESS_CODE.getCode().equals(retCode) && Arrays.asList(failRetcode).contains(bodyRetCode)){
					
					// 返回状态为-交易失败
					response.setCode(Constants.TppConstants.TRADE_STATE_FAILER
							.getCode());
					// 设置第三方通道返回错误信息
					response.setMsg((retMsg != null ? StringUtils.substring(retMsg,
							0, 150) : ""));
					
					// 设置银行返回码
					response.setBankRepCode(bodyRetCode);
					
					// 其他认为中间态
				} else {
					// 返回状态为-交易中间态
					response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE.getCode());
				}
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
		
		return response;
	}
	
}
