package com.zendaimoney.thirdpp.channel.pub.service;

import com.zendaimoney.thirdpp.channel.pub.vo.BankCardAuthReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardBinQueryReqVO;
import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 基础服务通道
 * @author 00231257
 *
 */
public interface IBaseChannelService {
	
	/**
	 * 银行卡实名认证指令
	 * @param bankCardAuthReqVo
	 * @return
	 */
	public Response bankCardAuthCommond(BankCardAuthReqVo bankCardAuthReqVo); 
	
	/**
	 * 卡bin查询
	 * @param binQueryReqVO
	 * @return
	 */
	public Response bankCardBinQueryCommond(BankCardBinQueryReqVO binQueryReqVO);

}
