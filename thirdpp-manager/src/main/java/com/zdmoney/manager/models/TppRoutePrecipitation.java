package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

public class TppRoutePrecipitation implements Serializable {

    /**  */
    private static final long serialVersionUID = -231013466177095297L;
    
    private Integer id;
    @NotBlank(message = "商户号不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String certificateNo;
    @NotBlank(message = "商户名不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String certificateName;
    private String thirdTypeNo;
    private BigDecimal precipitation;
    private String thirdTypeName;
    
    public String getThirdTypeName() {
        return thirdTypeName;
    }
    public void setThirdTypeName(String thirdTypeName) {
        this.thirdTypeName = thirdTypeName;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public String getCertificateNo() {
        return certificateNo;
    }
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }
    public String getCertificateName() {
        return certificateName;
    }
    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }
    
    
    

}
