package com.zendaimoney.thirdpp.notify.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zendaimoney.thirdpp.notify.action.DispatchAction;
import com.zendaimoney.thirdpp.notify.conf.ServerConfig;
import com.zendaimoney.thirdpp.notify.conf.SystemConfig;
import com.zendaimoney.thirdpp.notify.dao.TradeNotifyDao;
import com.zendaimoney.thirdpp.notify.dao.WatingMergeDao;
import com.zendaimoney.thirdpp.notify.entity.MqMergeVo;
import com.zendaimoney.thirdpp.notify.entity.MqMessage;
import com.zendaimoney.thirdpp.notify.entity.TotalOrder;
import com.zendaimoney.thirdpp.notify.entity.TradeNotify;
import com.zendaimoney.thirdpp.notify.entity.WaitingMergeEntity;
import com.zendaimoney.thirdpp.notify.service.IMergeService;
import com.zendaimoney.thirdpp.notify.util.Constants;
import com.zendaimoney.thirdpp.notify.util.Constants.TppConstants;
import com.zendaimoney.thirdpp.notify.util.JackJsonUtil;
import com.zendaimoney.thirdpp.notify.util.LogPrn;

/**
 * @author 00225642
 * 
 */
@Service
public class MergeServiceImpl implements IMergeService {

	private static final LogPrn logger = new LogPrn(MergeServiceImpl.class);

	@Autowired
	private DispatchAction dispatchAction;

	@Autowired
	private TradeNotifyDao tradeNotifyDao;

	@Autowired
	private WatingMergeDao watingMergeDao;

	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * 合单routingKey 线下
	 */
	private static String MERGE_ROUTING_KEY = ServerConfig.systemConfig.getMerge_offline_key();

	@Override
	public TotalOrder merge(MqMessage msg) throws SQLException,
			DataAccessException {

		String tradeFlow = msg.getTradeFlow();
		String bizType = msg.getBizType();
		if (StringUtils.isEmpty(tradeFlow) || StringUtils.isEmpty(bizType)) {
			logger.info("【tradeFlow或bizType为空】" + msg.toString());
			return null;
		}
		Long taskId = dispatchAction.executeQueryTaskId(bizType, tradeFlow);
		if (StringUtils.isEmpty(taskId)) {
			logger.info("【该流水对应的task无需进行合单】" + tradeFlow);
			return null;
		}
		// 统计taskId对应所有明细订单
		TotalOrder order = dispatchAction.executeQueryAction(bizType, taskId);
		if (null == order) {
			logger.info("【该流水对应的task无需进行合单】" + taskId + ",【流水号】" + tradeFlow);
			return null;
		}
		if (!isCanMerge(order)) {
			logger.info("【taskId对应的订单数据不满足合单要求】" + taskId);
			return null;
		}
		return order;
	}

	/**
	 * 更新任务表并且插入通知信息表
	 * 
	 * @param bizType
	 * @param order
	 * @throws SQLException
	 */
	@Override
	@Transactional(rollbackFor = { DataAccessException.class,
			SQLException.class })
	public void updateTaskAndSaveNotify(TotalOrder order)
			throws DataAccessException, SQLException {
		int updateNum;
		updateNum = dispatchAction.executeUpdateAction(order.getBizType(),
				order);
		if (updateNum <= 0) {
			return;
		}
		
		// 入推送订单表
		if (Constants.TppConstants.PUSH_NEED.getCode().equals(
				String.valueOf(order.getIsNeedPush()))) {
			tradeNotifyDao.insert(convertOrderToNotify(order, Constants.OP_MODE_OFFLINE));
		}
		logger.debug("【合单完成】" + order.getTaskId());
	}

	/**
	 * 判断是否满足合并的条件
	 * 
	 * @param order
	 * @return
	 */
	private boolean isCanMerge(TotalOrder order) {
		boolean mergeflag = false;
		if (order.getTradingCount() > 0) {
			// 有交易中的订单,不进行合单推送
			mergeflag = false;
		} else if (order.getSeparateCount() != order.getTotalCount()) {
			// 拆单数和汇总数不等，可能存在恶意删除或添加明细
			logger.info("【问题订单:任务拆单数与实际明细汇总数不一致,不允许合单.taskId】"
					+ order.getTaskId());
			mergeflag = false;
		} else if (0 != order.getOrderAmount()
				.compareTo(order.getTotalAmount())) {
			// 订单金额和实际汇总金额不等，可能存在恶意修改数据
			logger.info("【问题订单:订单金额和实际汇总金额不等,不允许合单.taskId】" + order.getTaskId());
			mergeflag = false;
		} else if (order.getTotalCount() == order.getSuccessCount()) {
			// 全部成功
			order.setNotifySuccessAmt(order.getTotalAmount());
			order.setNotifyStatus(TppConstants.TRADE_STATE_SUCCESS.getCode());
			order.setNotifyDesc(TppConstants.TRADE_STATE_SUCCESS.getDesc());
			mergeflag = true;
		} else if (order.getTotalCount() == order.getFailCount()) {
			// 全部失败
			order.setNotifySuccessAmt(order.getSuccessAmount());
			order.setNotifyStatus(TppConstants.TRADE_STATE_FAILER.getCode());
			order.setNotifyDesc(TppConstants.TRADE_STATE_FAILER.getDesc());
			mergeflag = true;
		} else {
			// 部分成功，部分失败
			order.setNotifySuccessAmt(order.getSuccessAmount());
			order.setNotifyStatus(TppConstants.TRADE_STATE_PARTY_SUCCESS
					.getCode());
			order.setNotifyDesc(TppConstants.TRADE_STATE_PARTY_SUCCESS
					.getDesc());
			mergeflag = true;
		}
		return mergeflag;
	}

