package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TAppIpsMapper;
import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.service.TAppIpsService;
import com.zdmoney.manager.service.TBankInfoService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TAppIpsServiceImpl implements TAppIpsService{
	
	@Autowired
	private TAppIpsMapper appIpsMapper;

	@Override
	public List<Map> getappIpsList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return appIpsMapper.getappIpsList(params);
	}

	@Override
	public int insert(TAppIps ips) {
		// TODO Auto-generated method stub
		return appIpsMapper.insert(ips);
	}

	@Override
	public int updateAppIps(TAppIps ips) {
		// TODO Auto-generated method stub
		return appIpsMapper.updateAppIps(ips);
	}

	@Override
	public TAppIps selectAppIpsByID(long id) {
		// TODO Auto-generated method stub
		return appIpsMapper.selectAppIpsByID(id);
	}

	@Override
	public void batchDeleteIps(List<Integer> listId) {
		// TODO Auto-generated method stub
		appIpsMapper.batchDeleteIps(listId);
	}

	@Override
	public int getappIpsListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return appIpsMapper.getappIpsListCount(params);
	}

	
}
