package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TSysRole;
import com.zdmoney.manager.models.TSysRoleApp;

public interface TSysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysRole record);

    int insertSelective(TSysRole record);

    TSysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysRole record);

    int updateByPrimaryKey(TSysRole record);

	List<TSysRole> getRoleList(Map<String, Object> params);

	List<TSysRole> getRolesByUser(@Param(value="userId")Long userId);

	int getRoleUsedCount(List<Long> list);

	void batchDeleteRole(List<Long> list);

	List<TSysRole> getOtherRoles(@Param(value="userId")Long userId);
	
	List select_tSysRoleList(Map<String, Object> params);
	
	Integer select_tSysRoleList_count(Map<String, Object> params);
	
	List select_tSysUserListByRole(Map<String, Object> params);
	
	Integer select_tSysUserListByRole_count(Map<String, Object> params);
	
	List select_tSysAppListByRole(Map<String, Object> params);
	
	Integer select_tSysAppListByRole_count(Map<String, Object> params);
	
	Integer delete_role_app(Map<String, Object> params);
	
	Integer delete_roleAppByRole(@Param(value="roleId")Long roleId);
	
	List select_appList(Map<String, Object> params);
	
	List select_appListByRole(Map<String, Object> params);
	
	Integer insert_sysRoleApp(TSysRoleApp roleApp);
	
	Integer delete_roleUser(Map<String, Object> params);
	
	List select_notRoleUserByRole(Map<String, Object> params);
	
	Integer select_notRoleUserByRole_count(Map<String, Object> params);
	
	Integer delete_roleUserByRole(Long roleId);
	
	List<String> select_appIdsByRoles(List<Long> roleIds);
	
	List select_tSysRoleByRoleName(@Param(value="roleName")String roleName);
}