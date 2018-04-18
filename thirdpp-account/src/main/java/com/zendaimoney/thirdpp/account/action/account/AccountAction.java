package com.zendaimoney.thirdpp.account.action.account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.AccountInfoTemple;
import com.zendaimoney.thirdpp.account.entity.AccountTaskTemple;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.ChannelAccountInfoService;
import com.zendaimoney.thirdpp.account.util.CalendarUtils;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.common.vo.Response;

public abstract class AccountAction {

	@Autowired
	protected ChannelAccountInfoService channelAccountInfoService;
	
	private static final Log logger = LogFactory.getLog(AccountAction.class);
	
	public void account(ChannelAccountRequest request, String bizType) {
		List<AccountInfo> accountInfoList = new ArrayList<AccountInfo>();
		try {
			accountInfoList = getNotAccountByReqIdAndBizType(request.getReqId(), bizType);
		} catch (SQLException e) {
			logger.error("通道对账获取该业务类型下的未对账记录异常", e);
			String failReason = StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH);
			throw new PlatformException(failReason, e);
		}
		
		List<AccountInfoTemple> collectInfoList = new ArrayList<AccountInfoTemple>();
		AccountInfoTemple accountObj = null;
		if (accountInfoList != null && accountInfoList.size() > 0) {
			for (AccountInfo info : accountInfoList) {
				String tradeFlow = info.getTradeFlow();
				accountObj = getBizInfo(tradeFlow);
				// 第三方存在交易，我方不存在该笔交易（即原始交易订单不存在）
				if (accountObj == null) {
					info.setAccountStatus(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_STATUS_FAILED);
					info.setFailedReason(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_FAILED_REASON_NO_ORIGINAL_DEAL);
					collectInfoList.add(accountObj);
				} else {
					long taskId = accountObj.getTaskId();
					AccountTaskTemple collectTask = getBizTask(taskId);
					if (collectTask == null) {
						logger.error("通道对账未发现【 taskId = " + taskId + "】的任务");
						info.setAccountStatus(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_STATUS_FAILED);
						info.setFailedReason(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_FAILED_REASON_NO_ORIGINAL_DEAL);
						collectInfoList.add(null);
						continue;
					}
					
					info.setBizFlow(accountObj.getBizFlow());
					info.setBizSysNo(accountObj.getBizSysNo());
					info.setTaskId(accountObj.getTaskId());
					
					info.setTaskAmount(collectTask.getAmount());
					info.setIsSeparate(collectTask.getIsSeparate());
					info.setSeparateCount(collectTask.getSeparateCount());

					// 状态不匹配
					if (!Response.SUCCESS_RESPONSE_CODE.equals(accountObj.getStatus())) {
						info.setAccountStatus(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_STATUS_FAILED);
						info.setFailedReason(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_FAILED_REASON_DEAL_STATUS_NOT_MATCH);
						collectInfoList.add(null);
						continue;
					}
					// 金额不匹配
					if (new BigDecimal(info.getAmount()).compareTo(accountObj
							.getAmount()) != 0) {
						info.setAccountStatus(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_STATUS_FAILED);
						info.setFailedReason(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_FAILED_REASON_DEAL_AMOUNT_NOT_MATCH);
						collectInfoList.add(null);
						continue;
					}
					
					// 状态匹配  + 金额匹配 = 对账成功
					info.setAccountStatus(Constants.AccountInfo.ACCOUNT_INFO_ACCOUNT_STATUS_SUCCESS);
					accountObj.setSettleDate(CalendarUtils.getShortFormatNow());
					collectInfoList.add(accountObj);
				}
			}
			batchUpdate(accountInfoList, collectInfoList);
		}
		accountInfoList = null; collectInfoList = null;
	}
	
	private List<AccountInfo> getNotAccountByReqIdAndBizType(String reqId, String bizType) throws SQLException {
		return channelAccountInfoService.getNotAccountByReqIdAndBizType(reqId, bizType);
	}
	
	protected abstract AccountInfoTemple getBizInfo(String tradeFlow) throws PlatformException;
	
	protected abstract AccountTaskTemple getBizTask(long taskId) throws PlatformException;
	
	private void batchUpdate(List<AccountInfo> accountInfoList, List<AccountInfoTemple> collectInfoList) {
		if (accountInfoList != null && !accountInfoList.isEmpty()) {
			// accountInfoList.size() 应该是等于  collectInfoList.size()
			// 那么我们只需要控制500 条进行一次的提交
			int size = accountInfoList.size();
			int batchDefinitionSize = ServerConfig.systemConfig.getBatchSize();
			if (batchDefinitionSize <= 0) {
				batchDefinitionSize = 500;
			}
			int batchSize = size / batchDefinitionSize;
			for (int i = 0; i < batchSize; i++) {
				List<AccountInfo> subBatchAccountInfo = accountInfoList.subList(batchDefinitionSize * i, batchDefinitionSize * (i + 1));
				List<AccountInfoTemple> subBatchAccountInfoTempleInfo = collectInfoList.subList(batchDefinitionSize * i, batchDefinitionSize * (i + 1));
				update(subBatchAccountInfo, subBatchAccountInfoTempleInfo);
				subBatchAccountInfo = null; subBatchAccountInfoTempleInfo = null;
			}
			List<AccountInfo> remainAccountInfo = accountInfoList.subList(batchDefinitionSize * batchSize,size);
			List<AccountInfoTemple> remainAccountInfoTempleInfo =  collectInfoList.subList(batchDefinitionSize * batchSize,size);
			update(remainAccountInfo, remainAccountInfoTempleInfo);
			remainAccountInfo = null; remainAccountInfoTempleInfo = null;
		}
	}
	
	protected abstract void update(List<AccountInfo> subBatchAccountInfo, List<AccountInfoTemple> subBatchAccountInfoTempleInfo) throws PlatformException;
}
