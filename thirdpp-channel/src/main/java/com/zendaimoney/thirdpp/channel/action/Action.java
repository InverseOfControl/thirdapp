package com.zendaimoney.thirdpp.channel.action;

import com.zendaimoney.thirdpp.channel.communication.Message;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.common.vo.Response;

public abstract class Action {
	
	private static final LogPrn logger = new LogPrn(Action.class);

	/** 发送前业务处理 */
	protected abstract ReqDto preProcess(BizReqVo vo) throws PlatformException;

	/**
	 * 请求报文构建.
	 * 
	 * @param dto
	 * @return
	 */
	protected abstract Message buildMessage(ReqDto dto,BizReqVo vo) throws PlatformException;

	/** 业务核心处理 */
	protected abstract Message process(BizReqVo vo, ReqDto dto) throws PlatformException;

	/** 返回处理 */
	protected abstract Response respHandler(Message message,BizReqVo bizReqVo) throws PlatformException;

	/**
	 * 返回报文解析.
	 * 
	 * @param message 	xml报文
	 * @param bizReqVo	业务VO
	 * @return
	 */
	protected abstract RespDto parseMessage(Message message,BizReqVo bizReqVo) throws PlatformException;
	
	/**
	 * Http请求失败处理(http返回状态码不为200)(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param message
	 * @return
	 */
	protected abstract Response communicationFailHandler(Message message,BizReqVo bizReqVo) ;

	/**
	 *  Http请求成功处理(http返回状态码为200)(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param dto
	 * @return
	 * @throws PlatformException
	 */
	protected abstract Response communicationSuccessHandler(RespDto dto,Message message, BizReqVo bizReqVo);
	
	/**
	 * 响应报文解析失败处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param message
	 * @return
	 */
	protected abstract Response parseMessageFailHandler(Message message, BizReqVo bizReqVo);

	/**
	 * communication提交抛出异常处理(此方法不能向外抛出异常，方法内部发生异常必须在方法内部捕获并以repsonse返回)
	 * @param message
	 * @return
	 */
	protected abstract Response communicationThrowExceptionHandler(Message message, BizReqVo bizReqVo);

	/** 报文执行处理入口 */
	public Response execute(BizReqVo bizReqVo) {
		Response response = null;
		Message m = null;
		try {
			m = process(bizReqVo, preProcess(bizReqVo));
			response = respHandler(m,bizReqVo);
		} catch (PlatformException e) {
			response = new Response(e.getRealCode(), e.getMessage());
			//设置操作流水号
			response.setFlowId(bizReqVo.getChannelReqId());
			return response;
		} catch (Exception e) {
			logger.error("====", e);
			response = new Response(PlatformErrorCode.UNKNOWN_ERROR.getErrorCode(), ExceptionUtil.getExceptionMessage(e));
			//设置操作流水号
			response.setFlowId(bizReqVo.getChannelReqId());
			return response;
		}
		return response;
	}

}
