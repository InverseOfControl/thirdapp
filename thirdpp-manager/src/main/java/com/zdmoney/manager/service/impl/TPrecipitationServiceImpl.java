package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppRoutePrecipitationMapper;
import com.zdmoney.manager.models.TppRoutePrecipitation;
import com.zdmoney.manager.service.TppRoutePrecipitationService;

@Service
public class TPrecipitationServiceImpl implements TppRoutePrecipitationService {
    
    @Autowired
    private TppRoutePrecipitationMapper precipitationMapper;

    @Override
    public List<Map> getPrecipitationInfoList(Map<String, Object> params) {
        return precipitationMapper.getPrecipitationInfoList(params);
    }

    @Override
    public int getPrecipitationInfoListCount(Map<String, Object> params) {
        return precipitationMapper.getPrecipitationInfoListCount(params);
    }

    @Override
    public int insert(TppRoutePrecipitation info) {
        return precipitationMapper.insert(info);
    }

    @Override
    public TppRoutePrecipitation selectPrecipitationInfoByID(Long parseLong) {
        return precipitationMapper.selectPrecipitationInfoByID(parseLong);
    }

    @Override
    public int update(TppRoutePrecipitation info) throws Exception {
        if(info.getId() == null)
            throw new Exception("update时主键不能为空");
        return precipitationMapper.update(info);
    }

    @Override
    public boolean hasCertificateNo(String CertificateNo) {
        List<TppRoutePrecipitation> tPrecipitationInfos = precipitationMapper.selectPrecipitationInfoByCertificateNo(CertificateNo);
        if(tPrecipitationInfos == null || tPrecipitationInfos.isEmpty())
            return false;
        return true;
    }

    @Override
    public TppRoutePrecipitation getDtoByCertificateNo(String CertificateNo) {
        List<TppRoutePrecipitation> tPrecipitationInfos = precipitationMapper.selectPrecipitationInfoByCertificateNo(CertificateNo);
        if(tPrecipitationInfos !=null && tPrecipitationInfos.size()>0){
            return tPrecipitationInfos.get(0);
        }
        return null;
    }

}
