package com.zendaimoney.thirdpp.trade.pub.service;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;

/**
 * 资金托管交易接口
 * @author 00231257
 *
 */
public interface ITrustTradeService {
	
	/**
	 * 同步单笔接口-还款，vo里面list明细为一条
	 * @param vo
	 * @return
	 */
	public Response syncRefund(RequestVo vo); 
	
	/**
	 * 异步接口-还款  提交单笔批量功能
	 * @param vo
	 * @return
	 */
	public Response asynRefund(RequestVo vo);

}
