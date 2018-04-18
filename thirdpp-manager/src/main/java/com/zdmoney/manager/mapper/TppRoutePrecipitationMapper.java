package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppRoutePrecipitation;;

public interface TppRoutePrecipitationMapper {
    
    List<Map> getPrecipitationInfoList(Map<String, Object> params);
    int getPrecipitationInfoListCount(Map<String, Object> params);
    int insert(TppRoutePrecipitation info);
    TppRoutePrecipitation selectPrecipitationInfoByID(Long parseLong);
    int update(TppRoutePrecipitation info);
    List<TppRoutePrecipitation> selectPrecipitationInfoByCertificateNo(String CertificateNo);
    TppRoutePrecipitation getDtoByCertificateNo(String CertificateNo);

}
