package com.zendaimoney.trust.channel.action.query;


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
import com.zendaimoney.trust.channel.pub.vo.req.RcqyReq;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.pub.vo.resp.RcqyResp;
import com.zendaimoney.trust.channel.service.MessageService;
import com.zendaimoney.trust.channel.service.RequestService;
import com.zendaimoney.trust.channel.service.TradeResultService;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.Constants.CmbStatus;
import com.zendaimoney.trust.channel.util.Constants.MessageStatus;
import com.zendaimoney.trust.channel.util.BeanUtils;
import com.zendaimoney.trust.channel.util.CalendarUtils;
import com.zendaimoney.trust.channel.util.ConvertUtils;
import com.zendaimoney.trust.channel.util.ExceptionUtil;
import com.zendaimoney.trust.channel.util.JSONHelper;
import com.zendaimoney.trust.channel.util.LogPrn;
/**
 * 存管专户入账交易查询RCQY - 查询
 * @author 00233197
 *
 */
@ReqActionAnnotation(thirdType = ThirdType.CMBPAY, cmbCategory = TrustCategory.TRADE, cmbBizType = TrustBizType.RCQY)
@Component(value = "com.zendaimoney.trust.channel.action.query.RcqyTradeAction")
public class RcqyTradeAction extends TradeActionAbstract{

	private static final LogPrn logger = new LogPrn(RcqyTradeAction.class);
	
	@Autowired
	private RequestService requestService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private TradeResultService tradeResultService;
	
	@Override
	protected TrustBizReqVo preProcess(TrustBizReqVo vo) throws PlatformException {
		Request request = null;
		RcqyReq rcqyReq = null;
		try {
			rcqyReq = (RcqyReq)vo;
		} catch (Exception e) {
			logger.error("==========", e);
			request = new Request(vo.getChannelReqId(), MessageStatus.SEND_REQUEST_PARSE_ERROR.getCode(), ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);
			throw new PlatformException(PlatformErrorCode.VO_2_DTO_ERROR, ExceptionUtil.getExceptionMessage(e));
		}
		return rcqyReq;
	}

