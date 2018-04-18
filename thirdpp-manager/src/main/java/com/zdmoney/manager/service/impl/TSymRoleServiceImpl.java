package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TSysRoleMapper;
import com.zdmoney.manager.models.TSysRole;
import com.zdmoney.manager.models.TSysRoleApp;
import com.zdmoney.manager.service.TSymRoleService;

/** 
 *
 * @author 渡心
 * @version 2014年12月18日 下午3:34:46 
 */
@Service
public class TSymRoleServiceImpl implements TSymRoleService {
	
	@Autowired
	private TSysRoleMapper tSysRoleMapper;

	@Override
	public List<TSysRole> getRoleList(Map<String, Object> params) {
		return tSysRoleMapper.getRoleList(params);
	}

	@Override
	public TSysRole getRoleById(Long id) {
		return tSysRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void insert(TSysRole role) {
		tSysRoleMapper.insert(role);
	}

	@Override
	public void update(TSysRole role) {
		tSysRoleMapper.updateByPrimaryKey(role);
	}

	@Override
	public List<TSysRole> getRolesByUser(Long userId) {
		return tSysRoleMapper.getRolesByUser(userId);
	}

	@Override
	public int getRoleUsedCount(List<Long> list) {
		return tSysRoleMapper.getRoleUsedCount(list);
	}

	@Override
	public void batchDeleteRole(List<Long> list) {
		tSysRoleMapper.batchDeleteRole(list);
	}

	@Override
	public List<TSysRole> getOtherRoles(Long userId) {
		return tSysRoleMapper.getOtherRoles(userId);
	}

	@Override
	public List select_tSysRoleList(Map<String, Object> params) {
		return tSysRoleMapper.select_tSysRoleList(params);
	}

	@Override
	public Integer select_tSysRoleList_count(Map<String, Object> params) {
		return tSysRoleMapper.select_tSysRoleList_count(params);
	}

	@Override
	public List select_tSysUserListByRole(Map<String, Object> params) {
		return tSysRoleMapper.select_tSysUserListByRole(params);
	}

	@Override
	public Integer select_tSysUserListByRole_count(Map<String, Object> params) {
		return tSysRoleMapper.select_tSysUserListByRole_count(params);
	}

	@Override
	public List select_tSysAppListByRole(Map<String, Object> params) {
		return tSysRoleMapper.select_tSysAppListByRole(params);
	}

	@Override
	public Integer select_tSysAppListByRole_count(Map<String, Object> params) {
		return tSysRoleMapper.select_tSysAppListByRole_count(params);
	}

	@Override
	public Integer delete_role_app(Map<String, Object> params) {
		return tSysRoleMapper.delete_role_app(params);
	}

	@Override
	public List select_appList(Map<String, Object> params) {
		return tSysRoleMapper.select_appList(params);
	}

	@Override
	public List select_appListByRole(Map<String, Object> params) {
		return tSysRoleMapper.select_appListByRole(params);
	}

	@Override
	public Integer delete_roleAppByRole(Long roleId) {
		return tSysRoleMapper.delete_roleAppByRole(roleId);
	}

	@Override
	public Integer insert_sysRoleApp(TSysRoleApp roleApp) {
		return tSysRoleMapper.insert_sysRoleApp(roleApp);
	}

	@Override
	public Integer delete_roleUser(Map<String, Object> params) {
		return tSysRoleMapper.delete_roleUser(params);
	}

	@Override
	public List select_notRoleUserByRole(Map<String, Object> params) {
		return tSysRoleMapper.select_notRoleUserByRole(params);
	}
	
	@Override
	public Integer select_notRoleUserByRole_count(Map<String, Object> params) {
		return tSysRoleMapper.select_notRoleUserByRole_count(params);
	}
	
	@Override
	public Integer delete_roleUserByRole(Long roleId) {
		return tSysRoleMapper.delete_roleUserByRole(roleId);
	}
	
	@Override
	public List<String> select_appIdsByRoles(List<Long> roleIds) {
		return tSysRoleMapper.select_appIdsByRoles(roleIds);
	}

	@Override
	public List select_tSysRoleByRoleName(String roleName) {
		return tSysRoleMapper.select_tSysRoleByRoleName(roleName);
	}

	

}
