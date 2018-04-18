package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TSysRole;
import com.zdmoney.manager.models.TSysRoleApp;

/** 
 *
 * @author 渡心
 * @version 2014年12月18日 下午3:33:50 
 */
public interface TSymRoleService {

	List<TSysRole> getRoleList(Map<String, Object> params);

	TSysRole getRoleById(Long id);

	void insert(TSysRole role);

	void update(TSysRole role);

	List<TSysRole> getRolesByUser(Long userId);

	int getRoleUsedCount(List<Long> list);

	void batchDeleteRole(List<Long> list);

	List<TSysRole> getOtherRoles(Long userId);

	List select_tSysRoleList(Map<String, Object> params);
	
	Integer select_tSysRoleList_count(Map<String, Object> params);
	
	List select_tSysUserListByRole(Map<String, Object> params);
	
	Integer select_tSysUserListByRole_count(Map<String, Object> params);
	
	List select_tSysAppListByRole(Map<String, Object> params);
	
	Integer select_tSysAppListByRole_count(Map<String, Object> params);
	
	Integer delete_role_app(Map<String, Object> params);
	
	Integer delete_roleAppByRole(Long roleId);
	
	List select_appList(Map<String, Object> params);
	
	List select_appListByRole(Map<String, Object> params);
	
	Integer insert_sysRoleApp(TSysRoleApp roleApp);
	
	Integer delete_roleUser(Map<String, Object> params);
	
	Integer delete_roleUserByRole(Long roleId);
	
	List select_notRoleUserByRole(Map<String, Object> params);
	
	Integer select_notRoleUserByRole_count(Map<String, Object> params);
	
	List<String> select_appIdsByRoles(List<Long> roleIds);
	
	List select_tSysRoleByRoleName(@Param(value="roleName")String roleName);
}
