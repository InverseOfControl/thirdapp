package com.zendaimoney.thirdpp.channel.pub.service;

import com.zendaimoney.thirdpp.channel.pub.vo.*;
import com.zendaimoney.thirdpp.common.vo.Response;

public interface IChannelService {
	
	/**
	 * 代收业务指令
	 * @return Response
	 */
	public Response collectCommond(CollectReqVo tradeRequestVo); 
	
	
	/**
	 * 查询业务指令
	 * @param queryReqVo
	 * @return
	 */
	public Response queryCommond(QueryReqVo queryReqVo); 
	
	/**
	 * 代付业务指令
	 * @return Response
	 */
	public Response payCommond(PayReqVo tradeRequestVo);

	/**
	 * 协议支付签约短信触发
	 * @param signMsgReqVo
	 * @return
	 */
	public Response signMessageCommond(SignMsgReqVo signMsgReqVo);

	/**
	 * 协议支付签约
	 * @param signReqVo
	 * @return
	 */
	public Response signCommond(SignReqVo signReqVo);
	
}
