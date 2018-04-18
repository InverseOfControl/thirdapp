package com.zendaimoney.thirdpp.trade.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.RequestDao;
import com.zendaimoney.thirdpp.trade.entity.Request;

@Service
@Transactional
public class RequestService {
	
	@Resource(name = "requestDao")
	private RequestDao requestDao;

	
	public void insert(Request request) {
		requestDao.insert(request);
	}
	
	
	
	

}
