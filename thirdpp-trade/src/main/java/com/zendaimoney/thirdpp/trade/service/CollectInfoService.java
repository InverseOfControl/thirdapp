package com.zendaimoney.thirdpp.trade.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.trade.dao.CollectInfoDao;
import com.zendaimoney.thirdpp.trade.entity.CollectInfo;
import com.zendaimoney.thirdpp.trade.mongo.UnKnowTradeService;
import com.zendaimoney.thirdpp.trade.mongo.UnknowTradeVO;
import com.zendaimoney.thirdpp.trade.mq.MqProducter;
import com.zendaimoney.thirdpp.trade.mq.TaskMergeVo;
import com.zendaimoney.thirdpp.trade.util.ConfigUtil;
import com.zendaimoney.thirdpp.trade.util.Constants;

@Transactional
@Service
public class CollectInfoService {

	@Resource(name = "collectInfoDao")
	private CollectInfoDao collectInfoDao;

	
	@Autowired
	private UnKnowTradeService unKnowTradeService;
	
	@Autowired
	private MqProducter mqProducter;

	public void insert(CollectInfo collectInfo) {
		collectInfoDao.insert(collectInfo);
	}
	
	/**
	 * 
	 * @param collectInfo
	 */
	public void update(CollectInfo collectInfo){
		collectInfoDao.update(collectInfo);
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
					ConfigUtil.getInstance().getSystemConfig().getMerge_offline_key(), vo);
		}

	}
}
