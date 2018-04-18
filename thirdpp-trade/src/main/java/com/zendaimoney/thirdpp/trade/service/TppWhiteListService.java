package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.TppWhiteListMapperDao;
import com.zendaimoney.thirdpp.trade.entity.TppWhiteList;

@Transactional
@Service
public class TppWhiteListService {

	@Resource(name = "tppWhiteListMapperDao")
	private TppWhiteListMapperDao tppWhiteListMapperDao;

	/**
	 * 是否有访问权限(白名单)
	 * 
	 * @param infoCategoryCode
	 * @param bankCardNo
	 * @param accountNo
	 * @param bizSysNo
	 * @return
	 * @throws SQLException
	 */
	public boolean isAccessPermission(String infoCategoryCode, String bankCardNo, String accountNo, String bizSysNo) throws SQLException {
		boolean isAccessPermission = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("infoCategoryCode", infoCategoryCode);
		paramMap.put("bankCardNo", bankCardNo);
		paramMap.put("accountNo", accountNo);
		paramMap.put("bizSysNo", bizSysNo);
		List<TppWhiteList> list = null;
		list = tppWhiteListMapperDao.queryTppWhiteList(paramMap);
		if (list != null && !list.isEmpty()) {
			isAccessPermission = true;
		}

		return isAccessPermission;
	}

}
