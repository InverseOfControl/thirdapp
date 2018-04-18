package com.zdmoney.manager.service;

import java.util.List;

import com.zdmoney.manager.models.TSysUserRole;

/** 
 *
 * @author 渡心
 * @version 2014年12月18日 下午5:35:05 
 */
public interface TSymUserRoleService {

	void insert(TSysUserRole ur);

	void deleteByUserId(Long userId);
	
	List select_userRole(String userId, String roleId);

}
