package com.zendaimoney.trust.channel.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.communication.Message;
import com.zendaimoney.trust.channel.entity.MessageDto;
import com.zendaimoney.trust.channel.entity.MessageInfo;
import com.zendaimoney.trust.channel.entity.Request;
import com.zendaimoney.trust.channel.entity.SysInfoCategory;
import com.zendaimoney.trust.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.trust.channel.entity.cmb.Header;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.service.MessageService;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.socket.clent.SocketSenderImpl;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.Constants.MessageStatus;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.ExceptionUtil;
import com.zendaimoney.trust.channel.util.JSONHelper;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ThirdPPCacheContainer;

/**
 * 资金托管查询接口抽象Action
 * @author mencius
 *
 */
public abstract class QueryActionAbstract {

	private static final LogPrn logger = new LogPrn(QueryActionAbstract.class);

	@Autowired
	private RequestService requestService;
	
	// 报文service
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private SocketSenderImpl socketSender;
	
	/** 
	 * 发送前业务处理 
	 * @param vo
	 * @return
	 * @throws PlatformException
	 */
	protected abstract TrustBizReqVo preProcess(TrustBizReqVo vo) throws PlatformException;

	/**
	 * 构建报文
	 * @param vo
	 * @return Message
	 */
	public Message buildMessage(TrustBizReqVo vo) {
		Message message = new Message();
		//Request request = null;
		try {
			
			// 根据信息类别编码去查询信息类别表
			SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
					.get(vo.getInfoCategoryCode());

			// 根据通道编号 + 商户类型 取得通道信息对象
			SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
					.get(vo.getThirdType().getCode()
							+ infoCategory.getMerchantType());
			
			// 创建报文头对象
			Header header = new Header();
			header.setTradeCode(vo.getTrustBizType().getCode()); // 功能交易码，参考每个接口的四位交易码
			header.setMerchantFlow(vo.getTradeFlow()); // 交易流水号
			header.setMerchantDate(vo.getTradeDate()); // 商户交易日期（YYYYMMDD）
			header.setMerchantTime(vo.getTradeTime()); // 商户交易时间（HHmmSS）
			header.setOrganzition(channelInfo.getMerchantNo()); // 请求方机构代码，招行分配
			
			// 请求报文，需要加密
			message.setRequestInfo(ConvertUtils.generateMessage(header, vo));

			// 把TPP通道reqid放到Message.提供respHandler方法中
			message.setChannelReqId(vo.getChannelReqId());

			// 设置交易流水号
			message.setTradeSn(vo.getTradeFlow());
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“请求报文解析失败”
			/*request = new Request(vo.getChannelReqId(),
					MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);*/
			throw new PlatformException(PlatformErrorCode.DTO_ENCODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return message;
	}

	/**
	 * 业务核心处理
	 * @param message
	 * @param vo
	 * @return
	 * @throws PlatformException
	 */
	public Message process(Message message, TrustBizReqVo vo) throws PlatformException {
		
		Request request = null;
		MessageInfo messageInfo = null;
		// 准备发送报文前相关处理(修改Request状态-请求报文已发送、记录发送报文)
		try {
			// 设置request-状态为“请求报文已发送”
//			request = new Request(vo.getChannelReqId(),
//					MessageStatus.SEND_REQUEST.getCode());
//			requestService.update(request);
			
			// 记录请求报文
//			messageInfo = new MessageInfo(vo.getChannelReqId(),
//					MessageInfo.MSG_TYPE_Q, message.getRequestInfo(), Constants.CMB_PAY_SYS_NO, vo.getTradeFlow());
			// 如果报文需要记录的话
			/*if (TrustCategory.TRADE.getCode().equals(vo.getTrustCategory().getCode())) {
				messageService.insert(messageInfo);
				// 记录日志
			} else {*/
				logger.info("channelCategory:" + ChannelCategory.TRADE
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			//}
		} catch (Exception e) {
			logger.error("====", e);
			throw new PlatformException(PlatformErrorCode.DB_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		// 把报文发送招商银行
		try {
			// 发向招商银行通讯请求
			message = socketSender.syncRequest(message, Constants.ENCODE_GBK);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 设置请求发送弄个异常状态码
			message.setStatusCode(Constants.CommunicationStatus.SOCKET_NOT_OK_STATUS.getCode());
			return message;
		}

		return message;
	}

	/**
	 * 返回处理响应
	 * @param message
	 * @param bizReqVo
	 * @return
	 * @throws PlatformException
	 */
	public Response respHandler(Message message, TrustBizReqVo bizReqVo)
			throws PlatformException {
		MessageDto messageDto = null;
		// 如果http請求操作成功的話
		if (Constants.CommunicationStatus.SOCKET_OK_STATUS.getCode().equals(
				message.getStatusCode())) {
			// 如果是报文解析异常
			try {
				messageDto = parseMessage(message, bizReqVo);
			} catch (PlatformException e) {
				// 设置状态:响应报文解析错误
				message.setStatusCode(Constants.CommunicationStatus.PARSE_MESSAGE_ERROR
						.getCode());
				// 初始化异常信息
				message.setMessage(Constants.CommunicationStatus.PARSE_MESSAGE_ERROR.getDesc());
				return parseMessageFailHandler(message, bizReqVo);
			}
			return communicationSuccessHandler(messageDto, message, bizReqVo);
			
			// 如果http请求发送异常
		} else if (Constants.CommunicationStatus.SOCKET_REQUEST_FAIL.getCode().equals(
				message.getStatusCode())) {
			
			// 初始化异常信息
			message.setMessage(Constants.CommunicationStatus.SOCKET_REQUEST_FAIL.getDesc());
			return communicationFailHandler(message, bizReqVo);
						
			// 如果http接收响应异常
		} else {
			
			// 初始化异常信息
			message.setMessage(Constants.CommunicationStatus.SOCKET_RESPONSE_FAIL.getDesc());
			return communicationResponseFailHandler(message, bizReqVo);
		}
	}

	/**
	 * 解析报文头
	 * @param message
	 * @param bizReqVo
	 * @return
	 * @throws PlatformException
	 */
	protected Header parseHeadMessage(Message message, TrustBizReqVo bizReqVo)
			throws PlatformException {
		// 报文头对象
		Header header = new Header();
		Request request = null;
		
		try {
			// 解析响应报文
			ConvertUtils.messageToObj(message, header);
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“响应报文解析失败”
//			request = new Request(message.getChannelReqId(),
//					MessageStatus.RESPONSE_PARSE_ERROR.getCode(),
//					ExceptionUtil.getExceptionPartyMessage(e));
//			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.DTO_DECODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return header;
	}
	
	/**
	 * 解析报文体
	 * 
	 * @param dto
	 * @return
	 */
	protected abstract MessageDto parseMessage(Message message,TrustBizReqVo bizReqVo)
			throws PlatformException;
	
	/**
	 * 请求失败处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param message
	 * @return
	 */
	public Response communicationFailHandler(Message message,TrustBizReqVo bizReqVo) {
		// 返回调用方为交易失败
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
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
	};
	
	
	/**
	 * 请求成功处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param dto
	 * @return
	 * @throws PlatformException
	 */
	protected abstract Response communicationSuccessHandler(MessageDto messageDto, Message message, TrustBizReqVo bizReqVo);
	
	
	/**
	 * 响应报文解析失败处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param message
	 * @return
	 */
	protected abstract Response parseMessageFailHandler(Message message, TrustBizReqVo bizReqVo);
	
	
	/**
	 * communication提交抛出异常处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param message
	 * @return
	 */
	protected abstract Response communicationThrowExceptionHandler(Message message, TrustBizReqVo bizReqVo);
	
	/**
	 * communication接收响应失败处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param message
	 * @return
	 */
	protected abstract Response communicationResponseFailHandler(Message message, TrustBizReqVo bizReqVo);

	/** 
	 * 执行处理入口 
	 * @param bizReqVo
	 * @return Response
	 */
	public Response execute(TrustBizReqVo bizReqVo) {
		Response response = null;
		try {
			
			bizReqVo = preProcess(bizReqVo);
			Message m = buildMessage(bizReqVo);
			m = process(m, bizReqVo);
			response = respHandler(m, bizReqVo);
			logger.info("channel最终响应结果:[code:"+response.getCode()+",msg:"+response.getMsg()+"]");
		} catch (PlatformException e) {
			response = new Response(e.getRealCode(), e.getMessage());
			//设置操作流水号
			response.setFlowId(bizReqVo.getChannelReqId());
			return response;
		} catch (Exception e) {
			logger.error("====", e);
			response = new Response(
					PlatformErrorCode.UNKNOWN_ERROR.getErrorCode(),
					ExceptionUtil.getExceptionMessage(e));
			//设置操作流水号
			response.setFlowId(bizReqVo.getChannelReqId());
			return response;
		}
		return response;
	}
	
	/**
	 * 设置 银行 返回码
	 * @param response
	 * @param headerCode
	 * @param bodyCode
	 */
	protected void setBankResponseCode(Response response, String headerCode, String bodyCode) {
		
		if (!Constants.CmbStatus.CMBMB99.getCode().equals(headerCode)) {
			response.setBankRepCode(headerCode);
			return;
		}
		
		response.setBankRepCode(bodyCode);
	}

}
