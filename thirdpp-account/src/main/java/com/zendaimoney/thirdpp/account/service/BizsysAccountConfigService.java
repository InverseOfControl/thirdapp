package com.zendaimoney.thirdpp.account.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.account.dao.BizsysAccountConfigDao;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;

@Service
@Component(value = "com.zendaimoney.thirdpp.account.service.BizsysAccountConfigService")
public class BizsysAccountConfigService {

	@Resource(name = "bizsysAccountConfigDao")
	private BizsysAccountConfigDao bizsysAccountConfigDao;
	
	public BizsysAccountConfig getBizsysAccountConfigByBizSysNo(String bizSysNo) throws SQLException {
		return bizsysAccountConfigDao.getBizsysAccountConfigByBizSysNo(bizSysNo);
	}
	
	public List<BizsysAccountConfig> getAvaiableBizsysAccountConfig() throws SQLException {
		return bizsysAccountConfigDao.getAvaiableBizsysAccountConfig();
	}
	
	public BizsysAccountConfig getBizsysAccountConfigByConfigId(String configId) throws SQLException {
		return bizsysAccountConfigDao.getBizsysAccountConfigByConfigId(configId);
	}
}
