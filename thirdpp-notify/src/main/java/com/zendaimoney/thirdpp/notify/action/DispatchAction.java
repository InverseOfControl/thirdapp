package com.zendaimoney.thirdpp.notify.action;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.notify.entity.TotalOrder;
import com.zendaimoney.thirdpp.notify.util.BizActionUtil;
import com.zendaimoney.thirdpp.notify.util.LogPrn;

/**
 * 纷发处理器
 * 
 * @author 00225642
 * 
 */
@Service
public class DispatchAction implements ApplicationContextAware {

	private static final LogPrn logger = new LogPrn(DispatchAction.class);

	private ApplicationContext applicationContext;

	private static Map<String, BizActionTarget> bizActionTargetMap;

	@PostConstruct
	private void initMap() {
		bizActionTargetMap = BizActionUtil.getBizActionTarget();
	}

	/**
	 * 查询，分发到指定业务处理action.
	 * 
	 */
	public TotalOrder executeQueryAction(String bizType, long taskId) {
		Action action = getAction(bizType);
		return action.executeQuery(taskId);
	}

	public int executeUpdateAction(String bizType, TotalOrder totalOrder)
			throws DataAccessException {
		Action action = getAction(bizType);
		return action.executeUpdate(totalOrder);
	}

	public Long executeQueryTaskId(String bizType, String tradeFlow)
			throws SQLException {
		Action action = getAction(bizType);
		return action.executeQueryByFlow(tradeFlow);
	}

	public String executeQueryFailReason(String bizType, Long taskId)
			throws SQLException {
		Action action = getAction(bizType);
		return action.executeQueryFailReason(taskId);
	}

	private Action getAction(String bizType) {
		String errorMsg = "";
		Action action = null;
		BizActionTarget target = bizActionTargetMap.get(bizType);
		if (target == null) {
			errorMsg = "request match action error. action method is not found. bizType : "
					+ bizType;
			logger.error(errorMsg);
			return action;
		}
		Class<? extends Action> clazz = target.getActionClazz();
		try {
			action = (Action) applicationContext.getBean(clazz.getName());
		} catch (Exception e) {
			errorMsg = "instance action error : " + clazz.getName();
			logger.error(errorMsg, e);
			return action;
		}
		return action;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
