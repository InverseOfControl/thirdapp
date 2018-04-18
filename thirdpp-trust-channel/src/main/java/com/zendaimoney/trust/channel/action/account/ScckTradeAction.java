package com.zendaimoney.trust.channel.action.account;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.action.TradeActionAbstract;
import com.zendaimoney.trust.channel.annotations.ReqActionAnnotation;
import com.zendaimoney.trust.channel.communication.Message;
import com.zendaimoney.trust.channel.entity.GeneralOper;
import com.zendaimoney.trust.channel.entity.MessageDto;
import com.zendaimoney.trust.channel.entity.MessageInfo;
import com.zendaimoney.trust.channel.entity.Request;
import com.zendaimoney.trust.channel.entity.TradeResult;
import com.zendaimoney.trust.channel.entity.cmb.Header;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.ScckReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.resp.ScckResp;
import com.zendaimoney.trust.channel.service.MessageService;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.service.TradeResultService;
import com.zendaimoney.trust.channel.util.BeanUtils;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.Constants.CmbStatus;
import com.zendaimoney.trust.channel.util.Constants.MessageStatus;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.ExceptionUtil;
import com.zendaimoney.trust.channel.util.JSONHelper;
import com.zendaimoney.trust.channel.util.LogPrn;

@ReqActionAnnotation(cmbBizType = TrustBizType.SCCK, thirdType = ThirdType.CMBPAY, cmbCategory = TrustCategory.TRADE)
@Component(value = "com.zendaimoney.trust.channel.action.account.ScckTradeAction")
public class ScckTradeAction extends TradeActionAbstract{

	private static final LogPrn logger = new LogPrn(ScckTradeAction.class);
	
	@Autowired
	private RequestService requestService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private TradeResultService tradeResultService;
	
