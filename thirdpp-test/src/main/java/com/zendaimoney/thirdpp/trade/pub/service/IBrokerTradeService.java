package com.zendaimoney.thirdpp.trade.pub.service;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;

/**
 * 居间人交易接口
 * @author 00231257
 *
 */
public interface IBrokerTradeService {
	/**
	 * 同步单笔接口-代收，vo里面list明细为一条
	 * @param vo
	 * @return
	 */
	public Response syncCollect(RequestVo vo); 
	
	/**
	 * 异步接口-代收  提交单笔批量功能
	 * @param vo
	 * @return
	 */
	public Response asynCollect(RequestVo vo);
}
