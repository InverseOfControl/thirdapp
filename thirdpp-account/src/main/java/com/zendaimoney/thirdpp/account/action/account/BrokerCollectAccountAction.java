package com.zendaimoney.thirdpp.account.action.account;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.AccountAnnotation;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.AccountInfoTemple;
import com.zendaimoney.thirdpp.account.entity.AccountTaskTemple;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.CollectInfoService;
import com.zendaimoney.thirdpp.account.service.CollectTaskService;
import com.zendaimoney.thirdpp.account.util.Constants;

@AccountAnnotation(bizType = Constants.BizType.BROKER_COLLECT)
@Component(value = "com.zendaimoney.thirdpp.account.action.account.BrokerCollectAccountAction")
public class BrokerCollectAccountAction extends AccountAction {

	private static final Log logger = LogFactory.getLog(BrokerCollectAccountAction.class);
	
	@Autowired
	private CollectInfoService collectInfoService;
	@Autowired
	private CollectTaskService collectTaskService;
	@Override
	protected AccountInfoTemple getBizInfo(String tradeFlow) throws PlatformException {
		try {
			return collectInfoService.queryBizInfoByTradeFlow(tradeFlow);
		} catch (SQLException e) {
			logger.error("通道对账查询 CollectInfo 异常【" + tradeFlow + "】", e);
			String failReason = StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH);
			throw new PlatformException(failReason, e);
		}
	}

	@Override
	protected AccountTaskTemple getBizTask(long taskId) throws PlatformException {
		try {
			return collectTaskService.getBizTaskByTaskId(taskId);
		} catch (SQLException e) {
			logger.error("通道对账查询  CollectTask 异常【" + taskId + "】", e);
			String failReason = StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH);
			throw new PlatformException(failReason, e);
		}
	}

	@Override
	protected void update(List<AccountInfo> subBatchAccountInfo, List<AccountInfoTemple> subBatchAccountInfoTempleInfo)
			throws PlatformException {
		channelAccountInfoService.batchUpdateCollect(subBatchAccountInfo, subBatchAccountInfoTempleInfo);
	}

}
