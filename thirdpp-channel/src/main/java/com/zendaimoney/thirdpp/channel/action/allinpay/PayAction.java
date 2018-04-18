package com.zendaimoney.thirdpp.channel.action.allinpay;

import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.action.Action;
import com.zendaimoney.thirdpp.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.communication.allinpay.AllinpayCommunication;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.allinpay.pay.trade.PayReq;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.allinpay.pay.trade.RespBody;
import com.zendaimoney.thirdpp.channel.dto.resp.allinpay.pay.trade.PayResp;
import com.zendaimoney.thirdpp.channel.entity.MessageInfo;
import com.zendaimoney.thirdpp.channel.entity.PayInfo;
import com.zendaimoney.thirdpp.channel.entity.Request;
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
import com.zendaimoney.thirdpp.channel.util.allinpay.AllinpayUtil;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 代收业务处理
 * 
 * @author 00231257
 * 
 */
@ReqActionAnnotation(thirdType = ThirdType.ALLINPAY, bizType = BizType.BROKER_PAY, channelCategory = ChannelCategory.TRADE)
@Component("com.zendaimoney.thirdpp.channel.action.allinpay.PayAction")
public class PayAction extends Action {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(PayAction.class);

	@Autowired
	private AllinpayCommunication allinpayCommunication;

	@Autowired
	private RequestService requestService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private TradeResultService tradeResultService;
	
	// 代付交易明细service
	@Autowired
	private PayInfoService payInfoService;

	public PayAction() {
		super();
	}

