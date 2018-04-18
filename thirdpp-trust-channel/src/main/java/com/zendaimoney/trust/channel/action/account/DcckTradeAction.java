package com.zendaimoney.trust.channel.action.account;

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
import com.zendaimoney.trust.channel.entity.MessageInfo;
import com.zendaimoney.trust.channel.entity.Request;
import com.zendaimoney.trust.channel.entity.TradeResult;
import com.zendaimoney.trust.channel.entity.cmb.Header;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;
import com.zendaimoney.trust.channel.pub.vo.req.DcckReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.resp.FileResp;
import com.zendaimoney.trust.channel.service.MessageService;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.service.TradeResultService;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.Constants.CmbStatus;
import com.zendaimoney.trust.channel.util.Constants.MessageStatus;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.ExceptionUtil;
import com.zendaimoney.trust.channel.util.JSONHelper;
import com.zendaimoney.trust.channel.util.LogPrn;
/**
 * 每日交易明细对账 - 对账
 * @author 00233197
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbCategory = TrustCategory.TRADE, cmbBizType = TrustBizType.DCCK)
@Component(value = "com.zendaimoney.trust.channel.action.account.DcckTradeAction")
public class DcckTradeAction extends TradeActionAbstract {

	private static final LogPrn logger = new LogPrn(DcckTradeAction.class);
	
	@Autowired
	private RequestService requestService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private TradeResultService tradeResultService;
	
	@Override
	protected TrustBizReqVo preProcess(TrustBizReqVo vo) throws PlatformException {
		Request request = null;
		DcckReq dcckReq = null;
		try {
			dcckReq = (DcckReq) vo;
		} catch (Exception e) {
			request = new Request(vo.getChannelReqId(),
					MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			
			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.VO_2_DTO_ERROR, ExceptionUtil.getExceptionMessage(e));
		}
		return dcckReq;
	}

	@Override
	protected MessageDto parseMessage(Message message, TrustBizReqVo bizReqVo) throws PlatformException {
		try {
			Header header = parseHeadMessage(message, bizReqVo);
			FileResp fileResp = new FileResp();
			if (Constants.CmbStatus.CMBMB99.getCode().equals(header.getRetCode())) {
				ConvertUtils.messageToObj(message, fileResp);
			}
			MessageDto messageDto = new MessageDto(header, fileResp);
			return messageDto;
		} catch (Exception e) {
			logger.error("====", e);
			Request request = new Request(bizReqVo.getChannelReqId(),
					MessageStatus.RESPONSE_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.CMB_PARSE_ERROR, ExceptionUtil.getExceptionMessage(e));
		} 
	}

	@Override
	protected Response communicationSuccessHandler(MessageDto messageDto, Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode(), Constants.CmbConstants.TRADE_STATE_MIDDLE.getDesc());
		response.setFlowId(message.getChannelReqId());
		
		Header header = messageDto.getHeader();
		FileResp fileResp = (FileResp)messageDto.getRespVO();
		
		Request request = null;
		MessageInfo messageInfo = null;
		try {
			request = new Request(message.getChannelReqId(), MessageStatus.RECEIVED_RESPONSE.getCode());
			requestService.update(request);
			
			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(),
					Constants.CMB_PAY_SYS_NO, bizReqVo.getTradeFlow());
			
			if (TrustCategory.TRADE.getCode().equals(bizReqVo.getTrustCategory().getCode())) {
				messageService.insert(messageInfo);
			} else {
				logger.info("CmbCategory:" + bizReqVo.getTrustCategory()
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}
		} catch (Exception e) {
			logger.error("=============", e);
		}
		
		TradeResult tradeResult = null;
		String headerRetCode = header == null? "" : header.getRetCode();
		String bodyRetCode = fileResp == null ? "" : fileResp.getRetCode();
		String bodyMsg = fileResp == null ? "" : fileResp.getMsg();
		
		setBankResponseCode(response, headerRetCode, bodyRetCode);
		
		try {
			if (Constants.CmbStatus.CMBMB99.getCode().equals(headerRetCode)) {
				
				if (Constants.CmbStatus.CMBMB99.getCode().equals(bodyRetCode)) {
					// 表示成功
					response.setCode(Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode());
					response.setMsg(StringUtils.isBlank(bodyMsg)?Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc() :bodyMsg);
					
					tradeResult = new TradeResult(
							Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(),
							"",
							bizReqVo.getBizType().getCode(),
							bodyRetCode,
							StringUtils.isBlank(bodyMsg) ? "" : StringUtils.substring(bodyMsg, 0, 150),
							Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode(),
							message.getChannelReqId());
					
					// 批量更新操作对象
					BatchOper batchOper = new BatchOper(bizReqVo.getTradeFlow());
					// 记录回盘文件、描述信息、回盘时间
					batchOper.setRespFileName(fileResp.getRespFilename());
					batchOper.setRespInfo(fileResp.getMsg());
					batchOper.setRespTime(CalendarUtils.getFormatNow());
					
					finalResultHandler(tradeResult, response, batchOper);
					
					AttachmentResponse<String> attachmentResponse = new AttachmentResponse<String>();
					BeanUtils.copyProperties(attachmentResponse, response);
					attachmentResponse.setAttachment(fileResp.getRespFilename());
					return attachmentResponse;
				} else {
					// headerRetCode == CMBMB99 && bodyRetCode != CMBMB99
					CmbStatus cmbStatus = CmbStatus.get(bodyRetCode); // 找到指定的错误返回码
					response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
					response.setMsg(StringUtils.isBlank(bodyMsg)? cmbStatus != null? cmbStatus
							.getDesc():Constants.CmbConstants.TRADE_STATE_FAILER.getDesc() : bodyMsg);
				
					tradeResult = new TradeResult(
							Constants.CMB_PAY_SYS_NO,
							message.getTradeSn(),
							"",
							bizReqVo.getBizType().getCode(),
							bodyRetCode,
							StringUtils.isBlank(bodyMsg) ? "" : StringUtils.substring(bodyMsg, 0, 150),
							Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
							message.getChannelReqId());
					
					finalResultHandler(tradeResult, response, null);
				}
				
			} else {
				 // headerRetCode != CMBMB99(报文头中直接返回失败，则报文体则是原请求报文)
				CmbStatus cmbStatus = CmbStatus.get(headerRetCode); // 找到指定的错误返回码
				response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER.getCode());
				response.setMsg(cmbStatus != null ? cmbStatus.getDesc(): Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());
			
				tradeResult = new TradeResult(
						Constants.CMB_PAY_SYS_NO,
						message.getTradeSn(),
						"",
						bizReqVo.getBizType().getCode(),
						headerRetCode,
						StringUtils.isBlank(bodyMsg) ? "" : StringUtils.substring(bodyMsg, 0, 150),
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						message.getChannelReqId());
				
				finalResultHandler(tradeResult, response, null);
			}
		} catch (Exception e) {
			logger.error("========", e);
		}
		
		return response;
	}

	@Override
	protected Response parseMessageFailHandler(Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
				message.getMessage());
		response.setFlowId(message.getChannelReqId());
		return response;
	}

	@Override
	protected Response communicationThrowExceptionHandler(Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
				message.getMessage());
		response.setFlowId(message.getChannelReqId());
		
		try {
			Request request = new Request(message.getChannelReqId(),
					MessageStatus.SEND_REQUEST_ERROR.getCode(),
					message.getMessage());
			requestService.update(request);
		} catch (Exception e) {
			logger.error("=========", e);
		}
		return response;
	}

	@Override
	protected Response communicationResponseFailHandler(Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
				message.getMessage());
		response.setFlowId(message.getChannelReqId());
		
		try {
			Request request = new Request(message.getChannelReqId(),
					MessageStatus.SEND_REQUEST_ERROR.getCode(),
					message.getMessage());
			requestService.update(request);
		} catch (Exception e) {
			logger.error("=========", e);
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
	private void finalResultHandler(TradeResult tradeResult, Response response, BatchOper batchOper) {
		try {
			tradeResultService.finalResultHandler(tradeResult, batchOper);
		} catch (Exception e) {
			// 返回状态为-交易失败
			response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER
					.getCode());
			response.setMsg(Constants.CmbConstants.TRADE_STATE_FAILER
					.getDesc());
			
			logger.error("交易最终结果处理异常", e);
		}
	}
	
}
