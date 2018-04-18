package com.zendaimoney.trust.channel.pub.service;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;

/**
 * 对外暴露服务：资金托管
 * @author mencius
 *
 */
public interface TrustChannelService {
	
	/**
	 * 交易命令
	 * @param trustBizReqVo
	 * @return
	 */
	public Response tradeCommand(TrustBizReqVo trustBizReqVo);
	
	/**
	 * 查询命令
	 * @param trustBizReqVo
	 * @return
	 */
	public Response queryCommand(TrustBizReqVo trustBizReqVo);
	
	/**
	 * 批量命令
	 * @param trustBizReqVo
	 * @return
	 */
	public Response batchCommand(TrustBizReqVo trustBizReqVo);

}
