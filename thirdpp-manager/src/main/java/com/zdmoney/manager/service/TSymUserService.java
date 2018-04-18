package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TSysUser;

/** 
 *
 * @author 渡心
 * @version 2014年12月18日 下午2:19:28 
 */
public interface TSymUserService {

	List<TSysUser> getUserList(Map<String, Object> params);

	TSysUser getUserById(Long id);

	Long insert(TSysUser user);

	Long update(TSysUser user);

	TSysUser getByLoginNameAndPassword(String loginUserName, String password);
	
	TSysUser getByLoginName(String loginUserName);
	
	List select_tSysUserList(Map<String, Object> params);
	
	Integer select_tSysUserList_count(Map<String, Object> params);
}
