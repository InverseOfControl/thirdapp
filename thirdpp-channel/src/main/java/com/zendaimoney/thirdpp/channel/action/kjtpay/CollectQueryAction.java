package com.zendaimoney.thirdpp.channel.action.kjtpay;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.channel.action.Action;
import com.zendaimoney.thirdpp.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.communication.kjtpay.KjtpayCommunication;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.kjtpay.collect.query.CollectQueryReq;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.kjtpay.collect.query.CollectQueryResp;
import com.zendaimoney.thirdpp.channel.entity.MessageInfo;
import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.channel.service.TradeResultService;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.Constants.MessageStatus;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.kjtpay.KjtpayUtil;
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
@ReqActionAnnotation(thirdType = ThirdType.KJTPAY, bizType = BizType.BROKER_COLLECT, channelCategory = ChannelCategory.QUERY)
@Component("com.zendaimoney.thirdpp.channel.action.kjtpay.CollectQueryAction")
public class CollectQueryAction extends Action {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(CollectQueryAction.class);

	@Autowired
	private KjtpayCommunication kjtpayCommunication;

	@Autowired
	private TradeResultService tradeResultService;
	
	public CollectQueryAction() {
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
			dto = new CollectQueryReq(vo);
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“请求报文解析失败”
			request = new Request(vo.getChannelReqId(),
					MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",request:" + JSONHelper.bean2json(request));
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
			message = kjtpayCommunication.sendMessageByHttp(message);
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

		CollectQueryResp dto = null;
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
		

		CollectQueryReq req = (CollectQueryReq) dto;
		try {
			infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap.get(vo
					.getInfoCategoryCode());

			channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo
					.getThirdType().getCode() + infoCategory.getMerchantType());
			// 报文请求地址
			message.setUrl(ConfigUtil.getInstance().getKjtpayConfig()
					.getCollectingUrl());
			
			// 获取证书签名路径
			String keyPath= ConfigUtil.getInstance().getKjtpayConfig().getKeyPath() + channelInfo.getCertName();
			//获取keyFile
			String keyFilePath=Thread.currentThread().getContextClassLoader().getResource(keyPath).getPath();
			
			String certPath = ConfigUtil.getInstance().getKjtpayConfig().getKeyPath() + channelInfo.getUserName();
			//获取certFile
			String certFilePath=Thread.currentThread().getContextClassLoader().getResource(certPath).getPath();
			
			Map<String, String> sParaTemp = KjtpayUtil.xmlToMap(dto.encode());
			//请求参数对象Map,参数加密 
			Map<String, String> map = KjtpayUtil.buildRequestPara(channelInfo.getMerchantNo(),sParaTemp,req.getSign_type(),channelInfo.getCertPwd(),req.get_input_charset(),keyFilePath,certFilePath,channelInfo.getCertPwd());

			//req.setSign(map.get("sign"));
			
			// 请求报文，需要加密
			message.setRequestInfo(req.encode());
			
			message.setMessageMap(map);

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
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",request:" + JSONHelper.bean2json(request));
			throw new PlatformException(PlatformErrorCode.DTO_ENCODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return message;
	}

	/**
	 * 返回报文转化成数据传递对象
	 */
	@Override
	protected CollectQueryResp parseMessage(Message message, BizReqVo bizReqVo)
			throws PlatformException {
		Request request = null;
		CollectQueryResp dto = null;
		try {
			dto = new CollectQueryResp();
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
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",request:" + JSONHelper.bean2json(request));
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
	protected Response communicationFailHandler(Message message, BizReqVo vo) {
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
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",request:" + JSONHelper.bean2json(request));
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
			Message message, BizReqVo vo) {
		Response response = new Response(
				Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		CollectQueryResp res = (CollectQueryResp) dto;
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
			
			queryReqVo = (QueryReqVo) vo;
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
		// 获取所有中间态
		String[] midStatus = Constants.KjtpayConstants.TRADE_STATE_MID.getCode().split(",");
		String[] sucStatus = Constants.KjtpayConstants.TRADE_STATE_SUCC.getCode().split(",");
		// 返回记录集合
		String body = "";
		try {
			// header通道返回码
			String retCode = res.getIs_success();
			// 通道返回信息
			String retMsg = "";
			String result = res.getResult();
			String bodyRetCode = "";
			String errorCode = "";
			// 返回结果集
			body = res.getTradeList();
			if (body != null && !body.isEmpty()) {
				body = body.split("\\|")[0];
				String[] oneSplit = body.split("\\^");
				if(oneSplit.length>=8){
					bodyRetCode = oneSplit[6];
				}
				if(oneSplit.length>=9){
					errorCode = oneSplit[8];
				}
				if(oneSplit.length>=10){
					retMsg = oneSplit[9];
				}
				
				// 如果交易返回处于中间态.
				if (StringUtils.isBlank(retCode)||!Constants.KjtpayConstants.RESPONSE_SUC_CODE.getCode().equals(retCode)||!"true".equals(result)
						|| Arrays.asList(midStatus).contains(bodyRetCode)) {
					// 返回状态为-交易中间态
					response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
							.getCode());
					// 如果交易通道返回成功(info和body中状态码都成功才算成功)
				} else if (Constants.KjtpayConstants.RESPONSE_SUC_CODE.getCode()
						.equals(retCode) && "true".equals(result)
						&& Arrays.asList(sucStatus).contains(bodyRetCode)) {
					// 返回状态为-交易成功
					response.setCode(Constants.TppConstants.TRADE_STATE_SUCCESS
							.getCode());
					// 设置银行返回码
					response.setBankRepCode(bodyRetCode);
					
					// 其他定义交易失败
				} else {
					// 返回状态为-交易失败
					response.setCode(Constants.TppConstants.TRADE_STATE_FAILER
							.getCode());
				
					response.setMsg((retMsg != null ? StringUtils.substring(
							retMsg, 0, 150) : ""));
					
					if (StringUtils.isBlank(errorCode)) {
						errorCode = bodyRetCode;
					}
					// 设置银行返回码
					response.setBankRepCode(errorCode);
					
				}
			} else {
				
				if(Constants.KjtpayConstants.RESPONSE_SUC_CODE.getCode().equals(retCode) && "true".equals(result)) {
					response.setCode(Constants.TppConstants.TRADE_STATE_FAILER
							.getCode());
				}else {
					// 返回状态为-交易中间态
					response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
							.getCode());
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
	protected Response parseMessageFailHandler(Message message, BizReqVo vo) {
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
			BizReqVo vo) {
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
			logger.info("channelCategory:" + ChannelCategory.QUERY
					+ ",request:" + JSONHelper.bean2json(request));
		} catch (Exception e) {
			logger.error("==============", e);

		}
		return response;
	}
}
