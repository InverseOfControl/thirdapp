package com.zendaimoney.thirdpp.route.transfer.service;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.route.transfer.conf.ServerConfig;
import com.zendaimoney.thirdpp.route.transfer.dao.CollectInfoDao;
import com.zendaimoney.thirdpp.route.transfer.entity.CollectInfo;
import com.zendaimoney.thirdpp.route.transfer.mq.MqProducter;
import com.zendaimoney.thirdpp.route.transfer.mq.TaskMergeVo;


@Service
@Transactional
public class CollectInfoService {

	@Resource(name = "collectInfoDao")
	private CollectInfoDao collectInfoDao;

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
        updateCollectInfo.setNotifyMergeStatus(CollectInfo.NOTIFY_MERGE_STATUS_YES);
        collectInfoDao.update(updateCollectInfo);

        if (collectInfo != null) {
            TaskMergeVo vo = new TaskMergeVo(collectInfo.getTradeFlow(), collectInfo.getBizTypeNo());
            mqProducter.sendMessage(ServerConfig.systemConfig.getMerge_offline_key(), vo);
        }

	}
	
}
