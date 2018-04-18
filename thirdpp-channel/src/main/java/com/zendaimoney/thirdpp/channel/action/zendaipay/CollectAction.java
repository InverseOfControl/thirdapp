package com.zendaimoney.thirdpp.channel.action.zendaipay;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.action.Action;
import com.zendaimoney.thirdpp.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.communication.zendaipay.ZendaipayCommunication;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.zendaipay.collect.trade.CollectReq;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.zendaipay.collect.trade.CollectResp;
import com.zendaimoney.thirdpp.channel.entity.CollectInfo;
import com.zendaimoney.thirdpp.channel.entity.MessageInfo;
import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
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
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.zendaipay.ZendaipayUtil;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 代收业务处理
 * @author 00233197
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.ZENDAIPAY, bizType = BizType.BROKER_COLLECT, channelCategory = ChannelCategory.TRADE)
@Component("com.zendaimoney.thirdpp.channel.action.zendaipay.CollectAction")
public class CollectAction extends Action {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(CollectAction.class);

	@Autowired
	private ZendaipayCommunication zendaipayCommunication;

	@Autowired
	private RequestService requestService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private TradeResultService tradeResultService;

	@Autowired
	private CollectInfoService collectInfoService;

	public CollectAction() {
		super();
	}

	/**
	 * 初始化数据传输对象
	 */
	@Override
	protected ReqDto preProcess(BizReqVo vo) throws PlatformException {
		ReqDto dto = null;
		Request request = null;
		try {
			dto = new CollectReq(vo);
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
			
			CollectReqVo collectReqVo = (CollectReqVo) vo;
			// 记录请求报文
			messageInfo = new MessageInfo(dto.getChannelReqId(),
					MessageInfo.MSG_TYPE_Q, message.getRequestInfo(), dto
							.getThirdType().getCode(), collectReqVo.getTradeFlow());
			
			// 如果报文需要记录的话
			if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance()
					.getZendaipayConfig().getMsgInStorage())) {
				
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
			message = zendaipayCommunication.sendMessageByHttp(message);
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

		CollectResp dto = null;
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
		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = null;
		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = null;

		CollectReq req = (CollectReq) dto;
		try {
			infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap.get(vo
					.getInfoCategoryCode());

			channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo
					.getThirdType().getCode() + infoCategory.getMerchantType());
			// 报文请求地址
			message.setUrl(ConfigUtil.getInstance().getZendaipayConfig()
					.getCollectingUrl());

			// 组装签名前报文
			String signMessage = ZendaipayUtil.buildSignMessage(req,channelInfo.getCertPwd());
			// 签名之后的报文
			String sign = ZendaipayUtil.Sign(signMessage);
			
			req.setSign(sign);

			// 请求报文，需要加密
			message.setRequestInfo(req.encode());

			// 请求参数对象Map
			Map<String, String> paraMap = ZendaipayUtil.xmlToMap(message
					.getRequestInfo());
			message.setMessageMap(paraMap);

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

	/**
	 * 返回报文转化成数据传递对象
	 */
	@Override
	protected CollectResp parseMessage(Message message, BizReqVo bizReqVo)
			throws PlatformException {
		Request request = null;
		CollectResp dto = null;
		try {
			dto = new CollectResp();
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
		CollectResp res = (CollectResp) dto;

		Request request = null;
		MessageInfo messageInfo = null;
		// 发送报文后相关处理(修改Request状态-响应报文已收到、记录响应报文)
		try {
			// 设置request-状态为“收到响应报文”
			request = new Request(message.getChannelReqId(),
					MessageStatus.RECEIVED_RESPONSE.getCode());
			requestService.update(request);
			
			CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
			// 记录响应报文
			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(), dto
							.getThirdType().getCode(), collectReqVo.getTradeFlow());
			// 如果报文需要记录的话
			if (Constants.MSG_IN_STORAGE_YES.equals(ConfigUtil.getInstance()
					.getZendaipayConfig().getMsgInStorage())) {
				
				messageService.insert(messageInfo);
			} else {
				logger.info("channelCategory:" + ChannelCategory.TRADE
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}

		} catch (Exception e) {
			logger.error("==============", e);

		}

		// 业务逻辑处理
		// 响应码属于中间态：222222\333333
		String[] middleRetcode = Constants.ZendaipayConstants.RETCODE_STATE_MID
				.getCode().split(",");

		// 获取所有中间态
		String[] middleStatus = Constants.ZendaipayConstants.TRADE_STATE_MID.getCode().split(",");
		// 待更新
		CollectInfo updateCollectInfo = null;
		// 交易结果
		TradeResult tradeResult = null;

		try {
			// header通道返回码
			String retCode = res.getCode();

			// 通道返回信息
			String retMsg = res.getMsg();
			// 通道返回流水号
			String payTransFlow = "";

			// body通道返回码
			String bodyRetCode = res.getResultCode();

			// 如果交易返回处于中间态.
			if (StringUtils.isEmpty(retCode) || Arrays.asList(middleRetcode).contains(retCode)) {
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
				// 如果交易通道返回成功(响应码和交易码都成功)
			} else if (Constants.ZendaipayConstants.RESPONSE_CODE.getCode()
					.equals(retCode)
					&& Constants.ZendaipayConstants.TRADE_STATE_SUCC.getCode()
							.equals(bodyRetCode)) {
				// 返回状态为-交易成功
				response.setCode(Constants.TppConstants.TRADE_STATE_SUCCESS
						.getCode());
				
				// 交易成功时 银联响应描述为空，为统一记录交易结果描述 赋值为：交易成功
				if (StringUtils.isBlank(retMsg)) {
					retMsg = Constants.ZendaipayConstants.TRADE_STATE_SUCC.getDesc();
				}
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
				
				// 其他定义交易失败
			} else {
				
				// 返回状态为-交易失败
				response.setCode(Constants.TppConstants.TRADE_STATE_FAILER
						.getCode());
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
				
			}
		} catch (Exception e) {
			logger.error("==============", e);
			// 写MONGODB-该状态需要通过查询才能确定
			try {
				CollectReqVo collectReqVo = (CollectReqVo) bizReqVo;
				collectInfoService.sendNotifyQueryMsg(collectReqVo);
			} catch (Exception e1) {
				logger.error("==============", e1);
			}
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
				logger.error("==============" + e1.getMessage(), e1);
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
	
}
