package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppOperationRequestMapper;
import com.zdmoney.manager.service.TppOperationRequestService;

@Service
public class TppOperationRequestServiceImpl implements	
	TppOperationRequestService {

	@Autowired
	private TppOperationRequestMapper tppOperationRequestMapper;

	@Override
	public List selectOperationRequestList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tppOperationRequestMapper.selectOperationRequestList(params);
	}
	@Override
	public Integer selectOperationRequestListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tppOperationRequestMapper.selectOperationRequestListCount(params);
	}
	@Override
	public List selectOperationRequestListById(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tppOperationRequestMapper.selectOperationRequestListById(params);
	}
	

}
