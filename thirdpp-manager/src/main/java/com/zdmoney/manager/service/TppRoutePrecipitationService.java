package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppRoutePrecipitation;

public interface TppRoutePrecipitationService {
    
    List<Map> getPrecipitationInfoList(Map<String, Object> params);
    int getPrecipitationInfoListCount(Map<String, Object> params);
    int insert(TppRoutePrecipitation info);
    TppRoutePrecipitation selectPrecipitationInfoByID(Long parseLong);
    TppRoutePrecipitation getDtoByCertificateNo(String CertificateNo);
    boolean hasCertificateNo(String CertificateNo);
    int update(TppRoutePrecipitation info) throws Exception;

}
