package com.zendaimoney.thirdpp.transfer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.transfer.conf.ServerConfig;
import com.zendaimoney.thirdpp.transfer.dao.CollectInfoDao;
import com.zendaimoney.thirdpp.transfer.dao.CollectTaskDao;
import com.zendaimoney.thirdpp.transfer.entity.collect.CollectInfo;
import com.zendaimoney.thirdpp.transfer.entity.collect.CollectTask;
import com.zendaimoney.thirdpp.transfer.mongo.UnKnowTradeService;
import com.zendaimoney.thirdpp.transfer.mongo.UnknowTradeVO;
import com.zendaimoney.thirdpp.transfer.mq.MqProducter;
import com.zendaimoney.thirdpp.transfer.mq.TaskMergeVo;
import com.zendaimoney.thirdpp.transfer.util.Constants;

@Service
@Transactional
public class CollectInfoService {

	@Resource(name = "collectInfoDao")
	private CollectInfoDao collectInfoDao;

	@Resource(name = "collectTaskDao")
	private CollectTaskDao collectTaskDao;

	@Autowired
	private UnKnowTradeService unKnowTradeService;

	@Autowired
	private MqProducter mqProducter;

	/**
	 * collectInfo入库操作
	 * 
	 * @param collectInfo
	 */
	public void insert(CollectInfo collectInfo) {
		collectInfoDao.insert(collectInfo);
	}

	/**
	 * collectInfo修改操作
	 * 
	 * @param collectInfo
	 */
	public int update(CollectInfo collectInfo) {
		return collectInfoDao.update(collectInfo);
	}

	/**
	 * 待收任务转发(转发分成两步：任务更新、交易信息入库)
	 * 
	 * @param task
	 * @param tradeInfos
	 *            task拆分交易记录集
	 */
	public void transfer(CollectTask task, List<CollectInfo> tradeInfos) {
		if (task != null) {
			collectTaskDao.update(task);
		}
		// task拆分交易记录集
		if (tradeInfos != null && tradeInfos.size() > 0) {
			for (int i = 0; i < tradeInfos.size(); i++) {
				collectInfoDao.insert(tradeInfos.get(i));
			}
		}

	}

	/**
	 * 发送通知查询信息
	 * 
	 * @author liuyi
	 * @date 2015-7-12 上午10:20:36 
	 * @since 0.1
	 * @see
	 */
	public void sendNotifyQueryMsg(CollectReqVo collectReqVo) {

		// 修改通知查询状态-2已通知查询
		CollectInfo updateCollectInfo = new CollectInfo();
		updateCollectInfo.setTradeFlow(collectReqVo.getTradeFlow());
		updateCollectInfo
				.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_YES);
		collectInfoDao.update(updateCollectInfo);
		// 发送mongo查询信息
		UnknowTradeVO unknowTradeVO = new UnknowTradeVO(
				collectReqVo.getTradeFlow(), collectReqVo.getBizType()
						.getCode(), collectReqVo.getBizSys().getCode(),
				collectReqVo.getThirdType().getCode(), collectReqVo.getAmount()
						.toString(), collectReqVo.getInfoCategoryCode(), Constants.OP_MODE_OFFLINE, collectReqVo.getPayerAccountNo());
		unKnowTradeService.add(unknowTradeVO);

	}

	/**
	 * 发送通知合并信息
	 * 
	 * @author liuyi
	 * @date 2015-7-12 上午10:20:36 
	 * @since 0.1
	 * @see
	 */
	public void sendNotifyMergeMsg(CollectInfo collectInfo) {

		CollectInfo updateCollectInfo = new CollectInfo();
		updateCollectInfo.setTradeFlow(collectInfo.getTradeFlow());
		updateCollectInfo
				.setNotifyMergeStatus(CollectInfo.NOTIFY_MERGE_STATUS_YES);
		collectInfoDao.update(updateCollectInfo);

		if (collectInfo != null) {
			TaskMergeVo vo = new TaskMergeVo(collectInfo.getTradeFlow(),
					collectInfo.getBizTypeNo());
			mqProducter.sendMessage(
					ServerConfig.systemConfig.getMerge_offline_key(), vo);
		}

	}

}
