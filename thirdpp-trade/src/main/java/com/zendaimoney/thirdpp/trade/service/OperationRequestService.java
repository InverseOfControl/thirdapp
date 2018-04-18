package com.zendaimoney.thirdpp.trade.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.OperationRequestDao;
import com.zendaimoney.thirdpp.trade.entity.OperationRequest;

@Transactional
@Service
public class OperationRequestService {

	@Resource(name = "operationRequestDao")
	private OperationRequestDao operationRequestDao;
	
	public void insert(OperationRequest operationRequest) {
		operationRequestDao.insert(operationRequest);
	}
	
	public void update(OperationRequest operationRequest) {
		operationRequestDao.update(operationRequest);
	}
}
