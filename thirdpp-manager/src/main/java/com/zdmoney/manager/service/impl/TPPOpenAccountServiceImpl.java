package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TAppIpsMapper;
import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.mapper.TppOpenAccountMapper;
import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TPPOpenAccount;
import com.zdmoney.manager.service.TAppIpsService;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TPPOpenAccountService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TPPOpenAccountServiceImpl implements TPPOpenAccountService{
	
	@Autowired
	private TppOpenAccountMapper accountMapper;

	@Override
	public List<Map> getOpenAccountList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return accountMapper.getOpenAccountList(params);
	}

	@Override
	public int getOpenAccountListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return accountMapper.getOpenAccountListCount(params);
	}

	@Override
	public TPPOpenAccount getOpenAccountById(String id) {
		// TODO Auto-generated method stub
		return accountMapper.getOpenAccountById(id);
	}

	
	
}
