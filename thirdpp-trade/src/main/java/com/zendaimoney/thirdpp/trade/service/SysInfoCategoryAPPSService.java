package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.SysInfoCategoryAPPSDao;
import com.zendaimoney.thirdpp.trade.entity.SysInfoCategoryAPPS;

@Transactional
@Service
public class SysInfoCategoryAPPSService {

	@Resource(name = "sysInfoCategoryAPPSDao")
	private SysInfoCategoryAPPSDao sysInfoCategoryAPPSDao;

	/**
	 * 此业务系统是否有访问该信息类别权限
	 * 
	 * @param infoCategoryCode
	 * @param appCode
	 * @return
	 * @throws SQLException
	 */
	public boolean isAccessPermission(String infoCategoryCode, String appCode) throws SQLException {
		boolean isAccessPermission = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("infoCategoryCode", infoCategoryCode);
		paramMap.put("appCode", appCode);
		List<SysInfoCategoryAPPS> list = null;
			list = sysInfoCategoryAPPSDao.querySysInfoCategoryAPPS(paramMap);
			if (list != null && !list.isEmpty()) {
				isAccessPermission = true;
			}
		return isAccessPermission;
	}

}
