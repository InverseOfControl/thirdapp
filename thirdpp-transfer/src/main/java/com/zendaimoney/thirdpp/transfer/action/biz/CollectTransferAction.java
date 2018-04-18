package com.zendaimoney.thirdpp.transfer.action.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ymkj.base.cache.redis.client.ICacheClient;
import com.zendaimoney.thirdpp.channel.pub.service.IChannelService;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.transfer.action.Action;
import com.zendaimoney.thirdpp.transfer.annotations.TransferActionAnnotation;
import com.zendaimoney.thirdpp.transfer.conf.ServerConfig;
import com.zendaimoney.thirdpp.transfer.entity.Response;
import com.zendaimoney.thirdpp.transfer.entity.collect.CollectInfo;
import com.zendaimoney.thirdpp.transfer.entity.collect.CollectTask;
import com.zendaimoney.thirdpp.transfer.exception.PlatformException;
import com.zendaimoney.thirdpp.transfer.service.CollectInfoService;
import com.zendaimoney.thirdpp.transfer.service.CollectTaskService;
import com.zendaimoney.thirdpp.transfer.util.CacheUtil;
import com.zendaimoney.thirdpp.transfer.util.CalendarUtils;
import com.zendaimoney.thirdpp.transfer.util.Constants;
import com.zendaimoney.thirdpp.transfer.util.IDGenerateUtil;
import com.zendaimoney.thirdpp.transfer.util.LogPrn;
import com.zendaimoney.thirdpp.transfer.util.ThirdPPCacheContainer;

/**
 * 代收转发action
 * 
 * @author 00231257
 * 
 */
@TransferActionAnnotation(bizType = BizType.BROKER_COLLECT)
@Component("com.zendaimoney.thirdpp.transfer.action.biz.CollectTransferAction")
public class CollectTransferAction extends Action<CollectTask, CollectInfo> {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(CollectTransferAction.class);

	@Autowired
	private CollectTaskService collectTaskService;

	@Autowired
	private CollectInfoService collectInfoService;

	@Autowired
	private IChannelService channelService;
	
	@Autowired
	private ICacheClient cacheClient;

	/**
	 * 业务操作前操作
	 * 
	 * @param thirdType
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	@Override
	protected CollectTask preProcess(String thirdType, String name)
			throws PlatformException {
		// 组装查询参数.
		HashMap<String, Object> param = new HashMap<String, Object>();
		// 发送通道
		param.put("paySysNo", thirdType);
		// 发送程序名称
		param.put("sendThreadName", name);
		// 最大发送次数
		param.put("maxSendNum", ServerConfig.systemConfig.getMaxSendNum());

		// 业务类型
		param.put("bizTypeNo", ServerConfig.systemConfig.getBizType().getCode());
		CollectTask task = null;
		CollectTask updateTask = null;
		// 锁定数
		int lockNum = 0;
		try {
			// 获取待发送任务
			task = collectTaskService.queryWaitingSendTask(param);
			// 如果没有待发送数据
			if (task == null) {
				return null;
			}
			// 锁定该任务
			updateTask = new CollectTask();
			updateTask.setId(task.getId());
			// 设置状态-发送中
			updateTask.setStatus(Integer
					.valueOf(Constants.TppConstants.SEND_STATUS_SENDING
							.getCode()));
			updateTask.setSendThreadName(name);
			// 发送数量可以是任意值，只要不为空就行
			updateTask.setSendNum(0);
			updateTask.setOldStatus(task.getStatus());
			lockNum = collectTaskService.update(updateTask);

			// 如果没有锁定任务，则返回选中对象
			if (lockNum <= 0) {
				return null;
			}

		} catch (Exception e) {
			logger.error("获取任务或者锁定任务异常", e);
			throw new PlatformException("获取任务或者锁定任务异常", e);

		}
		return task;
	}

	/**
	 * 核心处理
	 */
	@Override
	protected Response process(CollectTask task) throws PlatformException {
		// 返回对象
		Response response = new Response(false, task);
		// 如果待发送任务为空，则立即返回response
		if (task == null) {
			response = new Response(true, null);
			return response;
			// 如果待发送任务不为空，则进行转发操作
		} else {
			// 转发操作
			transfer(task);
		}
		return response;
	}

