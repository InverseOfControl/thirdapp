package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.SysAppDao;
import com.zendaimoney.thirdpp.trade.entity.SysApp;

@Transactional
@Service
public class SysAppService {

	@Resource(name = "sysAppDao")
	private SysAppDao sysAppDao;

	/**
	 * 根据appCode获取应用系统信息。
	 * 
	 * @param appCode
	 * @param ip
	 * @return
	 * @throws SQLException
	 */
	public SysApp querySysApp(String appCode) throws SQLException {
		SysApp sysApp = sysAppDao.querySysApp(appCode);
		return sysApp;
	}
}
