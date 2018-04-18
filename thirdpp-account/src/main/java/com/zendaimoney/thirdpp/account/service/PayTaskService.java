package com.zendaimoney.thirdpp.account.service;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.account.dao.PayInfoDao;
import com.zendaimoney.thirdpp.account.dao.PayTaskDao;
import com.zendaimoney.thirdpp.account.entity.PayTask;


@Transactional
@Service
public class PayTaskService {
	@Resource(name = "payTaskDao")
	private PayTaskDao payTaskDao;

	@Resource(name = "payInfoDao")
	private PayInfoDao payInfoDao;

	public void insert(PayTask payTask) {
		payTaskDao.insert(payTask);
	}

	public PayTask getBizTaskByTaskId(long id) throws SQLException{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		return payTaskDao.queryPayTaskByTaskId(param);
	}
}
