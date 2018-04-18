package com.zdmoney.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.EnumSetMapper;
import com.zdmoney.manager.service.EnumSetService;

@Service
public class EnumSetServiceImpl implements EnumSetService {
	
	@Autowired
	EnumSetMapper enumSetMapper;
	
	@Override
	public List select_sysAppList(Map<String, Object> params) {
		return enumSetMapper.select_sysAppList(params);
	}
	
	public List select_dictionaryByType(String dicType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dicType", dicType);
		return enumSetMapper.select_dictionaryByType(params);
	}

	@Override
	public List select_areaInfoParent(String params) {
		return enumSetMapper.select_areaInfoParent(params);
	}

	@Override
	public List select_merType() {
		return enumSetMapper.select_merType();
	}

	@Override
	public List select_infoCategory() {
		return enumSetMapper.select_infoCategory();
	}

	@Override
	public List select_bizType() {
		// TODO Auto-generated method stub
		return enumSetMapper.select_bizType();
	}

	@Override
	public List select_allSysAppList() {
		// TODO Auto-generated method stub
		return enumSetMapper.select_allSysAppList();
	}

	@Override
	public List select_infoCategoryByApp(String appCode) {
		return enumSetMapper.select_infoCategoryByApp(appCode);
	}

}
