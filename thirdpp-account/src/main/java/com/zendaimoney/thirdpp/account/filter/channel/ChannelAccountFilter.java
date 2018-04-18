package com.zendaimoney.thirdpp.account.filter.channel;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.action.account.AccountAction;
import com.zendaimoney.thirdpp.account.action.account.AccountActionTarget;
import com.zendaimoney.thirdpp.account.annotation.ChannelFilterAnnotation;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.ChannelAccountInfoService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.ActionMapperUtil;
import com.zendaimoney.thirdpp.account.util.Constants;

@ChannelFilterAnnotation(filterStep = Constants.ChannelFilter.FILTER_ANNOTATION_ACCOUNT)
@Component(value = "com.zendaimoney.thirdpp.account.filter.channel.ChannelAccountFilter")
public class ChannelAccountFilter implements ChannelFilter {

	private static final Log logger = LogFactory.getLog(ChannelAccountFilter.class);
	@Autowired
	private ChannelAccountInfoService channelAccountInfoService;
	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;
	
	@Override
	public void doFilter(ChannelAccountConfig config, ChannelAccountRequest request, ChannelFilterChain chain, boolean isHandle) {
		String currentThreadName = Thread.currentThread().getName();
		if (canAccount(request, config)) {
			try {
				// 根据对账流水表中该次请求的最后一条状态是第三方对账完成的记录的业务类型
				// 要是没有记录 则从请求表中配置的第一个业务类型进行对账
				// 要是有记录，则从该记录所属的业务类型进行对账
				if (isHandle) {
					logger.info("通道【手工】对账对账开始......");
				} else {
					logger.info(currentThreadName + "通道对账对账开始......");
				}
				long currentStartTime = System.currentTimeMillis();
				String bizType = request.getBizType();
				AccountInfo ac =  channelAccountInfoService.getLastSuccessByReqId(request.getReqId());
				Set<String> needAccountBizType = getNeedAccountBizType(bizType, ac);
				if (accountOpt(needAccountBizType, request)) {
					request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_ACCOUNT_SUCCESS);
					request.setFailedReason(StringUtils.EMPTY);
					channelAccountRequestService.update(request);
				}
				long currentEndTime = System.currentTimeMillis();
				if (isHandle) {
					logger.info("通道【手工】对账对账结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				} else {
					logger.info(currentThreadName + "通道对账对账结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				}
			} catch (Exception e) {
				request.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_ACCOUNT_FAILED);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setFailedReason(StringUtils.substring(e.getMessage(), 0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				request.setAccountFailedTimes(request.getAccountFailedTimes() + 1);
				channelAccountRequestService.update(request);
				throw new PlatformException("通道对账批量对账异常", e);
			}
		} 
		
		chain.doFilter(config, request, Constants.ChannelFilter.FILTER_ANNOTATION_ACCOUNT, isHandle);
	}
	
	private Set<String> getNeedAccountBizType(String bizType, AccountInfo ac){
		Set<String> needAccountBizType = new HashSet<String>();
		String bt[] = bizType.split(Constants.STRING_DOWN_LINE);
		if (ac == null) {
			// 还有没有与业务表进行对账操作
			for (String s : bt) {
				needAccountBizType.add(s);
			}
		} else {
			String currentBizType = ac.getBizType();
			for (int i = bt.length - 1; i >= 0; i --) {
				needAccountBizType.add(bt[i]);
				if (currentBizType == bt[i]) {
					break;
				}
			}
		}
		return needAccountBizType;
	}
	
	private boolean canAccount(ChannelAccountRequest request, ChannelAccountConfig config) {
		boolean flag = true;
		int status = request.getStatus();
		if(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INSERT_TABLE_SUCCESS != status
			&& Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_ACCOUNT_FAILED != status) {
			flag = false;
		}
		return flag;
	}
	
	private boolean accountOpt(Set<String> needAccountBizType, ChannelAccountRequest request) throws PlatformException {
		for (String bizType : needAccountBizType) {
			AccountActionTarget act = ActionMapperUtil.accountActionMap.get(bizType);
			if (act == null) {
				throw new PlatformException("通道对账对账失败, 暂不支持业务类型【" + bizType + "】");
			}
			Class<? extends AccountAction> cla = act.getActionClazz();
			AccountAction action = (AccountAction)ServerConfig.getBean(cla.getName());
			action.account(request, bizType);
		}
		return true;
	}
	
}
