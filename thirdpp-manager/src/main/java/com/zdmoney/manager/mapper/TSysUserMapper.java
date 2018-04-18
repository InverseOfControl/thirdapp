package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TSysUser;

public interface TSysUserMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(TSysUser record);

    Long insertSelective(TSysUser record);

    TSysUser selectByPrimaryKey(Long id);

    Long updateByPrimaryKeySelective(TSysUser record);

    Long updateByPrimaryKey(TSysUser record);

	List<TSysUser> getUserList(Map<String, Object> params);

	TSysUser getByLoginNameAndPassword(@Param(value="loginUserName")String loginUserName, @Param(value="password")String password);
	
	TSysUser getByLoginName(@Param(value="loginUserName")String loginUserName);

	Integer checkHealth();
	
	List select_tSysUserList(Map<String, Object> params);
	
	Integer select_tSysUserList_count(Map<String, Object> params);
}