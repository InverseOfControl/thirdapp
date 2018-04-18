package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.service.TSymPermissionService;

/** 
 *
 * @author 00225641
 * @version 2014年12月18日 下午7:29:38 
 */
@Service
public class TSymPermissionServiceImpl implements TSymPermissionService {
	
	@Autowired
	private TSysPermissionMapper tSysPermissionMapper;

	@Override
	public List<TSysPermission> getPermissionList(Map<String, Object> params) {
		return tSysPermissionMapper.getPermissionList(params);
	}

	@Override
	public TSysPermission getPermissionById(Long id) {
		return tSysPermissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void insert(TSysPermission permission) {
		tSysPermissionMapper.insert(permission);
	}

	@Override
	public void update(TSysPermission permission) {
		tSysPermissionMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public List<TSysPermission> getOtherPermissionList(Long id) {
		return tSysPermissionMapper.getOtherPermissionList(id);
	}

	@Override
	public List<TSysPermission> getPermissionByRole(Long roleId) {
		return tSysPermissionMapper.getPermissionByRole(roleId);
	}

	@Override
	public List<TSysPermission> getTopPermissionList(String permType) {
		return tSysPermissionMapper.getTopPermissionList(permType);
	}

	@Override
	public int getPermUsedCount(List<Long> list) {
		return tSysPermissionMapper.getPermUsedCount(list);
	}

	@Override
	public void batchDeletePerm(List<Long> list) {
		tSysPermissionMapper.batchDeletePerm(list);
	}

	@Override
	public int getSonPermsCount(List<Long> list) {
		return tSysPermissionMapper.getSonPermsCount(list);
	}

	@Override
	public List select_tSysPermissionList(Map<String, Object> params) {
		return tSysPermissionMapper.select_tSysPermissionList(params);
	}

	@Override
	public Integer select_tSysPermissionList_count(Map<String, Object> params) {
		return tSysPermissionMapper.select_tSysPermissionList_count(params);
	}

}
