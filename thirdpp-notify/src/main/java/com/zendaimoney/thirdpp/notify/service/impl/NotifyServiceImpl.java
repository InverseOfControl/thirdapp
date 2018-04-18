package com.zendaimoney.thirdpp.notify.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.notify.conf.ServerConfig;
import com.zendaimoney.thirdpp.notify.dao.SysAppDao;
import com.zendaimoney.thirdpp.notify.dao.TradeNotifyDao;
import com.zendaimoney.thirdpp.notify.dao.WatingMergeDao;
import com.zendaimoney.thirdpp.notify.entity.NotifyMessage;
import com.zendaimoney.thirdpp.notify.entity.ReqMessage;
import com.zendaimoney.thirdpp.notify.entity.SysApp;
import com.zendaimoney.thirdpp.notify.entity.TradeNotify;
import com.zendaimoney.thirdpp.notify.service.INotifyService;
import com.zendaimoney.thirdpp.notify.util.Constants.TppConstants;
import com.zendaimoney.thirdpp.notify.util.HttpUtils;
import com.zendaimoney.thirdpp.notify.util.LogPrn;

/**
 * @author 00225642
 * 
 */
@Service
public class NotifyServiceImpl implements INotifyService {

	private static final LogPrn logger = new LogPrn(NotifyServiceImpl.class);

	private static final String BACKMSG = "success";

	@Autowired
	private TradeNotifyDao tradeNotifyDao;

	@Autowired
	private SysAppDao sysAppDao;

	@Autowired
	private WatingMergeDao watingMergeDao;

	@Override
	public List<TradeNotify> queryByAppName(String opMode) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appName", ServerConfig.systemConfig.getAppName());
		map.put("rowNum", ServerConfig.systemConfig.getNotifyQueryCount());
		map.put("notifyCount", ServerConfig.systemConfig.getNotifyNumber());
		map.put("opMode", opMode);
		return tradeNotifyDao.queryNotifysByAppName(map);
	}

	/**
	 * 回调请求分组
	 * 
	 * @param notifys
	 * @return
	 */
	public Map<String, List<NotifyMessage>> buildXml(List<TradeNotify> notifys) {
		// 不同业务系统分不同的通知报文,key-业务系统编码，value-通知报文
		Map<String, List<NotifyMessage>> map = new HashMap<String, List<NotifyMessage>>();
		for (TradeNotify notify : notifys) {
			List<NotifyMessage> notifyList = map.get(notify.getBizSysNo());
			if (null == notifyList) {
				notifyList = new ArrayList<NotifyMessage>();
			}
			NotifyMessage notifyMessage = convert(notify);
			notifyList.add(notifyMessage);
			map.put(notify.getBizSysNo(), notifyList);
		}
		return map;
	}
	
	/**
	 * 回调请求分组
	 * 
	 * @param notifys
	 * @return
	 */
	public Map<String, List<NotifyMessage>> buildXmlNew(List<TradeNotify> notifys) {
		// 不同业务系统分不同的通知报文,key-业务系统编码，value-通知报文
		Map<String, List<NotifyMessage>> map = new HashMap<String, List<NotifyMessage>>();
		for (TradeNotify notify : notifys) {
			List<NotifyMessage> notifyList = map.get(notify.getNotifyUrl());
			if (null == notifyList) {
				notifyList = new ArrayList<NotifyMessage>();
			}
			NotifyMessage notifyMessage = convert(notify);
			notifyList.add(notifyMessage);
			map.put(notify.getNotifyUrl(), notifyList);
		}
		return map;
	}

	@Override
	public void notifyBusiSys(List<TradeNotify> notifys) throws SQLException {
		Map<String, List<NotifyMessage>> map = buildXmlNew(notifys);
		// 读取业务系统通知url
		for (String key : map.keySet()) {
			
			if (key == null) {
				logger.info("AppCode:" + key + " not found notify URL");
				continue;
			}
			
			// 线下代扣通知URL
			String notifyUrl = key;
			List<NotifyMessage> notifyList = map.get(key);
			String xml = new ReqMessage(notifyList).toXml();
			String backMsg = "";
			// 通知
			try {
				logger.info("【通知业务系统信息】\n" + xml);
				backMsg = HttpUtils.URLPost(notifyUrl, xml).toString();
				logger.info("【业务系统回写信息】" + backMsg);
			} catch (IOException e) {
				logger.error("【回调超时】" + e.getMessage());
				updateNotifyStatus(key,
						TppConstants.NOTIFY_STATUS_WAIT.getCode(), notifyList);
				continue;
			}
			// 更新通知表
			if (BACKMSG.equalsIgnoreCase(backMsg)) {
				updateNotifyStatus(key,
						TppConstants.NOTIFY_STATUS_NOTIFYED.getCode(),
						notifyList);
			} else {
				updateNotifyStatus(key,
						TppConstants.NOTIFY_STATUS_WAIT.getCode(), notifyList);
			}
		}

	}

	private void updateNotifyStatus(String bizSysNo, String status,
			List<NotifyMessage> notifyList) {
		for (NotifyMessage msg : notifyList) {
			TradeNotify notify = new TradeNotify();
			notify.setId(msg.getRequestId());
			notify.setNotifyStatus(status);
			tradeNotifyDao.update(notify);
		}
	}

	/**
	 * TradeNotify对象转NotifyMessage
	 * 
	 * @param notify
	 * @return
	 */
	private NotifyMessage convert(TradeNotify notify) {
		NotifyMessage msg = new NotifyMessage();
		msg.setTaskId(notify.getTaskId());
		msg.setOrderNo(notify.getBizFlow());
		msg.setRequestId(notify.getId());
		msg.setTradeFlow(notify.getTradeFlow());
		msg.setReturnCode(notify.getTradeStatus());
		msg.setReturnInfo(notify.getTradeResultInfo());
		msg.setSuccessAmount(notify.getTradeSuccessAmount().toString());
		msg.setFailReason(notify.getFailReason());
		msg.setPaySysNo(notify.getPaySysNo());
		msg.setMerId(notify.getMerId());
		return msg;
	}

}
