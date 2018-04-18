package com.zdmoney.manager.service;

import java.util.List;

import com.zdmoney.manager.models.TSysRolePermission;

/** 
 *
 * @author 渡心
 * @version 2014年12月19日 下午5:14:15 
 */
public interface TSymRolePermissionService {

	void insert(TSysRolePermission rp);

	void deleteByRole(Long roleId);

	void deleteByRoleList(List<Long> list);

	void deleteByPermList(List<Long> list);

}
