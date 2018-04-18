package com.zendaimoney.trust.channel.action.file;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.TradeActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.communication.Message;
import com.zendaimoney.trust.channel.entity.BatchOper;
import com.zendaimoney.trust.channel.entity.MessageDto;
import com.zendaimoney.trust.channel.entity.Request;
import com.zendaimoney.trust.channel.entity.SysInfoCategory;
import com.zendaimoney.trust.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.trust.channel.entity.TradeResult;
import com.zendaimoney.trust.channel.entity.cmb.Header;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.FileReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.resp.FileResp;
import com.zendaimoney.trust.channel.service.MessageService;
import com.zendaimoney.trust.channel.service.TradeResultService;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.Constants.CmbStatus;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.ExceptionUtil;
import com.zendaimoney.trust.channel.util.LogPrn;
import com.zendaimoney.trust.channel.util.ThirdPPCacheContainer;

/**
 * 文件指令处理Action
 * @author mencius
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbBizType = TrustBizType.FILE, cmbCategory = TrustCategory.TRADE)
@Component("com.zendaimoney.trust.channel.action.file.FileAction")
public class FileAction extends TradeActionAbstract {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(FileAction.class);
	
	//@Autowired
	//private RequestService requestService;

	// 报文service
	@Autowired
	private MessageService messageService;
	
	// 交易结果service
	@Autowired
	private TradeResultService tradeResultService;
	
	/**
	 * 
	 */
	@Override
	protected TrustBizReqVo preProcess(TrustBizReqVo vo)
			throws PlatformException {
		//Request request = null;
		try {
			
			return (FileReq) vo;
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“请求报文解析失败”
			/*request = new Request(vo.getChannelReqId(),
					MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);*/
			throw new PlatformException(PlatformErrorCode.VO_2_DTO_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
	}
	
	/**
	 * 构建报文
	 * @param vo
	 * @return Message
	 */
	@Override
	protected Message buildMessage(TrustBizReqVo vo) {
		Message message = new Message();
		Request request = null;
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
	@Override
	protected Message process(Message message, TrustBizReqVo vo) throws PlatformException {
		
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
	 * 解析报文
	 */
	@Override
	protected MessageDto parseMessage(Message message, TrustBizReqVo bizReqVo)
			throws PlatformException {
		MessageDto messageDto = null;
		FileResp fileResp = null;
		Request request = null;
		Header header = null;
		try {
			
			// 报文头
			header = parseHeadMessage(message, bizReqVo);
			
			// 通讯返回码，例如暂停交易，报文校验错等。非CMBMB99时报文体直接原接收报文体返回，不做任何处理
			if (Constants.CmbStatus.CMBMB99.getCode().equals(header.getRetCode())) {
				
				fileResp = new FileResp();
				// 解析响应报文
				ConvertUtils.messageToObj(message, fileResp);
			}
			
			// 解析完之后 将报文头和报文体赋值在报文传输对象上
			messageDto = new MessageDto(header, fileResp);
			
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“响应报文解析失败”
			/*request = new Request(message.getChannelReqId(),
					MessageStatus.RESPONSE_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);*/
			throw new PlatformException(PlatformErrorCode.CMB_PARSE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return messageDto;
	}
	@Override
	protected Header parseHeadMessage(Message message, TrustBizReqVo bizReqVo)
			throws PlatformException {
		// 报文头对象
		Header header = new Header();
		
		try {
			// 解析响应报文
			ConvertUtils.messageToObj(message, header);
		} catch (Exception e) {
			logger.error("====", e);
			throw new PlatformException(PlatformErrorCode.DTO_DECODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return header;
	}
	@Override
	public Response communicationFailHandler(Message message,TrustBizReqVo bizReqVo) {
		// 返回调用方为交易失败
		AttachmentResponse<FileResp> attachmentResponse = new AttachmentResponse<FileResp>();
		// 返回调用方为异常
		attachmentResponse.setCode(Constants.CmbConstants.TRADE_STATE_ABNORMAL.getCode());
		attachmentResponse.setMsg(message.getMessage());
		// 设置操作流水号
		attachmentResponse.setFlowId(message.getChannelReqId());
	

		return attachmentResponse;
	};

	@Override
	protected Response communicationSuccessHandler(MessageDto messageDto,
			Message message, TrustBizReqVo bizReqVo) {
		// 初始化响应结果
		AttachmentResponse<FileResp> attachmentResponse = new AttachmentResponse<FileResp>();
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		Header header = messageDto.getHeader();
		FileResp resp = (FileResp) messageDto.getRespVO();

		
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
			
			if (Constants.CmbStatus.CMBMB99.getCode().equals(retCode)) {
				
				// 成功(交易状态码为 CMBMB99)
				if (Constants.CmbStatus.CMBMB99.getCode().equals(tradeCode)) {
					response.setCode(Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode());
					response.setMsg(StringUtils.isBlank(retMsg) ? Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc() : retMsg);
					
					// 初始化交易结果
					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(), "", bizReqVo.getBizType().getCode(), tradeCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode(),
							message.getChannelReqId());
					// 批量更新操作对象
					BatchOper batchOper = new BatchOper(bizReqVo.getTradeFlow());
					// 记录回盘文件、描述信息、回盘时间
					batchOper.setRespFileName(resp.getRespFilename());
					batchOper.setRespInfo(resp.getMsg());
					batchOper.setRespTime(CalendarUtils.getFormatNow());
					// 业务更新处理对象
					finalResultHandler(null, batchOper, response, bizReqVo);
					
					attachmentResponse.setAttachment(resp);
					// 交易失败 
				} else {
					
					CmbStatus cmbStatus = Constants.CmbStatus.get(tradeCode);
					response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
					response.setMsg(StringUtils.isBlank(retMsg) ? (cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_FAILER.getDesc() : cmbStatus.getDesc()) : retMsg);
					
					// 初始化交易结果
					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(), "", bizReqVo.getBizType().getCode(), tradeCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
							message.getChannelReqId());
					
					// 业务更新处理对象
					//finalResultHandler(tradeResult, message, response, bizReqVo);
					
				} 
			} else {
				CmbStatus cmbStatus = Constants.CmbStatus.get(retCode);
				response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
				response.setMsg(cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_FAILER.getDesc() : cmbStatus.getDesc());
				
				// 初始化交易结果
				tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
						message.getTradeSn(), "", bizReqVo.getBizType().getCode(), retCode,
						(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
								: ""),
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						message.getChannelReqId());
				
				// 业务更新处理对象
				//finalResultHandler(tradeResult, message, response, bizReqVo);
			}
			
			BeanUtils.copyProperties(attachmentResponse, response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			attachmentResponse.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
			attachmentResponse.setMsg(Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
			attachmentResponse.setFlowId(message.getChannelReqId());
		}
		logger.info("交易结果:"+tradeResult.toString());
		return attachmentResponse;
	}

	@Override
	protected Response parseMessageFailHandler(Message message,
			TrustBizReqVo bizReqVo) {
		AttachmentResponse<FileResp> attachmentResponse = new AttachmentResponse<FileResp>();
		
		// 返回调用方为失败
		attachmentResponse.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
		attachmentResponse.setMsg(message.getMessage());
		// 设置操作流水号
		attachmentResponse.setFlowId(message.getChannelReqId());
		return attachmentResponse;
	}

	@Override
	protected Response communicationThrowExceptionHandler(Message message,
			TrustBizReqVo bizReqVo) {
		AttachmentResponse<FileResp> attachmentResponse = new AttachmentResponse<FileResp>();
		// 返回调用方为失败
		attachmentResponse.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
		attachmentResponse.setMsg(message.getMessage());
		// 设置操作流水号
		attachmentResponse.setFlowId(message.getChannelReqId());
		
		Request request = null;

		// 发送报文后相关处理(修改Request状态-请求报文发送失败)
		try {
			// 设置request-状态为“请求报文发送失败”
			/*request = new Request(message.getChannelReqId(),
					MessageStatus.SEND_REQUEST_ERROR.getCode(),
					message.getMessage());
			requestService.update(request);*/
		} catch (Exception e) {
			logger.error("==============", e);
		}

		return attachmentResponse;
	}
	
	
	/**
	 * 交易结果最终态的业务处理
	 * @param tradeResult
	 * @param message
	 * @param response
	 * @param bizReqVo
	 */
	private void finalResultHandler(TradeResult tradeResult, Message message, Response response,
			TrustBizReqVo bizReqVo) {
		try {
			tradeResultService.finalResultHandler(tradeResult);
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
	 * 交易结果最终态的业务处理
	 * @param tradeResult
	 * @param message
	 * @param response
	 * @param bizReqVo
	 */
	private void finalResultHandler(TradeResult tradeResult,BatchOper batchOper, Response response,
			TrustBizReqVo bizReqVo) {
		try {
			tradeResultService.finalResultHandler(tradeResult,batchOper);
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
	 * 接收socket响应失败
	 */
	@Override
	protected Response communicationResponseFailHandler(Message message,
			TrustBizReqVo bizReqVo) {
		AttachmentResponse<FileResp> attachmentResponse = new AttachmentResponse<FileResp>();
		// 返回调用方为失败
		attachmentResponse.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
		attachmentResponse.setMsg(message.getMessage());
		// 设置操作流水号
		attachmentResponse.setFlowId(message.getChannelReqId());
		Request request = null;

		// 发送报文后相关处理(修改Request状态-请求报文发送失败)
		try {
			// 设置request-状态为“请求报文发送失败”
			/*request = new Request(message.getChannelReqId(),
					MessageStatus.SEND_REQUEST_ERROR.getCode(),
					message.getMessage());
			requestService.update(request);*/
		} catch (Exception e) {
			logger.error("==============", e);
		}

		return attachmentResponse;
	}

}
