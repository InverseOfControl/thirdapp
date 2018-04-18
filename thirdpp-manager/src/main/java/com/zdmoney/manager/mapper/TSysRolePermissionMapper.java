package com.zdmoney.manager.mapper;

import java.util.List;

import com.zdmoney.manager.models.TSysRolePermission;

public interface TSysRolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysRolePermission record);

    int insertSelective(TSysRolePermission record);

    TSysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysRolePermission record);

    int updateByPrimaryKey(TSysRolePermission record);

	void deleteByRole(Long roleId);

	void deleteByRoleList(List<Long> list);

	void deleteByPermList(List<Long> list);
}