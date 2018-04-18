package com.zdmoney.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TSysRolePermissionMapper;
import com.zdmoney.manager.models.TSysRolePermission;
import com.zdmoney.manager.service.TSymRolePermissionService;

/** 
 *
 * @author 渡心
 * @version 2014年12月19日 下午5:14:59 
 */
@Service
public class TSymRolePermissionServiceImpl implements TSymRolePermissionService {
	
	@Autowired
	private TSysRolePermissionMapper tSysRolePermissionMapper;

	@Override
	public void insert(TSysRolePermission rp) {
		tSysRolePermissionMapper.insert(rp);
	}

	@Override
	public void deleteByRole(Long roleId) {
		tSysRolePermissionMapper.deleteByRole(roleId);
	}

	@Override
	public void deleteByRoleList(List<Long> list) {
		tSysRolePermissionMapper.deleteByRoleList(list);
	}

	@Override
	public void deleteByPermList(List<Long> list) {
		tSysRolePermissionMapper.deleteByPermList(list);
	}

}
