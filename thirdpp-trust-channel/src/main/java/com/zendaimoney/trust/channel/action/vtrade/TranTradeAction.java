package com.zendaimoney.trust.channel.action.vtrade;

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
import com.zendaimoney.trust.channel.entity.CashTransfer;
import com.zendaimoney.trust.channel.entity.MessageDto;
import com.zendaimoney.trust.channel.entity.MessageInfo;
import com.zendaimoney.trust.channel.entity.Request;
import com.zendaimoney.trust.channel.entity.TradeResult;
import com.zendaimoney.trust.channel.entity.cmb.Header;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.TranReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.resp.TranResp;
import com.zendaimoney.trust.channel.service.CashTransferService;
import com.zendaimoney.trust.channel.service.MessageService;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.service.TradeResultService;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.Constants.CmbStatus;
import com.zendaimoney.trust.channel.util.Constants.MessageStatus;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.ExceptionUtil;
import com.zendaimoney.trust.channel.util.JSONHelper;
import com.zendaimoney.trust.channel.util.LogPrn;

/**
 * 单笔转账-虚拟户交易
 * @author 00233197
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbBizType = {TrustBizType.INVS,TrustBizType.REPY,TrustBizType.CASM,TrustBizType.MFEE,TrustBizType.MPAY,TrustBizType.MADV,
		TrustBizType.MREG,TrustBizType.GINN,TrustBizType.GOUT,TrustBizType.GCHG,TrustBizType.GDRW,TrustBizType.ETPM,TrustBizType.OADV,TrustBizType.OREG}, cmbCategory = TrustCategory.TRADE)
@Component("com.zendaimoney.trust.channel.action.vtrade.TranTradeAction")
public class TranTradeAction extends TradeActionAbstract {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(TranTradeAction.class);
	
	@Autowired
	private RequestService requestService;

	// 报文service
	@Autowired
	private MessageService messageService;
	
	// 交易结果service
	@Autowired
	private TradeResultService tradeResultService;
	
	@Autowired
	private CashTransferService cashTransferService;
	
	@Override
	protected TrustBizReqVo preProcess(TrustBizReqVo vo)
			throws PlatformException {
		
		TranReq tranReq = null;
		Request request = null;
		try {
			// 请求对象转换
			tranReq = (TranReq) vo;
			/*tranReq.setTradeType(ThirdPPCacheContainer.tppTradeTypeTothirdTradeTypeMap.get(
					vo.getThirdType().getCode()+vo.getTrustBizType().getCode()));*/
		
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
		return tranReq;
	}

	/**
	 * 解析报文体
	 */
	@Override
	protected MessageDto parseMessage(Message message, TrustBizReqVo bizReqVo)
			throws PlatformException {
		MessageDto messageDto = null;
		TranResp tranResp = null;
		Request request = null;
		Header header = null;
		try {
			
			// 报文头
			header = parseHeadMessage(message, bizReqVo);
			
			// 通讯返回码，例如暂停交易，报文校验错等。非CMBMB99时报文体直接原接收报文体返回，不做任何处理
			if (Constants.CmbStatus.CMBMB99.getCode().equals(header.getRetCode())) {
				
				tranResp = new TranResp();
				// 解析响应报文
				ConvertUtils.messageToObj(message, tranResp);
			}
			
			// 解析完之后 将报文头和报文体赋值在报文传输对象上
			messageDto = new MessageDto(header, tranResp);
		} catch (Exception e) {
			logger.error("====", e);
			// 设置request-状态为“响应报文解析失败”
			request = new Request(message.getChannelReqId(),
					MessageStatus.RESPONSE_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.CMB_PARSE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return messageDto;
	}

	/**
	 * 通信失败的处理
	 */
//	@Override
//	protected Response communicationFailHandler(Message message,
//			TrustBizReqVo bizReqVo) {
//		// 返回调用方为交易失败
//		Response response = new Response(
//				Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
//				message.getMessage());
//		// 设置操作流水号
//		response.setFlowId(message.getChannelReqId());
//		Request request = null;
//
//		// 发送报文后相关处理(修改Request状态-请求报文发送失败)
//		try {
//			// 设置request-状态为“请求报文发送失败”
//			request = new Request(message.getChannelReqId(),
//					MessageStatus.SEND_REQUEST_ERROR.getCode(),
//					message.getMessage());
//			requestService.update(request);
//		} catch (Exception e) {
//			logger.error("==============", e);
//		}
//
//		return response;
//	}
	
	
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
	protected Response communicationSuccessHandler(MessageDto messageDto,
			Message message, TrustBizReqVo bizReqVo) {
		// 初始化响应结果
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
				message.getMessage());
		// 设置操作流水号
		response.setFlowId(message.getChannelReqId());
		Header header = messageDto.getHeader();
		TranResp resp = (TranResp) messageDto.getRespVO();
		Request request = null;
		MessageInfo messageInfo = null;
		
		// 发送报文后相关处理(修改Request状态-响应报文已收到、记录响应报文)
		try {
			// 设置request-状态为“收到响应报文”
			request = new Request(message.getChannelReqId(),
					MessageStatus.RECEIVED_RESPONSE.getCode());
			requestService.update(request);
			
			// 记录响应报文
			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(), Constants.CMB_PAY_SYS_NO, bizReqVo.getTradeFlow());
			// 如果报文需要记录的话
			if (TrustCategory.TRADE.getCode().equals(bizReqVo.getTrustCategory().getCode())) {
				
				messageService.insert(messageInfo);
			} else {
				logger.info("CmbCategory:" + bizReqVo.getTrustCategory()
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}

		} catch (Exception e) {
			logger.error("==============", e);
		}
		
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
					
					// 初始化交易结果
					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(), "",  bizReqVo.getBizType().getCode(), tradeCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode(),
							message.getChannelReqId());
					
					// 转账对象
					CashTransfer tran = new CashTransfer(bizReqVo.getTradeFlow(), Constants.TRANSFER_STATUS_SUCCESS,resp.getMsg(),resp.getBankAccountDate()==null?"":resp.getBankAccountDate()) ;
					// 业务更新处理对象
					finalResultHandler(tradeResult, tran, message, response, bizReqVo);
					
					// 响应对象泛型
					AttachmentResponse<TranResp> attachmentResponse = new AttachmentResponse<TranResp>();
					// 赋值响应结果信息
					BeanUtils.copyProperties(attachmentResponse, response);
					// 将虚拟账户赋值
					attachmentResponse.setAttachment(resp);
					
					return attachmentResponse;
				
				}else if(StringUtils.isBlank(tradeCode) || Constants.CmbStatus.CMBMBXX.getCode().equals(tradeCode) || Constants.CmbStatus.CMBMB05.getCode().equals(tradeCode)){
					CmbStatus cmbStatus = Constants.CmbStatus.get(tradeCode);
					response.setCode(Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode());
					response.setMsg(StringUtils.isBlank(retMsg) ? (cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_MIDDLE.getDesc() : cmbStatus.getDesc()) : retMsg);
					
					// 初始化交易结果
					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(), "",  bizReqVo.getBizType().getCode(), tradeCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(),
							message.getChannelReqId());
					
					// 业务更新处理对象
					midResultHandler(tradeResult, message, bizReqVo);	
				} else {
					
					CmbStatus cmbStatus = Constants.CmbStatus.get(tradeCode);
					response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
					response.setMsg(StringUtils.isBlank(retMsg) ? (cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_FAILER.getDesc() : cmbStatus.getDesc()) : retMsg);
					
					// 初始化交易结果
					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(), "",  bizReqVo.getBizType().getCode(), tradeCode,
							(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
									: ""),
							Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
							message.getChannelReqId());
					
					// 账户资金对象
					CashTransfer tran = new CashTransfer(bizReqVo.getTradeFlow(), Constants.TRANSFER_STATUS_FAIL,resp.getMsg(),resp.getBankAccountDate()==null?"":resp.getBankAccountDate());
					// 业务更新处理对象
					finalResultHandler(tradeResult, tran, message, response, bizReqVo);
					
				}
			} else {
				CmbStatus cmbStatus = Constants.CmbStatus.get(retCode);
				response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
				response.setMsg(cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_FAILER.getDesc() : cmbStatus.getDesc());
				
				// 初始化交易结果
				tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
						message.getTradeSn(), "",  bizReqVo.getBizType().getCode(), retCode,
						(retMsg != null ? StringUtils.substring(retMsg, 0, 150)
								: ""),
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						message.getChannelReqId());
				
				// 转账对象
				CashTransfer tran = new CashTransfer(bizReqVo.getTradeFlow(), Constants.TRANSFER_STATUS_FAIL,retMsg,null);
				// 业务更新处理对象
				finalResultHandler(tradeResult, tran, message, response, bizReqVo);
			}
			
		} catch (Exception e) {
			logger.error("==============", e);
		}
		
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
	 * @param message
	 * @param response
	 * @param bizReqVo
	 */
	private void finalResultHandler(TradeResult tradeResult, CashTransfer tran, Message message, Response response,
			TrustBizReqVo bizReqVo) {
		try {
			tradeResultService.finalResultHandler(tradeResult, tran);
		} catch (Exception e) {
			// 返回状态为-交易中间态
			response.setCode(Constants.CmbConstants.TRADE_STATE_MIDDLE
					.getCode());
			response.setMsg(Constants.CmbConstants.TRADE_STATE_MIDDLE
					.getDesc());
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
