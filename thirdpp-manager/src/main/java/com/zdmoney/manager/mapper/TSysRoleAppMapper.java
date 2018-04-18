package com.zdmoney.manager.mapper;

import com.zdmoney.manager.models.TSysRoleApp;

public interface TSysRoleAppMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TSysRoleApp record);

    int insertSelective(TSysRoleApp record);

    TSysRoleApp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSysRoleApp record);

    int updateByPrimaryKey(TSysRoleApp record);
}