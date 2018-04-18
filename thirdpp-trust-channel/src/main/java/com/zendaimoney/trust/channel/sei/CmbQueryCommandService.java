package com.zendaimoney.trust.channel.sei;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;

/**
 * 对外暴露：查询指令接口
 * @author mencius
 *
 */
public interface CmbQueryCommandService {

	/**
	 * 查询指令
	 * @param bizReqVo
	 * @return Response
	 */
	public Response queryCommand(TrustBizReqVo bizReqVo);
}
