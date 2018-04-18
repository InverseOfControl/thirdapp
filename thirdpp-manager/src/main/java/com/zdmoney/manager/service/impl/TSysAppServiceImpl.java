package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.mapper.TSysAppMapper;
import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TSysApp;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TSysAppService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TSysAppServiceImpl implements TSysAppService{
	
	@Autowired
	private TSysAppMapper appMapper;

	@Override
	public List<Map> getSysAppList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return appMapper.getSysAppList(params);
	}

	@Override
	public int insert(TSysApp app) {
		// TODO Auto-generated method stub
		return appMapper.insert(app);
	}

	@Override
	public int updateSysApp(TSysApp app) {
		// TODO Auto-generated method stub
		return appMapper.updateSysApp(app);
	}

	@Override
	public TSysApp selectSysAppByID(long id) {
		// TODO Auto-generated method stub
		return appMapper.selectSysAppByID(id);
	}

	@Override
	public int getSysAppListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return appMapper.getSysAppListCount(params);
	}

	@Override
	public int batchDeleteInfo(List<Integer> list) {
		// TODO Auto-generated method stub
		return appMapper.batchDeleteInfo(list);
	}

	@Override
	public List<Map> getSysAppMeanu() {
		// TODO Auto-generated method stub
		return appMapper.getSysAppMeanu();
	}

	@Override
	public int getAppCodeCount(String code) {
		// TODO Auto-generated method stub
		return appMapper.getAppCodeCount(code);
	}

	@Override
	public String getSysAppName(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return appMapper.getSysAppName(params);
	}

	@Override
	public int updateSysAppFlag(TSysApp app) {
		return appMapper.updateSysAppFlag(app);
	}

}
