package com.zendaimoney.thirdpp.route.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.zendaimoney.thirdpp.route.entity.SysThirdChannelInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by YM20051 on 2017/5/25.
 */
public interface SysThirdChannelInfoMapper extends AutoMapper<SysThirdChannelInfoEntity> {

    public List<SysThirdChannelInfoEntity> findByAll(@Param("status") Integer status);
}