	/**
	 * 将task数据转移到交易明细表
	 * 
	 * @param task
	 * @throws PlatformException
	 */
	@Override
	protected void transfer(CollectTask task) throws PlatformException {
		// 交易记录
		CollectInfo tradeInfo = null;
		// 被拆分交易集
		List<CollectInfo> tradeInfos = new ArrayList<CollectInfo>();
		// 待更新task
		CollectTask updateTask = new CollectTask();
		// 交易金额
		BigDecimal tradeAmount = task.getAmount();
		// 拆分剩余金额
		BigDecimal remainAmount = task.getAmount();

		// 拆分数
		int separateCount = 1;
		// 报盘是否需要拆单0不需要1需要
		Integer isNeedSplit = null;

		try {

			isNeedSplit = task.getIsNeedSpilt();

			// 通道所允许每次交易的最大金额,如果maxAmount<=0，该通道所属银行没有最大交易金额限制
			String tppToThirdMapKey = task.getPaySysNo()
					+ task.getPayerBankCode();
			// 每个通道银行所规定最大限额
			BigDecimal maxAmount = ThirdPPCacheContainer.tppBankCodeToThirdBankCollectMaxMoneyMap
					.get(tppToThirdMapKey);
			// 每个通道银行所规定最大放大限额
			BigDecimal maxSplitAmount = null;
			if (maxAmount != null && maxAmount.compareTo(BigDecimal.ZERO) > 0) {
				maxSplitAmount = maxAmount.multiply(new BigDecimal(String
						.valueOf(ServerConfig.systemConfig.getTaskSplitNum())));
			}

			// 如果报盘不需要拆单
			if (isNeedSplit != null
					&& CollectTask.IS_NEED_SPLIT_NO == isNeedSplit.intValue()) {

				tradeInfo = new CollectInfo();
				// 把task数据复制到tradeInfo
				BeanUtils.copyProperties(task, tradeInfo);
				// tradeInfo个别属性定制
				tradeInfo.setTaskId(task.getId());
				// 设置状态-交易处理中
				tradeInfo.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE
						.getCode());
				// 生成交易流水号
				tradeInfo.setTradeFlow(IDGenerateUtil
						.createTradeFlow(BizType.BROKER_COLLECT.getCode()));
				// 设置通知查询状态-待通知查询
				tradeInfo
						.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_WAITING);

				tradeInfos.add(tradeInfo);
				
				// 设置task拆分信息
				// 拆分状态-不需要拆分
				updateTask.setIsSeparate(Integer
						.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_NO
								.getCode()));
				// 拆分数-1
				updateTask.setSeparateCount(separateCount);

