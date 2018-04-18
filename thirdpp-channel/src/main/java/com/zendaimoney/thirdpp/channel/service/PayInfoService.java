package com.zendaimoney.thirdpp.channel.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.dao.CollectInfoDao;
import com.zendaimoney.thirdpp.channel.dao.PayInfoDao;
import com.zendaimoney.thirdpp.channel.entity.CollectInfo;
import com.zendaimoney.thirdpp.channel.entity.PayInfo;
import com.zendaimoney.thirdpp.channel.mongo.UnKnowTradeService;
import com.zendaimoney.thirdpp.channel.mongo.UnknowTradeVO;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.PayReqVo;
import com.zendaimoney.thirdpp.channel.util.Constants;

@Service
@Transactional
public class PayInfoService {

	@Resource(name = "payInfoDao")
	private PayInfoDao payInfoDao;

	@Autowired
	private UnKnowTradeService unKnowTradeService;
	
	
	

	/**
	 * payInfo入库操作
	 * 
	 * @param payInfo
	 */
	public void insert(PayInfo payInfo) {
		payInfoDao.insert(payInfo);
	}

	/**
	 * payInfo修改操作
	 * 
	 * @param payInfo
	 */
	public int update(PayInfo payInfo) {
		return payInfoDao.update(payInfo);
	}

	/**
	 * 发送通知查询信息
	 * 
	 * @author liuhongyi
	 * @date 2015-9-8 下午15:49:36 
	 * @since 0.1
	 * @see
	 */
	public void sendNotifyQueryMsg(PayReqVo payReqVo) {

		// 修改通知查询状态-2已通知查询
		PayInfo updatePayInfo = new PayInfo();
		updatePayInfo.setTradeFlow(payReqVo.getTradeFlow());
		updatePayInfo
				.setNotifyQueryStatus(PayInfo.NOTIFY_QUERY_STATUS_YES);
		payInfoDao.update(updatePayInfo);
		// 发送mongo查询信息
		UnknowTradeVO unknowTradeVO = new UnknowTradeVO(
				payReqVo.getTradeFlow(), payReqVo.getBizType().getCode(), payReqVo.getBizSys().getCode(),
				payReqVo.getThirdType().getCode(), payReqVo.getAmount()
						.toString(), payReqVo.getInfoCategoryCode(), Constants.OP_MODE_OFFLINE, payReqVo.getPayerAccountNo());
		unKnowTradeService.add(unknowTradeVO);

	}
}
