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
import com.zendaimoney.thirdpp.account.service.PayInfoService;
import com.zendaimoney.thirdpp.account.service.PayTaskService;
import com.zendaimoney.thirdpp.account.util.Constants;

@AccountAnnotation(bizType = Constants.BizType.BROKER_PAY)
@Component(value = "com.zendaimoney.thirdpp.account.action.account.BrokerPayAccountAction")
public class BrokerPayAccountAction extends AccountAction {
	
	private static final Log logger = LogFactory.getLog(BrokerPayAccountAction.class);
	
	@Autowired
	private PayInfoService payInfoService;
	@Autowired
	private PayTaskService payTaskService;
	
	@Override
	protected AccountInfoTemple getBizInfo(String tradeFlow) throws PlatformException {
		try {
			return payInfoService.queryBizInfoByTradeFlow(tradeFlow);
		} catch (SQLException e) {
			logger.error("通道对账查询 PayInfo 异常【" + tradeFlow + "】", e);
			String failReason = StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH);
			throw new PlatformException(failReason, e);
		}
	}

	@Override
	protected AccountTaskTemple getBizTask(long taskId) throws PlatformException {
		try {
			return payTaskService.getBizTaskByTaskId(taskId);
		} catch (SQLException e) {
			logger.error("通道对账查询  PayTask 异常【" + taskId + "】", e);
			String failReason = StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH);
			throw new PlatformException(failReason, e);
		}
	}

	@Override
	protected void update(List<AccountInfo> subBatchAccountInfo, List<AccountInfoTemple> subBatchAccountInfoTempleInfo) throws PlatformException {
		channelAccountInfoService.batchUpdatePay(subBatchAccountInfo, subBatchAccountInfoTempleInfo);
	}

}
