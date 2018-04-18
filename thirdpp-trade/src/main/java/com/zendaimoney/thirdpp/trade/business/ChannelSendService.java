package com.zendaimoney.thirdpp.trade.business;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.zendaimoney.thirdpp.channel.pub.vo.*;
import com.zendaimoney.thirdpp.trade.entity.*;

import com.zendaimoney.thirdpp.trade.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.pub.service.IBaseChannelService;
import com.zendaimoney.thirdpp.channel.pub.service.IChannelService;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.CardBinResponseVo;
import com.zendaimoney.thirdpp.trade.util.CalendarUtils;
import com.zendaimoney.thirdpp.trade.util.Constants;
import com.zendaimoney.thirdpp.trade.util.LogPrn;

@Component
@Transactional
public class ChannelSendService {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(ChannelSendService.class);

	@Autowired
	private IChannelService channelServiceConsumer;

    @Autowired
	private IBaseChannelService baseChannelServiceConsumer;
	
	@Autowired
	private CollectInfoService collectInfoService;

	@Autowired
	private SignMessageInfoService signMessageInfoService;

	@Autowired
	private SignInfoService signInfoService;

	@Autowired
	private PayInfoService payInfoService;
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	private OperationRequestService operationRequestService;
	
	@Autowired
	private PayTaskService payTaskService;

	/**
	 * 将交易数据发送到通道
	 * 
	 * @param collectInfo
	 */
	public Response callChannel(CollectInfo collectInfo) {
		CollectReqVo collectReqVo = new CollectReqVo();
		// 待更新CollectInfo
		CollectInfo updateCollectInfo = null;
		Response response = null;
		try {
			BeanUtils.copyProperties(collectInfo, collectReqVo);
			// 设置业务系统号
			collectReqVo.setBizSys(BizSys.get(collectInfo.getBizSysNo()));
			// 设置业务类型
			collectReqVo.setBizType(BizType.get(collectInfo.getBizTypeNo()));
			// 设置第三方通道编码
			collectReqVo.setThirdType(ThirdType.get(collectInfo.getPaySysNo()));
			// 设置信息类别编码
			collectReqVo.setInfoCategoryCode(collectInfo.getInfoCategoryCode());
			response = channelServiceConsumer.collectCommond(collectReqVo);
			logger.info("response code:" + response.getCode() + ",tradeFlow:"
					+ collectReqVo.getTradeFlow());
			// 如果是交易异常，把交易明细状态修改为交易异常,其他情况(交易成功、交易失败、交易处理中)都叫由支付通道处理
			if (Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode().equals(
					response.getCode())) {
				updateCollectInfo = new CollectInfo();
				// 设置交易流水号
				updateCollectInfo.setTradeFlow(collectInfo.getTradeFlow());
				// 设置交易状态-交易异常
				updateCollectInfo
						.setStatus(Constants.TppConstants.TRADE_STATE_ABNORMAL
								.getCode());
				// 设置通知查询状态-不需要通知查询
				updateCollectInfo
						.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_NO);
				
				// 设置回盘时间
				updateCollectInfo.setThirdReturnTime(CalendarUtils
						.parsefomatCalendar(Calendar.getInstance(),
								CalendarUtils.LONG_FORMAT));
				// 设置失败原因(取前面200个字符)
				updateCollectInfo
						.setFailReason(response.getMsg() != null ? StringUtils
								.substring(response.getMsg(), 0, 200) : "");
				collectInfoService.update(updateCollectInfo);

				// 写入通知mq
				collectInfoService.sendNotifyMergeMsg(collectInfo);
			}
		} catch (Exception e) {
			logger.error("调用TPP通道异常", e);
			// 写MONGODB
			try {
				collectInfoService.sendNotifyQueryMsg(collectReqVo);
			} catch (Exception e1) {
				logger.error("=============", e1);
			}
			// 如果是异常，就认为是交易处理中(不确定交易)
			response = new Response(
					Constants.TppConstants.TRADE_STATE_MIDDLE.getCode());
			return response;

		}

