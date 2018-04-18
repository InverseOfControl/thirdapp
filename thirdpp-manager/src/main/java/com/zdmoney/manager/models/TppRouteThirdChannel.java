package com.zdmoney.manager.models;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class TppRouteThirdChannel implements Serializable {

    /**  */
    private static final long serialVersionUID = -6907474566734959880L;
    
    private Integer id;
    @NotBlank(message = "请选择通道", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String thirdTypeNo;
    private String thirdTypeName;
    @NotBlank(message = "请选择通道是否可用", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String isAvailable;
    private Integer priority;
    private Integer failTimes;
    private Integer transferTimes;
    private Integer precipitation;
    
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
    public String getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public Integer getFailTimes() {
        return failTimes;
    }
    public void setFailTimes(Integer failTimes) {
        this.failTimes = failTimes;
    }
    public Integer getPrecipitation() {
        return precipitation;
    }
    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }
    public String getThirdTypeName() {
        return thirdTypeName;
    }
    public void setThirdTypeName(String thirdTypeName) {
        this.thirdTypeName = thirdTypeName;
    }
    public Integer getTransferTimes() {
        return transferTimes;
    }
    public void setTransferTimes(Integer transferTimes) {
        this.transferTimes = transferTimes;
    }
    

    

    

}
