package com.zendaimoney.thirdpp.channel.sei;

import org.springframework.beans.factory.annotation.Autowired;

import com.zendaimoney.thirdpp.channel.action.DispatchAction;
import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.filter.ChannelRequestFilter;
import com.zendaimoney.thirdpp.channel.pub.service.IBaseChannelService;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardAuthReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardBinQueryReqVO;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardBinQueryRespVO;
import com.zendaimoney.thirdpp.channel.service.RequestService;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.ResponseUtil;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 银联实名认证
 * @author mencius
 *
 */
public class BaseChannelServiceImpl implements IBaseChannelService {

	private static final LogPrn logger = new LogPrn(BaseChannelServiceImpl.class);
	
	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ChannelRequestFilter channelRequestFilter;
	
	/**
	 * 实名认证
	 */
	@Override
	public Response bankCardAuthCommond(BankCardAuthReqVo bankCardAuthReqVo) {

		Response response = null;
		Request request = null;

		// 记录request-初始化状态
		try {
			
			// 进行通道过滤处理 
			response = channelRequestFilter.doFilter(bankCardAuthReqVo);
			// 如果过滤器中存在错误code，则返回中间未知状态
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				
				// 将响应CODE设置为交易异常
				response.setCode(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());
				
				return response;
			}
			// 初始化request
			request = new Request(bankCardAuthReqVo);
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			response = new Response(
					Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode(),
					PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return response;
		}
		response = dispatchAction.executeReqAction(bankCardAuthReqVo,
				ChannelCategory.AUTH);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		return response;
	}
	
	public void setDispatchAction(DispatchAction dispatchAction) {
		this.dispatchAction = dispatchAction;
	}

	public void setRequestService(RequestService requestService) {
		this.requestService = requestService;
	}
	
	public void setChannelRequestFilter(
			ChannelRequestFilter channelRequestFilter) {
		this.channelRequestFilter = channelRequestFilter;
	}

	/**
	 * 卡bin查询
	 */
	/* (non-Javadoc)
	 * @see com.zendaimoney.thirdpp.channel.pub.service.IBaseChannelService#bankCardBinQueryCommond(com.zendaimoney.thirdpp.channel.pub.vo.BankCardBinQueryReqVO)
	 */
	@Override
	public Response bankCardBinQueryCommond(BankCardBinQueryReqVO binQueryReqVO) {
		
		AttachmentResponse<BankCardBinQueryRespVO> attachmentResponse = new AttachmentResponse<BankCardBinQueryRespVO>();
		Response response = null;
		Request request = null;

		// 记录request-初始化状态
		try {
			
			// 进行通道过滤处理 
			response = channelRequestFilter.doFilter(binQueryReqVO);
			// 如果过滤器中存在错误code，则返回中间未知状态
			if (!Response.SUCCESS_RESPONSE_CODE.equals(response.getCode())) {
				
				// 将响应CODE设置为交易异常
				attachmentResponse.setCode(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());
				attachmentResponse.setMsg(response.getMsg());
				return attachmentResponse;
			}
			// 初始化request
			request = new Request(binQueryReqVO);
			requestService.insert(request);
		} catch (Exception e) {
			logger.error("===", e);
			// 返回交易异常-返回码:333333,必须返回该码
			
			attachmentResponse.setCode(Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode());
			attachmentResponse.setMsg(PlatformErrorCode.DB_ERROR.getDefaultMessage());
			return attachmentResponse;
		}
		response = dispatchAction.executeReqAction(binQueryReqVO,
				ChannelCategory.AUTH);

		// 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
		response = ResponseUtil.convert2UniqeErrorcode(response);

		// 返回对象为AttachmentResponse，方便TRADE层接收响应
		if (response instanceof AttachmentResponse) {
			attachmentResponse = (AttachmentResponse) response;
		} else {
			attachmentResponse.setCode(response.getCode());
			attachmentResponse.setBankRepCode(response.getBankRepCode());
			attachmentResponse.setFlowId(response.getFlowId());
			attachmentResponse.setMsg(response.getMsg());
		}
		
		logger.info("code:" + attachmentResponse.getCode() + ", msg:"
				+ attachmentResponse.getMsg() + ", flow:" + attachmentResponse.getFlowId() + ", bankretCode:"+ attachmentResponse.getBankRepCode() + ",attachment:" + attachmentResponse.getAttachment());
		return attachmentResponse;
	}

}
