package com.zdmoney.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TSysUserMapper;
import com.zdmoney.manager.service.HealthCheckService;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {
	@Autowired
	private TSysUserMapper tSysUserMapper;

	public Integer testConnection() {
		return tSysUserMapper.checkHealth();
	}

}
