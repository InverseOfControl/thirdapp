package com.zendaimoney.thirdpp.channel.service;

import javax.annotation.Resource;

import com.zendaimoney.thirdpp.channel.dao.*;
import com.zendaimoney.thirdpp.channel.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.mq.MqProducter;
import com.zendaimoney.thirdpp.channel.mq.TaskMergeVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;

@Service
@Transactional
public class TradeResultService {

	@Resource(name = "tradeResultDao")
	private TradeResultDao tradeResultDao;

	@Resource(name = "collectInfoDao")
	private CollectInfoDao collectInfoDao;
	
	@Resource(name = "payInfoDao")
	private PayInfoDao payInfoDao;

	@Resource(name = "signMessageInfoDao")
	private SignMessageInfoDao signMessageInfoDao;

	@Resource(name = "signInfoDao")
	private SignInfoDao signInfoDao;

	@Autowired
	private MqProducter mqProducter;

	/**
	 * 记录请求操作
	 * 
	 * @param tradeResult
	 */
	public void insert(TradeResult tradeResult) {
		tradeResultDao.insert(tradeResult);
	}

	/**
	 * 通道返回最终结果处理
	 * 
	 * @param tradeResult
	 * @param collectInfo
	 */
	public void finalResultHandler(TradeResult tradeResult,
			CollectInfo collectInfo) {

		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		// 修改交易最终状态
		if (collectInfo != null) {
			collectInfoDao.update(collectInfo);
		}

		if (tradeResult != null) {
			TaskMergeVo vo = new TaskMergeVo(tradeResult.getTransferFlow(),
					tradeResult.getBizType());
			mqProducter.sendMessage(ConfigUtil.getInstance().getSystemConfig()
					.getMerge_offline_key(), vo);
		}
	}

	public void finalResultHandler(TradeResult tradeResult, PayInfo payInfo) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		// 修改交易最终状态
		if (payInfo != null) {
			payInfoDao.update(payInfo);
		}

//		if (tradeResult != null) {
//			TaskMergeVo vo = new TaskMergeVo(tradeResult.getTransferFlow(),
//					tradeResult.getBizType());
//			mqProducter.sendMessage(ConfigUtil.getInstance().getSystemConfig()
//					.getMerge_offline_key(), vo);
//		}
	}


	/**
	 * 交易最终结果处理
	 * @author wulj
	 * @param tradeResult
	 * @param signMessageInfo
	 */
	public void finalResultHandler(TradeResult tradeResult, SignMessageInfo signMessageInfo){
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		// 修改交易最终状态
		if (signMessageInfo != null) {
			signMessageInfoDao.update(signMessageInfo);
		}
	}

	/**
	 * 交易最终结果处理
	 * @author wulj
	 * @param tradeResult
	 * @param signInfo
	 */
	public void finalResultHandler(TradeResult tradeResult, SignInfo signInfo){
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
		// 修改交易最终状态
		if (signInfo != null) {
			signInfoDao.update(signInfo);
		}
	}


	public void finalResultHandler(TradeResult tradeResult) {
		// 记录返回结果
		if (tradeResult != null) {
			tradeResultDao.insert(tradeResult);
		}
	}

}