	@Override
	protected TrustBizReqVo preProcess(TrustBizReqVo vo) throws PlatformException {
		ScckReq scckReq = null;
		Request request = null;
		try {
			// 请求对象转换
			scckReq = (ScckReq) vo;
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
		return scckReq;
	}

	@Override
	protected MessageDto parseMessage(Message message, TrustBizReqVo bizReqVo) throws PlatformException {
		MessageDto messageDto = null;
		Request request = null;
		Header header = null;
		ScckResp scckResp = null;
		try {
			header = parseHeadMessage(message, bizReqVo);
			if (Constants.CmbStatus.CMBMB99.getCode().equals(header.getRetCode())) {
				scckResp = new ScckResp();
				ConvertUtils.messageToObj(message, scckResp);
			}
			messageDto = new MessageDto(header, scckResp);
		} catch (Exception e) {
			logger.error("====", e);
			request = new Request(message.getChannelReqId(), MessageStatus.RESPONSE_PARSE_ERROR.getCode(), ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.CMB_PARSE_ERROR, ExceptionUtil.getExceptionMessage(e));
		}
		
		return messageDto;
	}

	@Override
	protected Response communicationSuccessHandler(MessageDto messageDto, Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(), Constants.CmbConstants.TRADE_STATE_MIDDLE.getDesc());
		response.setFlowId(message.getChannelReqId());
		
		Header header = messageDto.getHeader();
		ScckResp scckResp = (ScckResp)messageDto.getRespVO();
		Request request = null;
		MessageInfo messageInfo = null;
		
		try {
			request = new Request(message.getChannelReqId(), MessageStatus.RECEIVED_RESPONSE.getCode());
			requestService.update(request);
			
			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(),
					Constants.CMB_PAY_SYS_NO, bizReqVo.getTradeFlow());
			
			// 对于交易请求，需要记录在DB
			if (TrustCategory.TRADE.getCode().equals(bizReqVo.getTrustCategory().getCode())) {
				messageService.insert(messageInfo);
			} else {
				// 仅记录日志
				logger.info("CmbCategory:" + bizReqVo.getTrustCategory()
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}
		} catch (Exception e) {
			logger.error("============", e);
		}
		
		TradeResult tradeResult = null;
		
		String retCode = header == null? "" : header.getRetCode();
		String resultCode = (scckResp == null ? "" : scckResp.getRetCode());
		String resultMsg =  (scckResp == null ? "" : scckResp.getMsg());
		
		setBankResponseCode(response, retCode, resultCode);
		
		// 首先判断文件头中的返回状态码
		if (Constants.CmbStatus.CMBMB99.getCode().equals(retCode)) {
			// 再判断报文中的返回状态码
			if (Constants.CmbStatus.CMBMB99.getCode().equals(resultCode)) {
				response.setCode(Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode());
				response.setMsg(StringUtils.isNotBlank(resultMsg)? resultMsg : Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc());
				
				tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
						message.getTradeSn(), "",
						BizType.TRUST_GOPER.getCode(),
						resultCode, StringUtils.isBlank(resultMsg) ?  Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc() : StringUtils.substring(resultMsg, 0, 150),
						Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode(),
						message.getChannelReqId());
				
				// 更新 操作表
				GeneralOper generalOper = new GeneralOper(bizReqVo.getTradeFlow(), Constants.OPER_STATUS_SUCCESS, CalendarUtils.getFormatNow(), resultMsg);
				// 更新交易结果表
				finalResultHandler(tradeResult, response, generalOper);

				// 返回给调用方
				AttachmentResponse<String> attachResp = new AttachmentResponse<String>();
				BeanUtils.copyNotNullProperties(response, attachResp);
				attachResp.setAttachment(scckResp.getSummaryTradeInfo());
				return attachResp;
				
			} else {
				CmbStatus cmbStatus = CmbStatus.get(resultCode);
				response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
				response.setMsg(StringUtils.isBlank(resultMsg) ? (cmbStatus == null ? Constants.CmbConstants.TRADE_STATE_FAILER.getDesc() : cmbStatus.getDesc()) : resultMsg);
				
				
				tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
						message.getTradeSn(), "",
						BizType.TRUST_GOPER.getCode(),
						resultCode, StringUtils.isNotBlank(resultMsg) ? resultMsg
								: cmbStatus != null ? cmbStatus.getDesc()
										: Constants.CmbConstants.TRADE_STATE_FAILER
												.getDesc(),
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						message.getChannelReqId());
				
				// 更新 操作表
				GeneralOper generalOper = new GeneralOper(bizReqVo.getTradeFlow(), Constants.OPER_STATUS_FAIL, CalendarUtils.getFormatNow(), resultMsg);
				// 更新交易结果表
				finalResultHandler(tradeResult, response, generalOper);
			}
			
		} else {
			CmbStatus cmbStatus = CmbStatus.get(retCode);
			response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
			response.setMsg(cmbStatus != null ? cmbStatus.getDesc() : Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
			
			tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
					message.getTradeSn(), "",
					BizType.TRUST_GOPER.getCode(),
					retCode, cmbStatus != null? cmbStatus.getDesc() : Constants.CmbConstants.TRADE_STATE_FAILER.getDesc(),
					Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
					message.getChannelReqId());
			
			// 更新 操作表
			GeneralOper generalOper = new GeneralOper(bizReqVo.getTradeFlow(), Constants.OPER_STATUS_FAIL, CalendarUtils.getFormatNow(), resultMsg == null ? Constants.CmbConstants.TRADE_STATE_FAILER.getDesc() : resultMsg);
			// 更新交易结果表
			finalResultHandler(tradeResult, response, generalOper);
		}
		return response;
	}

	@Override
	protected Response parseMessageFailHandler(Message message, TrustBizReqVo bizReqVo) {
		// 解析相应报文失败时，由于在 parseMessage 方法中已经更新了request，所以这边不再进行更新操作
		Response response = new Response(Constants.CmbConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
		response.setFlowId(message.getChannelReqId());
		return response;
	}

	@Override
	protected Response communicationThrowExceptionHandler(Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(Constants.CmbConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
		response.setFlowId(message.getChannelReqId());
		try {
			Request request = new Request(message.getChannelReqId(), MessageStatus.SEND_REQUEST_ERROR.getCode(), message.getMessage());
			requestService.update(request);
		} catch (Exception e) {
			logger.error("===========", e);
		}
		return response;
	}

	@Override
	protected Response communicationResponseFailHandler(Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(Constants.CmbConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
		response.setFlowId(message.getChannelReqId());
		try {
			Request request = new Request(message.getChannelReqId(), MessageStatus.SEND_REQUEST_ERROR.getCode(), message.getMessage());
			requestService.update(request);
		} catch (Exception e) {
			logger.error("===========", e);
		}
		return response;
	}
	
	/**
	 * 交易结果最终态的业务处理
	 * @param tradeResult
	 * @param response
	 * @param generalOper
	 */
	private void finalResultHandler(TradeResult tradeResult, Response response, GeneralOper generalOper) {
		try {
			tradeResultService.finalResultHandler(tradeResult, generalOper);
		} catch (Exception e) {
			// 返回状态为-交易失败
			response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER
					.getCode());
			response.setMsg(Constants.CmbConstants.TRADE_STATE_FAILER
					.getDesc());
			
			logger.error("交易最终结果处理异常", e);
		}
	}

	public RequestService getRequestService() {
		return requestService;
	}

	public void setRequestService(RequestService requestService) {
		this.requestService = requestService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public TradeResultService getTradeResultService() {
		return tradeResultService;
	}

	public void setTradeResultService(TradeResultService tradeResultService) {
		this.tradeResultService = tradeResultService;
	}
	
}
