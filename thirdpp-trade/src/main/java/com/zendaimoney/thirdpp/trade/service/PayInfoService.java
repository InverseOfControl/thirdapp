package com.zendaimoney.thirdpp.trade.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.pub.vo.PayReqVo;
import com.zendaimoney.thirdpp.trade.dao.PayInfoDao;
import com.zendaimoney.thirdpp.trade.entity.CollectInfo;
import com.zendaimoney.thirdpp.trade.entity.PayInfo;
import com.zendaimoney.thirdpp.trade.mongo.UnKnowTradeService;
import com.zendaimoney.thirdpp.trade.mongo.UnknowTradeVO;
import com.zendaimoney.thirdpp.trade.mq.MqProducter;
import com.zendaimoney.thirdpp.trade.mq.TaskMergeVo;
import com.zendaimoney.thirdpp.trade.util.ConfigUtil;
import com.zendaimoney.thirdpp.trade.util.Constants;

@Transactional
@Service
public class PayInfoService {
	@Resource(name = "payInfoDao")
	private PayInfoDao payInfoDao;

	
	@Autowired
	private UnKnowTradeService unKnowTradeService;
	
	@Autowired
	private MqProducter mqProducter;

	
	public void insert(PayInfo payInfo) {
		payInfoDao.insert(payInfo);
	}
	
	/**
	 * 
	 * @param payInfo
	 */
	public void update(PayInfo payInfo){
		payInfoDao.update(payInfo);
	}

	
	/**
	 * 发送通知合并信息
	 * 
	 * @author liuhongyi
	 * @date 2015-9-2 上午11:47:32 
	 * @since 0.1
	 * @see
	 */
	public void sendNotifyMergeMsg(PayInfo payInfo) {
//		PayInfo updatePayInfo = new PayInfo();
//		updatePayInfo.setTradeFlow(payInfo.getTradeFlow());
//		updatePayInfo
//				.setNotifyMergeStatus(PayInfo.NOTIFY_MERGE_STATUS_YES);
		
		payInfoDao.update(payInfo);

//		if (payInfo != null) {
//			TaskMergeVo vo = new TaskMergeVo(payInfo.getTradeFlow(),
//					payInfo.getBizType());
//			mqProducter.sendMessage(
//					ConfigUtil.getInstance().getSystemConfig().getMerge_offline_key(), vo);
//		}

	}
	
	/**
	 * 发送通知查询信息
	 * 
	 * @author liuhongyi
	 * @date 2015-9-2 上午11:49:20 
	 * @since 0.1
	 * @see
	 */
	public void sendNotifyQueryMsg(PayReqVo payReqVo, String failReason) {
		// 修改通知查询状态-1不需要通知查询
		PayInfo updatePayInfo = new PayInfo();
		updatePayInfo.setTradeFlow(payReqVo.getTradeFlow());
//		updatePayInfo.setNotifyQueryStatus(PayInfo.NOTIFY_QUERY_STATUS_NO);
		updatePayInfo.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE.getCode());
		updatePayInfo.setFailReason(failReason);
		
		payInfoDao.update(updatePayInfo);
//		// 发送mongo查询信息
//		UnknowTradeVO unknowTradeVO = new UnknowTradeVO(
//				payReqVo.getTradeFlow(), payReqVo.getBizType()
//						.getCode(), payReqVo.getBizSys().getCode(),
//						payReqVo.getThirdType().getCode(), payReqVo.getAmount()
//						.toString(), payReqVo.getInfoCategoryCode(), Constants.OP_MODE_OFFLINE,payReqVo.getPayerAccountNo());
//		unKnowTradeService.add(unknowTradeVO);
	}
}
