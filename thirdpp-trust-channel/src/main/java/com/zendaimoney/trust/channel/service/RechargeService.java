package com.zendaimoney.trust.channel.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.trust.channel.dao.RechargeDao;
import com.zendaimoney.trust.channel.entity.Recharge;
import com.zendaimoney.trust.channel.mongo.UnKnowTradeService;
import com.zendaimoney.trust.channel.mongo.UnknowTradeVO;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 资金托管充值
 * @author user
 *
 */
@Service
@Transactional
public class RechargeService {

	@Resource(name = "rechargeDao")
	private RechargeDao rechargeDao;

	@Autowired
	private UnKnowTradeService unKnowTradeService;
	
	/**
	 * Recharge入库操作
	 * 
	 * @param collectInfo
	 */
	public void insert(Recharge recharge) {
		rechargeDao.insert(recharge);
	}
	
	/**
	 * 发送通知查询信息
	 * 
	 * @author mencius
	 * @date 2016-2-19 上午10:20:36 
	 * @since 0.1
	 * @see
	 */
	public void sendNotifyQueryMsg(Recharge recharge, TrustBizReqVo trustBizReqVo) {

		// 发送mongo查询信息
		UnknowTradeVO unknowTradeVO = new UnknowTradeVO(
				trustBizReqVo.getTradeFlow(), trustBizReqVo.getBizType().getCode(), 
				trustBizReqVo.getBizSysNo(), trustBizReqVo.getThirdType().getCode(), recharge.getAmount()
						.toString(), trustBizReqVo.getInfoCategoryCode(), Constants.OP_MODE_TRUST, "", trustBizReqVo.getTrustBizType().getCode());
		unKnowTradeService.add(unknowTradeVO);

	}

}
