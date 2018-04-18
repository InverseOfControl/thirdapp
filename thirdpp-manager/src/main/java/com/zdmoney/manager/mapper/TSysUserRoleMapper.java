package com.zdmoney.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TSysUserRole;

public interface TSysUserRoleMapper {
    int insert(TSysUserRole record);

    int insertSelective(TSysUserRole record);

	void deleteByUserId(Long userId);
	
	List select_userRole(@Param(value="userId")String userId, @Param(value="roleId") String roleId);
}