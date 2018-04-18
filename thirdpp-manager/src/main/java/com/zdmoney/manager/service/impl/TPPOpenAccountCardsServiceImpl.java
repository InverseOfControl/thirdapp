package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TAppIpsMapper;
import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.mapper.TppOpenAccountCardsMapper;
import com.zdmoney.manager.mapper.TppOpenAccountMapper;
import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.service.TAppIpsService;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TPPOpenAccountCardsService;
import com.zdmoney.manager.service.TPPOpenAccountService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TPPOpenAccountCardsServiceImpl implements TPPOpenAccountCardsService{
	
	@Autowired
	private TppOpenAccountCardsMapper accountMapper;

	@Override
	public List<Map> getOpenAccountCardsList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return accountMapper.getOpenAccountCardsList(params);
	}

	@Override
	public int getOpenAccountCardsListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return accountMapper.getOpenAccountCardsListCount(params);
	}

	 

	
	
}
