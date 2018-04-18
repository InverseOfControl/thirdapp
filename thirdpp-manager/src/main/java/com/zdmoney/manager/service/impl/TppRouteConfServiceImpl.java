package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppRouteConfMapper;
import com.zdmoney.manager.models.TppRouteConf;
import com.zdmoney.manager.service.TppRouteConfService;

@Service
public class TppRouteConfServiceImpl implements TppRouteConfService {

    @Autowired
    private TppRouteConfMapper tppRouteConfMapper;
    
    @Override
    public List<Map> getRouteConfInfoList(Map<String, Object> params) {
        return tppRouteConfMapper.getRouteConfInfoList(params);
    }

    @Override
    public int update(TppRouteConf info) throws Exception {
        if(info.getId() == null)
            throw new Exception("update时主键不能为空");
        return tppRouteConfMapper.update(info);
    }

    @Override
    public TppRouteConf getRouteConfByID(long id) {
        return tppRouteConfMapper.getRouteConfByID(id);
    }

    @Override
    public int insert(TppRouteConf info) {
        return tppRouteConfMapper.insert(info);
    }

}
