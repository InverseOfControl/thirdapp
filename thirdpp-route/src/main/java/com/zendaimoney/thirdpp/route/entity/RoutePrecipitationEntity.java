package com.zendaimoney.thirdpp.route.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;

/**
 * Created by YM20051 on 2017/6/5.
 */
@TableName(value = "TPP_ROUTE_PRECIPITATION")
public class RoutePrecipitationEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    // 商户号
    private String certificateNo;
    // 第三方通道编码
    private String thirdTypeNo;

    private BigDecimal precipitation;

    // 商户名称
    private String certificateName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getThirdTypeNo() {
        return thirdTypeNo;
    }

    public void setThirdTypeNo(String thirdTypeNo) {
        this.thirdTypeNo = thirdTypeNo;
    }

    public BigDecimal getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(BigDecimal precipitation) {
        this.precipitation = precipitation;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }
}