	@Override
	protected MessageDto parseMessage(Message message, TrustBizReqVo bizReqVo) throws PlatformException {
		Request request = null;
		MessageDto messageDto = null;
		RcqyResp rcqyResp = null;
		try {
			Header header = parseHeadMessage(message, bizReqVo);
			if (Constants.CmbStatus.CMBMB99.getCode().equals(header.getRetCode())) {
				rcqyResp = new RcqyResp();
				ConvertUtils.messageToObj(message, rcqyResp);
			}
			messageDto = new MessageDto(header, rcqyResp);
		} catch (Exception e) {
			logger.error("=========", e);

			request = new Request(message.getChannelReqId(),
					MessageStatus.RESPONSE_PARSE_ERROR.getCode(),
					ExceptionUtil.getExceptionPartyMessage(e));
			requestService.update(request);

			throw new PlatformException(PlatformErrorCode.CMB_PARSE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return messageDto;
	}

	@Override
	protected Response communicationSuccessHandler(MessageDto messageDto, Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(
				Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());

		response.setFlowId(message.getChannelReqId());

		Header header = messageDto.getHeader();
		RcqyResp rcqyResp = (RcqyResp) messageDto.getRespVO();
		Request request = null;
		MessageInfo messageInfo = null;
		try {
			request = new Request(message.getChannelReqId(),
					Constants.MessageStatus.RECEIVED_RESPONSE.getCode());
			requestService.update(request);

			messageInfo = new MessageInfo(message.getChannelReqId(),
					MessageInfo.MSG_TYPE_S, message.getResponseInfo(),
					Constants.CMB_PAY_SYS_NO, bizReqVo.getTradeFlow());

			if (TrustCategory.TRADE.getCode().equals(
					bizReqVo.getTrustCategory().getCode())) {
				messageService.insert(messageInfo);
			} else {
				logger.info("CmbCategory:" + bizReqVo.getTrustCategory()
						+ ",messageInfo:" + JSONHelper.bean2json(messageInfo));
			}

			String headerRetCode = (header != null ? header.getRetCode() : "");
			String bodyRetCode = (rcqyResp != null ? rcqyResp.getRetCode() : "");
			String bodyMsg = (rcqyResp != null ? rcqyResp.getTradeDesc() : "");

			setBankResponseCode(response, headerRetCode, bodyRetCode);
			
			TradeResult tradeResult = null;
			// 报文头返回成功，是前提条件
			if (Constants.CmbStatus.CMBMB99.getCode().equals(headerRetCode)) {
				if (Constants.CmbStatus.CMBMB99.getCode().equals(bodyRetCode)) {
					// 报文体成功，则认为是成功
					response.setCode(Constants.CmbConstants.TRADE_STATE_SUCCESS
							.getCode());
					response.setMsg(StringUtils.isBlank(bodyMsg) ? Constants.CmbConstants.TRADE_STATE_SUCCESS.getDesc():bodyMsg);

					tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
							bizReqVo.getTradeFlow(), "",
							bizReqVo.getBizType().getCode(), bodyRetCode,
							StringUtils.isBlank(bodyMsg) ? "" : StringUtils.substring(bodyMsg, 0, 150),
							Constants.CmbConstants.TRADE_STATE_SUCCESS
									.getCode(), message.getChannelReqId());

					// 批量更新操作对象
					BatchOper batchOper = new BatchOper(bizReqVo.getTradeFlow());
					// 记录回盘文件、描述信息、回盘时间
					batchOper.setRespFileName(rcqyResp.getFileName());
					batchOper.setRespInfo(rcqyResp.getTradeDesc());
					batchOper.setRespTime(CalendarUtils.getFormatNow());

					finalResultHandler(tradeResult, batchOper, response);

					AttachmentResponse<RcqyResp> ap = new AttachmentResponse<RcqyResp>();
					BeanUtils.copyNotNullProperties(response, ap);
					ap.setAttachment(rcqyResp);
					return ap;
				} else {
					// 根据招商报文体中的返回码，映射到指定的对象，要是改对象为null，则说明该返回码不在文档定义的返回码列表中。
					CmbStatus cmbStatus = CmbStatus.get(bodyRetCode);

					response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER
							.getCode());
					response.setMsg(StringUtils.isNotBlank(bodyMsg) ? bodyMsg
							: cmbStatus != null ? cmbStatus.getDesc()
									: Constants.CmbConstants.TRADE_STATE_FAILER
											.getDesc());

					tradeResult = new TradeResult(
							Constants.CMB_PAY_SYS_NO,
							bizReqVo.getTradeFlow(),
							"",
							bizReqVo.getBizType().getCode(),
							bodyRetCode,
							StringUtils.isBlank(bodyMsg) ? "" : StringUtils.substring(bodyMsg, 0, 150),
							Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
							message.getChannelReqId());

					finalResultHandler(tradeResult, null, response);
				}
			} else {
				// 根据招商报文头中的返回码，映射到指定的对象，要是改对象为null，则说明该返回码不在文档定义的返回码列表中。
				CmbStatus cmbStatus = CmbStatus.get(headerRetCode);

				response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER
						.getCode());
				response.setMsg(cmbStatus != null ? cmbStatus.getDesc()
						: Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());

				tradeResult = new TradeResult(Constants.CMB_PAY_SYS_NO,
						bizReqVo.getTradeFlow(), "",
						bizReqVo.getBizType().getCode(), headerRetCode,
						StringUtils.isBlank(bodyMsg) ? "" : StringUtils.substring(bodyMsg, 0, 150),
						Constants.CmbConstants.TRADE_STATE_FAILER.getCode(),
						message.getChannelReqId());

				finalResultHandler(tradeResult, null, response);

			}
		} catch (Exception e) {
			logger.error("==============", e);
		}
		return response;
	}

	@Override
	protected Response parseMessageFailHandler(Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(Constants.CmbConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
		response.setFlowId(message.getChannelReqId());
		return response;
	}

	@Override
	protected Response communicationThrowExceptionHandler(Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(Constants.CmbConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
		response.setFlowId(message.getChannelReqId());
		
		Request request = null;
		try {
			request = new Request(message.getChannelReqId(), MessageStatus.SEND_REQUEST_ERROR.getCode(), message.getMessage());
			requestService.update(request);
		} catch (Exception e) {
			logger.error("=========", e);
		}
		return response;
	}

	@Override
	protected Response communicationResponseFailHandler(Message message, TrustBizReqVo bizReqVo) {
		Response response = new Response(Constants.CmbConstants.TRADE_STATE_FAILER.getCode(), message.getMessage());
		response.setFlowId(message.getChannelReqId());
		
		Request request = null;
		try {
			request = new Request(message.getChannelReqId(), MessageStatus.SEND_REQUEST_ERROR.getCode(), message.getMessage());
			requestService.update(request);
		} catch (Exception e) {
			logger.error("=========", e);
		}
		return response;
	}

	/**
	 * 交易结果最终态的业务处理
	 * 
	 * @param tradeResult
	 * @param message
	 * @param response
	 * @param bizReqVo
	 */
	private void finalResultHandler(TradeResult tradeResult, BatchOper batchOper,
			Response response) {
		try {
			tradeResultService.finalResultHandler(tradeResult, batchOper);
		} catch (Exception e) {
			// 返回状态为-交易中间态
			response.setCode(Constants.CmbConstants.TRADE_STATE_FAILER
					.getCode());
			response.setMsg(Constants.CmbConstants.TRADE_STATE_FAILER.getDesc());

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
