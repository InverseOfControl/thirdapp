package com.zendaimoney.thirdpp.channel.service;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.dao.RequestDao;
import com.zendaimoney.thirdpp.channel.entity.Request;

@Service
@Transactional
public class RequestService {

	@Resource(name = "requestDao")
	private RequestDao requestDao;

	/**
	 * 记录请求操作
	 * 
	 * @param request
	 */
	public void insert(Request request) {
		requestDao.insert(request);
	}

	/**
	 * 根据request修改request
	 * 
	 * @param request
	 */
	public void update(Request request) {
		requestDao.update(request);
	}

	/**
	 * 根据reqid获取请求记录
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Request queryRequestByReqId(String reqId) throws SQLException {
		Request request = requestDao.queryRequestByReqId(reqId);
		return request;
	}
}
