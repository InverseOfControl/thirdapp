package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.SysAppIPSDao;
import com.zendaimoney.thirdpp.trade.entity.SysAppIPS;

@Transactional
@Service
public class SysAppIPSService {

	@Resource(name = "sysAppIPSDao")
	private SysAppIPSDao sysAppIPSDao;

	/**
	 * 此ip是否有访问该业务系统权限
	 * 
	 * @param ip
	 * @param appCode
	 * @return
	 * @throws SQLException 
	 */
	public boolean isAccessPermission(String appCode, String ip) throws SQLException {
		boolean isAccessPermission = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ip", ip);
		paramMap.put("appCode", appCode);
		List<SysAppIPS> list = null;
		list = sysAppIPSDao.querySysAppIPS(paramMap);
		if (list != null && !list.isEmpty()) {
			isAccessPermission = true;
		}

		return isAccessPermission;
	}

}
