package com.zendaimoney.thirdpp.route.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.zendaimoney.thirdpp.route.entity.ThirdFieldMapperEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by YM20051 on 2017/5/25.
 */
public interface ThirdFieldMapper extends AutoMapper<ThirdFieldMapperEntity> {

    public List<ThirdFieldMapperEntity> findByAll(@Param("status") Integer status);
}