				// 如果该通道对交易金额没有限制
			} else if (maxAmount == null
					|| maxAmount.compareTo(BigDecimal.ZERO) <= 0) {
				tradeInfo = new CollectInfo();
				// 把task数据复制到tradeInfo
				BeanUtils.copyProperties(task, tradeInfo);
				// tradeInfo个别属性定制
				tradeInfo.setTaskId(task.getId());
				// 设置状态-交易处理中
				tradeInfo.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE
						.getCode());
				// 生成交易流水号
				tradeInfo.setTradeFlow(IDGenerateUtil
						.createTradeFlow(BizType.BROKER_COLLECT.getCode()));
				// 设置通知查询状态-待通知查询
				tradeInfo
						.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_WAITING);

				tradeInfos.add(tradeInfo);
				// 设置task拆分信息
				// 拆分状态-不需要拆分
				updateTask.setIsSeparate(Integer
						.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_NO
								.getCode()));
				// 拆分数-1
				updateTask.setSeparateCount(separateCount);
				
				// 如果代扣金额超过放大金额，系统不于代扣
			} else if (maxSplitAmount != null
					&& maxSplitAmount.compareTo(task.getAmount()) < 0) {
				tradeInfo = new CollectInfo();
				// 把task数据复制到tradeInfo
				BeanUtils.copyProperties(task, tradeInfo);
				// tradeInfo个别属性定制
				tradeInfo.setTaskId(task.getId());
				// 设置状态-交易失败
				tradeInfo.setStatus(Constants.TppConstants.TRADE_STATE_FAILER
						.getCode());
				tradeInfo.setFailReason("报盘金额已超过放大倍数总金额");
				// 生成交易流水号
				tradeInfo.setTradeFlow(IDGenerateUtil
						.createTradeFlow(BizType.BROKER_COLLECT.getCode()));
				// 设置通知查询状态-不需要通知查询
				tradeInfo
						.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_NO);

				tradeInfos.add(tradeInfo);
				// 设置task拆分信息
				// 拆分状态-不需要拆分
				updateTask.setIsSeparate(Integer
						.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_NO
								.getCode()));
				// 拆分数-1
				updateTask.setSeparateCount(separateCount);
				// 设置待更新task属性
				updateTask.setId(task.getId());
				// 状态-已发送
				updateTask.setStatus(Integer
						.valueOf(Constants.TppConstants.SEND_STATUS_SENDED
								.getCode()));
				// 转发
				collectInfoService.transfer(updateTask, tradeInfos);
				// 写入通知mq,需要自己捕获异常
				try {
					collectInfoService.sendNotifyMergeMsg(tradeInfo);
				} catch (Exception e) {
					logger.error("================", e);
				}
				//TODO
				//拆单失败
				CacheUtil.addFailTimes(cacheClient, tradeInfo);
				return;
				// 该通道对交易金额有限制
			} else {
				// 如果交易金额<=定义最大金额，则不拆分
				if (tradeAmount.compareTo(maxAmount) <= 0) {
					tradeInfo = new CollectInfo();
					// 把task数据复制到tradeInfo
					BeanUtils.copyProperties(task, tradeInfo);
					// tradeInfo个别属性定制
					tradeInfo.setTaskId(task.getId());
					// 设置状态-交易处理中
					tradeInfo
							.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE
									.getCode());
					// 生成交易流水号
					tradeInfo.setTradeFlow(IDGenerateUtil
							.createTradeFlow(BizType.BROKER_COLLECT.getCode()));
					// 设置通知查询状态-待通知查询
					tradeInfo
							.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_WAITING);
					tradeInfos.add(tradeInfo);
					// 设置task拆分信息
					// 拆分状态-不需要拆分
					updateTask.setIsSeparate(Integer
							.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_NO
									.getCode()));
					// 拆分数-1
					updateTask.setSeparateCount(separateCount);
					// 如果交易金额>定义最大金额，进行拆分
				} else {
					while (remainAmount.compareTo(maxAmount) > 0) {
						tradeInfo = new CollectInfo();
						// 把task数据复制到tradeInfo
						BeanUtils.copyProperties(task, tradeInfo);
						// tradeInfo个别属性定制
						tradeInfo.setTaskId(task.getId());
						// 设置状态-交易处理中
						tradeInfo
								.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE
										.getCode());
						// 重设交易金额-最高金额
						tradeInfo.setAmount(maxAmount);
						// 生成交易流水号
						tradeInfo.setTradeFlow(IDGenerateUtil
								.createTradeFlow(BizType.BROKER_COLLECT
										.getCode()));
						// 设置通知查询状态-待通知查询
						tradeInfo
								.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_WAITING);
						tradeInfos.add(tradeInfo);
						remainAmount = remainAmount.subtract(maxAmount);
						separateCount++;
					}
					// 循环以后，如果remainAmount>0,则把剩余金额拆分成一条交易记录
					if (remainAmount.compareTo(BigDecimal.ZERO) > 0) {
						tradeInfo = new CollectInfo();
						// 把task数据复制到tradeInfo
						BeanUtils.copyProperties(task, tradeInfo);
						// tradeInfo个别属性定制
						tradeInfo.setTaskId(task.getId());
						// 设置状态-交易处理中
						tradeInfo
								.setStatus(Constants.TppConstants.TRADE_STATE_MIDDLE
										.getCode());
						// 重设交易金额-最高金额
						tradeInfo.setAmount(remainAmount);
						// 生成交易流水号
						tradeInfo.setTradeFlow(IDGenerateUtil
								.createTradeFlow(BizType.BROKER_COLLECT
										.getCode()));
						// 设置通知查询状态-待通知查询
						tradeInfo
								.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_WAITING);
						tradeInfos.add(tradeInfo);
					}
					// 设置task拆分信息
					// 拆分状态-不需要拆分
					updateTask
							.setIsSeparate(Integer
									.valueOf(Constants.TppConstants.TASK_IS_SEPARATE_YES
											.getCode()));
					// 拆分数-1
					updateTask.setSeparateCount(separateCount);
				}
			}
			// 设置待更新task属性
			updateTask.setId(task.getId());
			// 状态-已发送
			updateTask.setStatus(Integer
					.valueOf(Constants.TppConstants.SEND_STATUS_SENDED
							.getCode()));
			// 转发
			collectInfoService.transfer(updateTask, tradeInfos);
			// 调用支付通道
			callChannel(tradeInfos);
		} catch (Exception e) {
			logger.error("任务转发异常", e);
			throw new PlatformException("任务转发异常", e);
		}

	}

	/**
	 * 将交易数据发送到通道
	 * 
	 * @param info
	 * @throws PlatformException
	 */
	@Override
	protected void callChannel(List<CollectInfo> tradeInfos)
			throws PlatformException {
		CollectReqVo collectReqVo = null;
		com.zendaimoney.thirdpp.common.vo.Response response = null;
		// 待更新CollectInfo
		CollectInfo updateCollectInfo = null;
		String responseStatus="";
		String responseMsg="";
		if (tradeInfos != null && tradeInfos.size() > 0) {
			for (int i = tradeInfos.size()-1; i >=0 ; i--) {
				try {
					collectReqVo = new CollectReqVo();
					BeanUtils.copyProperties(tradeInfos.get(i), collectReqVo);
					// 设置业务系统号
					collectReqVo.setBizSys(BizSys.get(tradeInfos.get(i)
							.getBizSysNo()));
					// 设置业务类型-代收
					collectReqVo.setBizType(BizType.BROKER_COLLECT);
					// 设置第三方通道编码
					collectReqVo.setThirdType(ThirdType.get(tradeInfos.get(i)
							.getPaySysNo()));
					// 设置信息类别编码
					collectReqVo.setInfoCategoryCode(collectReqVo
							.getInfoCategoryCode());
					// 如果第一条为交易失败，剩余拆单都不再发送，直接处理为交易失败
					if(Constants.TppConstants.TRADE_STATE_FAILER.getCode().equals(responseStatus)){
						
						updateCollectInfo = new CollectInfo();
						// 设置交易流水号
						updateCollectInfo.setTradeFlow(tradeInfos.get(i)
								.getTradeFlow());
						// 设置交易状态-交易失败
						updateCollectInfo
								.setStatus(Constants.TppConstants.TRADE_STATE_FAILER
										.getCode());
						// 设置通知查询状态-不需要通知查询
						updateCollectInfo
								.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_NO);
						// 设置回盘时间
						updateCollectInfo.setThirdReturnTime(CalendarUtils
								.parsefomatCalendar(Calendar.getInstance(),
										CalendarUtils.LONG_FORMAT));
						// 设置失败原因
						updateCollectInfo.setFailReason(responseMsg);
						collectInfoService.update(updateCollectInfo);

						// 写入通知mq,需要自己捕获异常
						collectInfoService
								.sendNotifyMergeMsg(tradeInfos.get(i));
						
						continue;
					}
					response = channelService.collectCommond(collectReqVo);
					logger.info("response code:" + response.getCode()
							+ ",tradeFlow:" + collectReqVo.getTradeFlow()
							+ "response msg:" + response.getMsg());
					// 如果是交易异常，把交易明细状态修改为交易异常,其他情况(交易成功、交易失败、交易处理中)都叫由支付通道处理
					if (Constants.TppConstants.TRADE_STATE_ABNORMAL.getCode()
							.equals(response.getCode())) {
						updateCollectInfo = new CollectInfo();
						// 设置交易流水号
						updateCollectInfo.setTradeFlow(tradeInfos.get(i)
								.getTradeFlow());
						// 设置交易状态-交易异常
						updateCollectInfo
								.setStatus(Constants.TppConstants.TRADE_STATE_ABNORMAL
										.getCode());
						// 设置通知查询状态-不需要通知查询
						updateCollectInfo
								.setNotifyQueryStatus(CollectInfo.NOTIFY_QUERY_STATUS_NO);
						// 设置回盘时间
						updateCollectInfo.setThirdReturnTime(CalendarUtils
								.parsefomatCalendar(Calendar.getInstance(),
										CalendarUtils.LONG_FORMAT));
						// 设置失败原因(取前面200个字符)
						updateCollectInfo
								.setFailReason(response.getMsg() != null ? StringUtils
										.substring(response.getMsg(), 0, 200)
										: "");
					
						collectInfoService.update(updateCollectInfo);
						//TODO
						//交易失败
						CacheUtil.addFailTimes(cacheClient, tradeInfos.get(i));

						// 写入通知mq,需要自己捕获异常
						collectInfoService
								.sendNotifyMergeMsg(tradeInfos.get(i));
					// 第一条请求为失败时，设置状态标示为失败，拆单其他笔数都不再发送	
					}else if(Constants.TppConstants.TRADE_STATE_FAILER.getCode()
							.equals(response.getCode())){
						
						responseStatus = Constants.TppConstants.TRADE_STATE_FAILER.getCode();
						responseMsg = response.getMsg() != null ? StringUtils
								.substring(response.getMsg(), 0, 200)
								: "";
								//TODO
								//交易失败
								CacheUtil.addFailTimes(cacheClient, tradeInfos.get(i));
								
					}else if(Constants.TppConstants.TRADE_STATE_SUCCESS.getCode()
							.equals(response.getCode())){
						
						//TODO
						//交易成功
						CacheUtil.addAmount(cacheClient, tradeInfos.get(i));
					}
					
				} catch (Exception e) {
					logger.error("调用TPP通道异常", e);
					// 写MONGODB
					try {
						collectInfoService.sendNotifyQueryMsg(collectReqVo);
					} catch (Exception e1) {
						logger.error("==================", e1);
					}

				}

			}

		}

	}
}