		return response;

	}
	
	/**
	 * 将交易数据发送到通道 代付
	 * 
	 * @param payInfo
	 */
	public Response callChannel(PayInfo payInfo) {
		PayReqVo payReqVo = new PayReqVo();
		// 待更新PayInfo
		PayInfo updatePayInfo = null;
		Response response = null;
		try {
			BeanUtils.copyProperties(payInfo, payReqVo);
			// 设置业务系统号
			payReqVo.setBizSys(BizSys.get(payInfo.getBizSysNo()));
			// 设置业务类型
			payReqVo.setBizType(BizType.get(payInfo.getBizType()));
			// 设置第三方通道编码
			payReqVo.setThirdType(ThirdType.get(payInfo.getPaySysNo()));
			// 设置信息类别编码
			payReqVo.setInfoCategoryCode(payInfo.getInfoCategoryCode());
			response = channelServiceConsumer.payCommond(payReqVo);
			
			logger.info("channel resp[code:" + response.getCode() + " msg:" + response.getMsg() + ",tradeFlow:" + payInfo.getTradeFlow() + "]");
			
		} catch (Exception e) {
			logger.error("调用TPP通道异常", e);
			// 暂时不写MONGODB
			try {
				
				logger.info("ReqID:" + payInfo.getReqId() + ",暂未入mongo库");
//				payInfoService.sendNotifyQueryMsg(payReqVo, "调用TPP通道异常");
				
//				if (!Constants.TppConstants.TRADE_STATE_MIDDLE.getCode().equals(
//						response.getCode())) {
//					updatePayTask(payInfo, response);
//				}
			} catch (Exception e1) {
				logger.error("=============", e1);
			}
			// 如果是异常，就认为是交易处理中(不确定交易)
			response = new Response(
					Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
					"调用TPP通道异常");
			return response;

		}
		
		// 如果代付明细结果 非中间态，则更新代付任务表交易状态
		try {

			if (!Constants.TppConstants.TRADE_STATE_MIDDLE.getCode().equals(
					response.getCode())) {
				updatePayInfo = new PayInfo();
				updatePayInfo.setTaskId(payInfo.getTaskId());
				// 设置交易流水号
				updatePayInfo.setTradeFlow(payInfo.getTradeFlow());
				// 设置交易状态
				updatePayInfo.setStatus(response.getCode());
				
				//金额
				updatePayInfo.setAmount(payInfo.getAmount());
				
				// 设置失败原因(取前面200个字符)
				updatePayInfo
						.setFailReason(response.getMsg() != null ? StringUtils
								.substring(response.getMsg(), 0, 200) : "");

				updatePayInfo.setTransRepCode(response.getBankRepCode());

				updatePayTask(updatePayInfo, response);
			}
		} catch (Exception e) {
			logger.error("trade处理异常", e);
			// 如果是异常，就认为是交易处理中(不确定交易)
			response = new Response(
					Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
					Constants.TppConstants.TRADE_STATE_MIDDLE.getDesc());
			return response;
		}
		

		return response;

	}

	/**
	 * 将交易数据发送到通道(协议支付-签约短信)
	 * @param signMessageInfo
	 * @return
	 */
	public Response callChannel(SignMessageInfo signMessageInfo){
		SignMsgReqVo signMsgReqVo = new SignMsgReqVo();
		Response response;

		try{
			BeanUtils.copyProperties(signMessageInfo, signMsgReqVo);
			// 设置业务系统号
			signMsgReqVo.setBizSys(BizSys.get(signMessageInfo.getBizSysNo()));
			// 设置业务类型
			signMsgReqVo.setBizType(BizType.get(signMessageInfo.getBizTypeNo()));
			// 设置信息类别编码
			signMsgReqVo.setInfoCategoryCode(signMessageInfo.getInfoCategoryCode());
			// 设置第三方通道
			signMsgReqVo.setThirdType(ThirdType.get(signMessageInfo.getPaySysNo()));

			response = channelServiceConsumer.signMessageCommond(signMsgReqVo);
			logger.info("将交易数据发送到通道，response:"+ JSONObject.toJSONString(response));

			// 如果是交易异常，把交易明细状态修改为交易异常,其他情况(交易成功、交易失败、交易处理中)都叫由支付通道处理
			if (Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode().equals(response.getCode())){
				// 调用状态为 异常
				signMessageInfo.setStatus(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());
				// 调用失败原因(200字以内)
				String failReason = "";
				if(StringUtils.isNotEmpty(response.getMsg())){
					StringUtils.substring(response.getMsg(), 0, 200);
				}
				signMessageInfo.setFailReason(failReason);
				signMessageInfoService.saveOrUpdateSignInfo(signMessageInfo);
			}
		}catch (Exception e){
			logger.error("调用TPP通道(协议支付-签约短信)异常", e);
			// 如果是异常，就认为是交易处理中(不确定交易)
			response = new Response(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());

			return response;
		}

		return response;
	}

	/**
	 * 将交易数据发送到通道(协议支付-签约)
	 * @param signInfo
	 * @return
	 */
	public Response callChannel(SignInfo signInfo){
		SignReqVo signReqVo = new SignReqVo();
		Response response;

		try{
			BeanUtils.copyProperties(signInfo, signReqVo);
			// 设置业务系统号
			signReqVo.setBizSys(BizSys.get(signInfo.getBizSysNo()));
			// 设置业务类型
			signReqVo.setBizType(BizType.get(signInfo.getBizTypeNo()));
			// 设置信息类别编码
			signReqVo.setInfoCategoryCode(signInfo.getInfoCategoryCode());
			// 设置第三方通道
			signReqVo.setThirdType(ThirdType.get(signInfo.getPaySysNo()));

			response = channelServiceConsumer.signCommond(signReqVo);
			logger.info("将交易数据发送到通道，response:"+ JSONObject.toJSONString(response));

			// 如果是异常，把明细状态修改为异常,其他情况(成功、失败、处理中)都叫由支付通道处理
			if (Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode().equals(response.getCode())){
				// 调用状态为 异常
				signInfo.setStatus(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());
				// 调用失败原因(200字以内)
				String failReason = "";
				if(StringUtils.isNotEmpty(response.getMsg())){
					StringUtils.substring(response.getMsg(), 0, 200);
				}
				signInfo.setFailReason(failReason);
				signInfoService.saveOrUpdateSignInfo(signInfo);
			}
		}catch (Exception e){
			logger.error("调用TPP通道(协议支付-签约)异常", e);
			// 如果是异常，就认为是交易处理中(不确定交易)
			response = new Response(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());

			return response;
		}

		return response;
	}

	/**
	 * 更新代付任务表 (临时由TRADE层负责更新代付任务表)
	 * @param updatePayInfo
	 * @param response
	 * @throws SQLException 
	 */
	public void updatePayTask(PayInfo updatePayInfo, Response response) {
		
		// 更新代付任务对象
		PayTask payTask = new PayTask();
		payTask.setId(updatePayInfo.getTaskId()); // 任务ID
		payTask.setTradeStatus(response.getCode()); // 通道层返回交易状态码
		payTask.setTradeResultInfo(response.getMsg() != null ? 
				StringUtils.substring(response.getMsg(), 0, 200) : ""); // 交易结果描述
		
		// 交易成功时 更新task表中成功金额
		if (Constants.TppConstants.TRADE_STATE_SUCCESS.getCode().equals(response.getCode())) {
			
			payTask.setTradeSuccessAmount(updatePayInfo.getAmount()); 
		}
		// 更新代付明细表
		payTaskService.updatePayInfo(updatePayInfo);
		// 更新代付任务表
		payTaskService.update(payTask);
		
	}
	
	/**
	 * 调用 channel进行第三方验证，根据channel 返回的结果更新 operation request 状态
	 * @param oar
	 * @return
	 */
	public Response channelBankCardAuth(OperationRequest oar){
		
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);
		OperationRequest updateOperationRequest = null;
		BankCardAuthReqVo reqVo = new BankCardAuthReqVo();
		initBankCardAuthReqVo(oar, reqVo);
		
		try {
			response = baseChannelServiceConsumer.bankCardAuthCommond(reqVo);
		} catch (Exception e) {
			logger.error("调用TPP通道异常", e);
			try {
				updateOperationRequest = new OperationRequest();
				updateOperationRequest.setReqId(oar.getReqId());
				updateOperationRequest.setStatus(OperationRequest.StatusEnum.fail.getStatusCode());
				updateOperationRequest.setRespInfo(cutMessage(e.getMessage()));
				operationRequestService.update(updateOperationRequest);
			} catch (Exception e1) {
				logger.error("更新账户认证请求表异常", e1);
			}
			response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
			response.setMsg("操作超时，请重试");
			return response;
		}
		
		logger.info("请求通道账户认证返回 ：code = " + response.getCode());

		updateOperationRequest = new OperationRequest();
		updateOperationRequest.setReqId(oar.getReqId());
		updateOperationRequest.setRespCode(response.getCode());
		updateOperationRequest.setRespInfo(cutMessage(response.getMsg()));
		updateOperationRequest.setRespTime(new Date());
		
		if (Constants.TppConstants.TRADE_STATE_SUCCESS.getCode().equals(
				response.getCode())) {
			updateOperationRequest.setStatus(OperationRequest.StatusEnum.success.getStatusCode());
		} else if (Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode().equals(
				response.getCode())){
			response.setMsg(PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage());
		} else {
			updateOperationRequest.setStatus(OperationRequest.StatusEnum.fail.getStatusCode());
		}
		try {
			operationRequestService.update(updateOperationRequest);
		} catch (Exception e) {
			logger.error("更新操作请求表异常", e);
			response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
			response.setMsg("操作超时，请重试");
			return response;
		}
		return response;
	}

	@SuppressWarnings("rawtypes")
	public AttachmentResponse<CardBinResponseVo> channelBankCardBinQuery(OperationRequest oar){
		
		AttachmentResponse<CardBinResponseVo> response = new AttachmentResponse<CardBinResponseVo>();
		response.setCode(Response.SUCCESS_RESPONSE_CODE);
		
		OperationRequest updateOperationRequest = null;
		BankCardBinQueryReqVO bankCardBinQueryReqVo = new BankCardBinQueryReqVO();
		initCardBinQueryReqVo(oar, bankCardBinQueryReqVo);
		
		AttachmentResponse channelResponse = null;
		try {
			channelResponse = (AttachmentResponse)baseChannelServiceConsumer.bankCardBinQueryCommond(bankCardBinQueryReqVo);
		} catch (Exception e) {
			logger.error("调用TPP通道异常", e);
			try {
				updateOperationRequest = new OperationRequest();
				updateOperationRequest.setReqId(oar.getReqId());
				updateOperationRequest.setStatus(OperationRequest.StatusEnum.fail.getStatusCode());
				updateOperationRequest.setRespInfo(cutMessage(e.getMessage()));
				operationRequestService.update(updateOperationRequest);
			} catch (Exception e1) {
				logger.error("更新账户认证请求表异常", e1);
			}
			response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
			response.setMsg("操作超时，请重试");
			return response;
		}
		
		logger.info("请求通道卡bin返回 ：code = " + channelResponse.getCode());	
		updateOperationRequest = new OperationRequest();
		updateOperationRequest.setReqId(oar.getReqId());
		updateOperationRequest.setRespCode(channelResponse.getCode());
		updateOperationRequest.setRespInfo(cutMessage(channelResponse.getMsg()));
		updateOperationRequest.setRespTime(new Date());
		
		response.setCode(channelResponse.getCode());
		if (Constants.TppConstants.TRADE_STATE_SUCCESS.getCode().equals(
			channelResponse.getCode())) {
			
			CardBinResponseVo cardBinResponseVo = new CardBinResponseVo();
			BankCardBinQueryRespVO attachObject = (BankCardBinQueryRespVO)channelResponse.getAttachment();
			BeanUtils.copyProperties(attachObject, cardBinResponseVo);
			response.setAttachment(cardBinResponseVo);
			
			updateOperationRequest.setStatus(OperationRequest.StatusEnum.success.getStatusCode());
		} else if (Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode().equals(
				channelResponse.getCode())){
			response.setMsg(PlatformErrorCode.SYSTEM_BUSY.getDefaultMessage());
			updateOperationRequest.setStatus(OperationRequest.StatusEnum.fail.getStatusCode());
		} else {
			response.setMsg(channelResponse.getMsg());
			updateOperationRequest.setStatus(OperationRequest.StatusEnum.fail.getStatusCode());
		}

		try {
			operationRequestService.update(updateOperationRequest);
		} catch (Exception e) {
			logger.error("更新操作请求表异常", e);
			response.setAttachment(null);
			response.setCode(Constants.TppConstants.TRADE_STATE_FAILER.getCode());
			response.setMsg("操作超时，请重试");
			return response;
		}
		return response;
	}
	
	private void initBankCardAuthReqVo(OperationRequest source, BankCardAuthReqVo target){
		target.setBizSys(BizSys.get(source.getBizSysNo()));
		target.setBizType(BizType.get(source.getBizTypeNo()));
		target.setCardNo(source.getBankCardNo());
		target.setCertNo(source.getIdNo());
		target.setCertType(source.getIdType());
		target.setUserName(source.getRealName());
		target.setMobile(source.getMobile());
		target.setCardType(source.getBankCardType());
		target.setThirdType(ThirdType.SHUNIONPAY_ACCOUNT_AUTH);
		target.setInfoCategoryCode(source.getInfoCategoryCode());
		target.setAuthFlow(source.getTransferFlow());
	}
	
	private void initCardBinQueryReqVo(OperationRequest source, BankCardBinQueryReqVO target) {
		target.setBizSys(BizSys.get(source.getBizSysNo()));
		target.setBizType(BizType.get(source.getBizTypeNo()));
		target.setCardNo(source.getBankCardNo());
		target.setInfoCategoryCode(source.getInfoCategoryCode());
		target.setQueryFlow(source.getTransferFlow());
		target.setThirdType(ThirdType.SHUNIONPAY_ACCOUNT_AUTH);
	}
	
	private String cutMessage(String message) {
		if (null != message) {
			return StringUtils.substring(message, 0, 150);
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * 实时代付(融资)查询
	 * @param vo
	 * @return
	 */
	public Response queryPayChannel(PayInfo payInfo,QueryPayTask vo) {
		com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo channelVo = new com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo();
		
		// 待更新PayInfo
		PayInfo updatePayInfo = null;
		Response response = null;
		try {
			BeanUtils.copyProperties(vo, channelVo);
//			// 设置业务系统号
			channelVo.setBizSys(BizSys.get(vo.getBizSysNo()));
//			// 设置业务类型
			channelVo.setBizType(BizType.BROKER_PAY);
//			// 设置第三方通道编码
			channelVo.setThirdType(ThirdType.get(vo.getPaySysNo()));
//			// 设置信息类别编码
			channelVo.setInfoCategoryCode(vo.getInfoCategoryCode());
			response = channelServiceConsumer.queryCommond(channelVo);
			
			logger.info("channel resp[code:" + response.getCode() + " msg:" + response.getMsg() + ",flowId:" + response.getFlowId() + "]");
			
		} catch (Exception e) {
			logger.error("调用TPP通道异常", e);
		
			// 如果是异常，就认为是交易处理中(不确定交易)
			response = new Response(
					Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
					"调用TPP通道异常");
			return response;

		}
		
		// 如果代付明细结果 非中间态，则更新代付任务表交易状态
		try {

			if (!Constants.TppConstants.TRADE_STATE_MIDDLE.getCode().equals(response.getCode())) {
				updatePayInfo = new PayInfo();
				updatePayInfo.setTaskId(payInfo.getTaskId());
				// 设置交易流水号
				updatePayInfo.setTradeFlow(payInfo.getTradeFlow());
				// 设置交易状态
				updatePayInfo.setStatus(response.getCode());

				// 设置失败原因(取前面200个字符)
				updatePayInfo.setFailReason(response.getMsg() != null ? StringUtils.substring(response.getMsg(), 0, 200) : "");

				updatePayInfo.setTransRepCode(response.getBankRepCode());
				
				//金额
				updatePayInfo.setAmount(payInfo.getAmount());

				updatePayTask(updatePayInfo, response);
			}
		} catch (Exception e) {
			logger.error("trade处理异常", e);
			// 如果是异常，就认为是交易处理中(不确定交易)
			response = new Response(
					Constants.TppConstants.TRADE_STATE_MIDDLE.getCode(),
					Constants.TppConstants.TRADE_STATE_MIDDLE.getDesc());
			return response;
		}
		

		return response;

	}
	
}
