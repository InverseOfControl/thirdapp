package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TSysUserMapper;
import com.zdmoney.manager.models.TSysUser;
import com.zdmoney.manager.service.TSymUserService;

/** 
 *
 * @author 00225641
 * @version 2014年12月18日 下午2:20:16 
 */
@Service
public class TSymUserServiceImpl implements TSymUserService {

	@Autowired
	private TSysUserMapper tSysUserMapper;
	
	@Override
	public List<TSysUser> getUserList(Map<String, Object> params) {
		return tSysUserMapper.getUserList(params);
	}

	@Override
	public TSysUser getUserById(Long id) {
		return tSysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public Long insert(TSysUser user) {
		return tSysUserMapper.insert(user);
	}

	@Override
	public Long update(TSysUser user) {
		return tSysUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public TSysUser getByLoginNameAndPassword(String loginUserName,
			String password) {
		return tSysUserMapper.getByLoginNameAndPassword(loginUserName, password);
	}
	@Override
	public TSysUser getByLoginName(String loginUserName) {
		return tSysUserMapper.getByLoginName(loginUserName);
	}

	@Override
	public List select_tSysUserList(Map<String, Object> params) {
		return tSysUserMapper.select_tSysUserList(params);
	}

	@Override
	public Integer select_tSysUserList_count(Map<String, Object> params) {
		return tSysUserMapper.select_tSysUserList_count(params);
	}

}