	/**
	 * 初始化数据传输对象
	 */
	@Override
	protected ReqDto preProcess(BizReqVo vo) throws PlatformException {
		// TODO Auto-generated method stub
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
					.getAllinpayConfig().getMsgInStorage())) {
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
			message = allinpayCommunication.sendMessageByHttp(message);
		} catch (Exception e) {
			logger.error("=================", e);
			// 设置http请求发送弄个异常状态码
			message.setHttpStatusCode(Constants.CommunicationStatus.HTTP_SEND_EXCEPTION
					.getCode());
			return message;
		}

		return message;
	}

	/**
	 * http响应处理
	 */
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

	/**
	 * 构建报文
	 */
	@Override
	protected Message buildMessage(ReqDto dto, BizReqVo vo)
			throws PlatformException {
		Message message = new Message();
		Request request = null;
		try {
			// 报文请求地址
			message.setUrl(ConfigUtil.getInstance().getAllinpayConfig()
					.getUrl());
			// 请求报文，需要加密
			message.setRequestInfo(AllinpayUtil.signMsg(dto.encode(), vo));

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

	/**
	 * 返回报文转化成数据传递对象
	 */
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

	/**
	 * Http请求失败处理(http返回状态码不为150)(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * 
	 * @param message
	 * @return
	 * @throws PlatformException
	 */
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

	/**
	 * Http请求成功处理(http返回状态码为150)(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * 
	 * @param dto
	 * @return
	 * @throws PlatformException
	 */
	@Override
	protected Response communicationSuccessHandler(RespDto dto,
			Message message, BizReqVo bizReqVo) {
		Response response = new Response(
				Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		PayResp res = (PayResp) dto;
		Request request = null;
		MessageInfo messageInfo = null;
		// 发送报文后相关处理(修改Request状态-响应报文已收到、记录响应报文)
		try {
			// 设置request-状态为“收到响应报文”
			request = new Request(message.getChannelReqId(),
					MessageStatus.RECEIVED_RESPONSE.getCode());
			requestService.update(request);
			
			PayReqVo payReqVo = (PayReqVo) bizReqVo;
			// 记录响应报文
			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(), dto
							.getThirdType().getCode(), payReqVo.getTradeFlow());
			// 如果报文需要记录的话
			if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance()
					.getAllinpayConfig().getMsgInStorage())) {
				
				// 设置交易流水号
				messageInfo.setSpare1(payReqVo.getTradeFlow());
				
				messageService.insert(messageInfo);
			} else {
				logger.info("channelCategory:" + ChannelCategory.TRADE
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}

		} catch (Exception e) {
			logger.error("==============", e);

		}

		// 业务逻辑处理
		// 获取所有中间态
		String[] middleStatus = Constants.AllinpayConstants.TRADE_STATE_MID
				.getCode().split(",");
		// 待更新
		PayInfo updatePayInfo = null;
		// 交易结果
		TradeResult tradeResult = null;

		try {
			// header通道返回码
			String retCode = res.getHeader().getRetCode();
			// 通道返回信息
			String retMsg = res.getHeader().getErrMsg();
			// 通道返回流水号
			String payTransFlow = "";
			RespBody respBody =  res.getBody();
			
			if (respBody != null) {
				
				// body通道返回码
				String bodyRetCode = respBody.getRetCode();
				// 如果交易返回处于中间态.
				if (StringUtils.isBlank(retCode) || StringUtils.isBlank(bodyRetCode)
						|| Arrays.asList(middleStatus).contains(retCode)) {
					// 返回状态为-交易中间态
					response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
							.getCode());
					response.setMsg((retMsg != null ? StringUtils.substring(retMsg, 0, 150)
							: ""));
					
					// 初始化交易结果
					tradeResult = new TradeResult(res.getThirdType().getCode(),
							message.getTradeSn(), payTransFlow, res.getBizType()
							.getCode(), retCode, retMsg,
							Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
							message.getChannelReqId());
					// 得到中间结果处理
					midResultHandler(tradeResult, message, bizReqVo);
					// 如果交易通道返回成功(info和body中状态码都成功才算成功)
				} else if (Constants.AllinpayConstants.TRADE_STATE_SUCC.getCode()
						.equals(retCode)
						&& Constants.AllinpayConstants.TRADE_STATE_SUCC.getCode()
						.equals(bodyRetCode)) {
					// 返回状态为-交易成功
					response.setCode(Constants.TppConstants.TRADE_STATE_SUCCESS
							.getCode());
					// 交易记录状态
					updatePayInfo = new PayInfo(message.getTradeSn(),
							Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(),
							payTransFlow, null, retCode,
							CalendarUtils.parsefomatCalendar(
									Calendar.getInstance(),
									CalendarUtils.LONG_FORMAT),
									PayInfo.NOTIFY_QUERY_STATUS_NO,PayInfo.NOTIFY_MERGE_STATUS_NO);
					// 初始化交易结果
					tradeResult = new TradeResult(res.getThirdType().getCode(),
							message.getTradeSn(), payTransFlow, res.getBizType()
							.getCode(), retCode, (retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
									Constants.TppConstants.TRADE_STATE_SUCCESS.getCode(),
									message.getChannelReqId());
					// 得到最终结果处理
					finalResultHandler(tradeResult, updatePayInfo, message,
							response, bizReqVo);
					// 其他定义交易失败
				} else {
					// header和body中都有返回码，只有当两个返回码都成功时，才认为是成功的，当有一个失败时一个成功，取失败的，当两个都失败错误码，随便取一个
					if (!Constants.AllinpayConstants.TRADE_STATE_SUCC.getCode()
							.equals(bodyRetCode)) {
						
						if (!StringUtils.isBlank(bodyRetCode)) {
							retCode = bodyRetCode;
						}
					}
					// 返回状态为-交易失败
					response.setCode(Constants.TppConstants.TRADE_STATE_FAILER
							.getCode());
					
					// 设置第三方通道返回错误信息
					// 如果错误码为“未查询到符合条件的记录”需要特殊处理
					if (Constants.AllinpayConstants.UNKNOW_RETURNCODE.getCode()
							.equals(retCode)) {
						retMsg = Constants.AllinpayConstants.UNKNOW_RETURNCODE.getDesc();
					}
					
					// 设置第三方通道返回错误信息
					response.setMsg((retMsg != null ? StringUtils.substring(retMsg, 0, 150)
							: ""));
					// 交易记录状态
					updatePayInfo = new PayInfo(message.getTradeSn(),
							Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
							payTransFlow, (retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""), retCode,
									CalendarUtils.parsefomatCalendar(
											Calendar.getInstance(),
											CalendarUtils.LONG_FORMAT),
											PayInfo.NOTIFY_QUERY_STATUS_NO,PayInfo.NOTIFY_MERGE_STATUS_NO);
					// 初始化交易结果
					tradeResult = new TradeResult(res.getThirdType().getCode(),
							message.getTradeSn(), payTransFlow, res.getBizType()
							.getCode(), retCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
									Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
									message.getChannelReqId());
					// 得到最终结果处理
					finalResultHandler(tradeResult, updatePayInfo, message,
							response, bizReqVo);
				}
			} else {
				// 如果交易返回处于中间态.
				if (StringUtils.isBlank(retCode) || Arrays.asList(middleStatus).contains(retCode) 
						|| Constants.AllinpayConstants.TRADE_STATE_SUCC.getCode().equals(retCode)) {
					// 返回状态为-交易中间态
					response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
							.getCode());
					response.setMsg((retMsg != null ? StringUtils.substring(retMsg, 0, 150)
							: ""));
					
					// 初始化交易结果
					tradeResult = new TradeResult(res.getThirdType().getCode(),
							message.getTradeSn(), payTransFlow, res.getBizType()
							.getCode(), retCode, retMsg,
							Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
							message.getChannelReqId());
					// 得到中间结果处理
					midResultHandler(tradeResult, message, bizReqVo);
					// 其他定义交易失败
				} else {
					// 返回状态为-交易失败
					response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
					
					// 设置第三方通道返回错误信息
					// 如果错误码为“未查询到符合条件的记录”需要特殊处理
					if (Constants.AllinpayConstants.UNKNOW_RETURNCODE.getCode()
							.equals(retCode)) {
						retMsg = Constants.AllinpayConstants.UNKNOW_RETURNCODE.getDesc();
					}
					
					// 设置第三方通道返回错误信息
					response.setMsg((retMsg != null ? StringUtils.substring(retMsg, 0, 150)
							: ""));
					// 交易记录状态
					updatePayInfo = new PayInfo(message.getTradeSn(),
							Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
							payTransFlow, (retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""), retCode,
									CalendarUtils.parsefomatCalendar(
											Calendar.getInstance(),
											CalendarUtils.LONG_FORMAT),
											PayInfo.NOTIFY_QUERY_STATUS_NO,PayInfo.NOTIFY_MERGE_STATUS_NO);
					// 初始化交易结果
					tradeResult = new TradeResult(res.getThirdType().getCode(),
							message.getTradeSn(), payTransFlow, res.getBizType()
							.getCode(), retCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
									Constants.TppConstants.TRADE_STATE_FAILER.getCode(),
									message.getChannelReqId());
					// 得到最终结果处理
					finalResultHandler(tradeResult, updatePayInfo, message,
							response, bizReqVo);
				}
			}
					
		} catch (Exception e) {
			logger.error("==============", e);
		}
		return response;
	}

	/**
	 * 响应报文解析失败处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * 
	 * @param message
	 * @return
	 */
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

	/**
	 * communication提交抛出异常处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * 
	 * @param message
	 * @return
	 */
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
	 * 交易最终结果处理
	 * 
	 * @param tradeResult
	 * @param payInfo
	 */
	private void finalResultHandler(TradeResult tradeResult,
			PayInfo payInfo, Message message, Response response,
			BizReqVo bizReqVo) {
		try {
			tradeResultService.finalResultHandler(tradeResult, payInfo);
		} catch (Exception e) {
			// 如果本次失败的话，返回业务端状态为-交易处理中，并同时写入队列
			// 返回状态为-交易中间态
			response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
					.getCode());
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
	}

}
