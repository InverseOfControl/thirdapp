package com.zendaimoney.thirdpp.trade.pub.service;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.QueryReqVo;
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
	
	/**
	 * 同步单笔接口-代付，vo里面list明细为一条
	 * @param vo
	 * @return
	 */
	public Response syncPay(RequestVo vo);
	
	/**
	 * 异步接口-代付  提交单笔批量功能
	 * @param vo
	 * @return
	 */
//	public Response asynPay(RequestVo vo); 
	
	/**
	 * 实时代付(融资)查询
	 * @param vo
	 * @return
	 */
	public Response queryPay(QueryReqVo vo);

	/**
	 * 协议支付签约短信触发
	 * @param requestVo
	 * @author wulj
	 * @return
	 */
	public Response syncSignMessage(RequestVo requestVo);

	/**
	 * 协议支付签约
	 * @param requestVo
	 * @author wulj
	 * @return
	 */
	public Response syncSign(RequestVo requestVo);
}
