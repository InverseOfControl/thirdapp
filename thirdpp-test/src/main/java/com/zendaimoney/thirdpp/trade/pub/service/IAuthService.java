package com.zendaimoney.thirdpp.trade.pub.service;

import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BankCardAuthReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BankCardBinQueryReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.resp.query.CardBinResponseVo;

/**
 * 实名认证接口
 * 
 * @author 00231257
 *
 */
public interface IAuthService {

	/**
	 * 银行卡认证
	 * @param BankCardAuthReqVo vo 
	 * @return
	 */
	public Response bankCardAuth(BankCardAuthReqVo vo);
	
	
	/**
	 * 银行卡卡bin查询
	 * @param BankCardBinQueryReqVo vo 
	 * @return
	 */
	public AttachmentResponse<CardBinResponseVo> bankCardBinQuery(BankCardBinQueryReqVo vo);
}
