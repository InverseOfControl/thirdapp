package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TSysPermission;

/** 
 *
 * @author 00225641
 * @version 2014年12月18日 下午7:23:21 
 */
public interface TSymPermissionService {

	List<TSysPermission> getPermissionList(Map<String, Object> params);

	TSysPermission getPermissionById(Long id);

	void insert(TSysPermission permission);

	void update(TSysPermission permission);

	List<TSysPermission> getOtherPermissionList(Long id);

	List<TSysPermission> getPermissionByRole(Long roleId);

	List<TSysPermission> getTopPermissionList(String permType);

	int getPermUsedCount(List<Long> list);

	void batchDeletePerm(List<Long> list);

	int getSonPermsCount(List<Long> list);
	
	List select_tSysPermissionList(Map<String, Object> params);
	
	Integer select_tSysPermissionList_count(Map<String, Object> params);

}
