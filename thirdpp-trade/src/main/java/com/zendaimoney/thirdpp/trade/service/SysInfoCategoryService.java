package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.SysInfoCategoryDao;
import com.zendaimoney.thirdpp.trade.entity.SysInfoCategory;

@Transactional
@Service
public class SysInfoCategoryService {

	@Resource(name = "sysInfoCategoryDao")
	private SysInfoCategoryDao sysInfoCategoryDao;

	/**
	 * 根据信息类别编码查询信息类别
	 * 
	 * @param infoCategoryCode
	 * @return
	 * @throws SQLException 
	 */
	public SysInfoCategory querySysInfoCategoryByCode(String infoCategoryCode) throws SQLException {
		SysInfoCategory sysInfoCategory = null;
		sysInfoCategory = sysInfoCategoryDao.querySysInfoCategoryByCode(infoCategoryCode);
		return sysInfoCategory;
	}

}