	/**
	 * 封装通知对象
	 * 
	 * @param totalOrder
	 * @return
	 * @throws SQLException
	 */
	private TradeNotify convertOrderToNotify(TotalOrder totalOrder)
			throws SQLException {
		TradeNotify notify = new TradeNotify();
		notify.setBizFlow(totalOrder.getBizFlow());
		notify.setBizSysNo(totalOrder.getBizSysNo());
		notify.setNotifyStatus(TppConstants.NOTIFY_STATUS_WAIT.getCode());
		notify.setTaskId(totalOrder.getTaskId());
		notify.setTradeFlow(totalOrder.getTradeFlow());
		notify.setTradeResultInfo(totalOrder.getNotifyDesc());
		notify.setTradeStatus(totalOrder.getNotifyStatus());
		notify.setTradeSuccessAmount(totalOrder.getNotifySuccessAmt());
		notify.setAppName(ServerConfig.systemConfig.getAppName());
		notify.setBizType(totalOrder.getBizType());
		notify.setNotifyCount(0);
		String failReason = dispatchAction.executeQueryFailReason(
				totalOrder.getBizType(), totalOrder.getTaskId());
		notify.setFailReason(failReason);
		return notify;
	}
	
	/**
	 * 封装通知对象
	 * 
	 * @param totalOrder
	 * @return
	 * @throws SQLException
	 */
	private TradeNotify convertOrderToNotify(TotalOrder totalOrder, String opMode)
			throws SQLException {
		TradeNotify notify = new TradeNotify();
		notify.setBizFlow(totalOrder.getBizFlow());
		notify.setBizSysNo(totalOrder.getBizSysNo());
		notify.setNotifyStatus(TppConstants.NOTIFY_STATUS_WAIT.getCode());
		notify.setTaskId(totalOrder.getTaskId());
		notify.setTradeFlow(totalOrder.getTradeFlow());
		notify.setTradeResultInfo(totalOrder.getNotifyDesc());
		notify.setTradeStatus(totalOrder.getNotifyStatus());
		notify.setTradeSuccessAmount(totalOrder.getNotifySuccessAmt());
		notify.setAppName(ServerConfig.systemConfig.getAppName());
		notify.setBizType(totalOrder.getBizType());
		notify.setNotifyCount(0);
		String failReason = dispatchAction.executeQueryFailReason(
				totalOrder.getBizType(), totalOrder.getTaskId());
		notify.setFailReason(failReason);
		notify.setPriority(totalOrder.getPriority()); // 优先级(3最高 2高 1中 0普通)
		notify.setOpMode(opMode); // 运营方式(0线下运营1线上运营)
		notify.setPaySysNo(totalOrder.getPaySysNo());
		notify.setMerId(totalOrder.getMerId());
		
		notify.setNotifyUrl(totalOrder.getNotifyUrl()); // 通知业务端URL
		return notify;
	}

	@Override
	public void putMergeMq(String sendMessage) {
		amqpTemplate.convertAndSend(MERGE_ROUTING_KEY, sendMessage);
	}

	@Override
	public void doWaitingMerge() throws Exception {
		List<WaitingMergeEntity> list = queryWaitingResults();
		logger.info("notifyWaitingResults:" + list.size());
		for (WaitingMergeEntity entity : list) {
			MqMergeVo vo = new MqMergeVo(entity.getTradeFlow(),
					entity.getBizTypeNo());
			String msg = JackJsonUtil.objToStr(vo);
			// 放入合单队列
			putMergeMq(msg);
			// 更新
			updateWaitingMerge(entity);
		}
	}

	private void updateWaitingMerge(WaitingMergeEntity entity) {
		WaitingMergeEntity waiting = new WaitingMergeEntity();
		waiting.setId(entity.getId());
		waiting.setStatus(Constants.TppConstants.WAITING_DONE.getCode());
		watingMergeDao.update(waiting);
	}

	private List<WaitingMergeEntity> queryWaitingResults() throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Constants.TppConstants.WAITING_TODO.getCode());
		map.put("mergeModuleName", ServerConfig.systemConfig.getAppName());
		map.put("rowNum", ServerConfig.systemConfig.getWaitingQueryCount());
		List<WaitingMergeEntity> list = watingMergeDao.queryWaitingResults(map);
		return list;
	}
}
