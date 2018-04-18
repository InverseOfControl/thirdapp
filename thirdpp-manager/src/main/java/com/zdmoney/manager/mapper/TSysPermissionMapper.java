package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TSysPermission;

public interface TSysPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysPermission record);

    int insertSelective(TSysPermission record);

    TSysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysPermission record);

    int updateByPrimaryKey(TSysPermission record);

	List<TSysPermission> getPermissionList(Map<String, Object> params);

	List<TSysPermission> getOtherPermissionList(@Param(value="id")Long id);

	List<TSysPermission> getPermissionByRole(@Param(value="roleId")Long roleId);

	List<TSysPermission> getTopPermissionList(@Param(value="permType")String permType);

	int getPermUsedCount(List<Long> list);

	void batchDeletePerm(List<Long> list);

	int getSonPermsCount(List<Long> list);
	
	List select_tSysPermissionList(Map<String, Object> params);
	
	Integer select_tSysPermissionList_count(Map<String, Object> params);
}