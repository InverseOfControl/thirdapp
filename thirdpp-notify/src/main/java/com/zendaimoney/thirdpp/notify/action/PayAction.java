package com.zendaimoney.thirdpp.notify.action;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.notify.annotations.BizActionAnnotation;
import com.zendaimoney.thirdpp.notify.entity.TotalOrder;

@BizActionAnnotation(bizType = BizType.BROKER_PAY)
@Component("com.zendaimoney.thirdpp.notify.action.PayAction")
public class PayAction extends Action {
	
	@Override
	protected TotalOrder query(long taskId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int modify(TotalOrder totalOrder) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Long queryTaskIdByFlow(String tradeFlow) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String queryFailReason(Long taskId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
