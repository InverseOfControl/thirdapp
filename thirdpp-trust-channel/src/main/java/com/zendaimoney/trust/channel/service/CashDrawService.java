package com.zendaimoney.trust.channel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.trust.channel.dao.CashDrawDao;
import com.zendaimoney.trust.channel.entity.CashDraw;
import com.zendaimoney.trust.channel.mongo.UnKnowTradeService;
import com.zendaimoney.trust.channel.mongo.UnknowTradeVO;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.Constants;

@Service
@Transactional
public class CashDrawService {

	@Autowired
	private CashDrawDao cashDrawDao;
	
	@Autowired
	private UnKnowTradeService unKnowTradeService;
	
	/**
	 * 发送通知查询信息
	 * 
	 * @author mencius
	 * @date 2016-2-19 上午10:20:36 
	 * @since 0.1
	 * @see
	 */
	public void sendNotifyQueryMsg(CashDraw cashDraw, TrustBizReqVo trustBizReqVo) {

		
		if (cashDraw != null) {
			
			cashDrawDao.update(cashDraw);
		}
		// 发送mongo查询信息
		UnknowTradeVO unknowTradeVO = new UnknowTradeVO(
				trustBizReqVo.getTradeFlow(), trustBizReqVo.getBizType().getCode(), 
				trustBizReqVo.getBizSysNo(), trustBizReqVo.getThirdType().getCode(), cashDraw.getAmount()
						.toString(), trustBizReqVo.getInfoCategoryCode(), Constants.OP_MODE_TRUST, "", trustBizReqVo.getTrustBizType().getCode());
		unKnowTradeService.add(unknowTradeVO);

	}
}
