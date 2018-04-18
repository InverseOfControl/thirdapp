package com.zdmoney.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TSysUserRoleMapper;
import com.zdmoney.manager.models.TSysUserRole;
import com.zdmoney.manager.service.TSymUserRoleService;

/** 
 *
 * @author 渡心
 * @version 2014年12月18日 下午5:35:50 
 */
@Service
public class TSymUserRoleServiceImpl implements TSymUserRoleService {
	
	@Autowired
	TSysUserRoleMapper tSysUserRoleMapper;

	@Override
	public void insert(TSysUserRole ur) {
		tSysUserRoleMapper.insert(ur);
	}

	@Override
	public void deleteByUserId(Long userId) {
		tSysUserRoleMapper.deleteByUserId(userId);
	}
	
	@Override
	public List select_userRole(String userId, String roleId) {
		return tSysUserRoleMapper.select_userRole(userId, roleId);
	}

}
