package com.zendaimoney.trust.channel.proxy;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;

/**
 * 代理主题：资金托管交易
 * @author mencius
 *
 */
public interface TrustSubject {
	
	/**
	 * 代理主题执行器
	 * @param cmbBizReqVo
	 * @return
	 */
	public Response execute(TrustBizReqVo cmbBizReqVo);

}
