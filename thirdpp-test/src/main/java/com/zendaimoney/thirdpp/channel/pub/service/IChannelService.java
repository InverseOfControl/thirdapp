package com.zendaimoney.thirdpp.channel.pub.service;

import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.common.vo.Response;

public interface IChannelService {
	
	/**
	 * 代收业务指令
	 * @return Response
	 */
	public Response collectCommond(CollectReqVo tradeRequestVo); 

}
