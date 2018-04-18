package com.zendaimoney.thirdpp.channel.service;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.dao.CollectInfoDao;
import com.zendaimoney.thirdpp.channel.entity.CollectInfo;
import com.zendaimoney.thirdpp.channel.mongo.UnKnowTradeService;
import com.zendaimoney.thirdpp.channel.mongo.UnknowTradeVO;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.Constants;

@Service
@Transactional
public class CollectInfoService {

	@Resource(name = "collectInfoDao")
	private CollectInfoDao collectInfoDao;

	@Autowired
	private UnKnowTradeService unKnowTradeService;
	
	
	

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
	
	public Map<String,Object> getAgrmno(Map<String,Object> map) throws SQLException{
		return collectInfoDao.getAgrmno(map);
	}

}
