package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

public class TppRouteChannelCost implements Serializable {
    
    /**  */
    private static final long serialVersionUID = 2863058591952334568L;
    private Integer id;
    @NotBlank(message = "第三方通道编码为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String thirdTypeNo;
    private String thirdTypeName;
    @NotBlank(message = "请选择类型", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String costType;
    private String hasLimitAmount;
    private BigDecimal limitAmount;
    private BigDecimal fixedAmount;
    private BigDecimal percent;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String range;
    private String[] ids;
    
    
    
    public String getRange() {
        return range;
    }
    public void setRange(String range) {
        this.range = range;
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
    public String getCostType() {
        return costType;
    }
    public void setCostType(String costType) {
        this.costType = costType;
    }
    public String getHasLimitAmount() {
        return hasLimitAmount;
    }
    public void setHasLimitAmount(String hasLimitAmount) {
        this.hasLimitAmount = hasLimitAmount;
    }
    public BigDecimal getLimitAmount() {
        return limitAmount;
    }
    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }
    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }
    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }
    public BigDecimal getPercent() {
        return percent;
    }
    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
    public BigDecimal getMinAmount() {
        return minAmount;
    }
    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }
    public String getThirdTypeName() {
        return thirdTypeName;
    }
    public void setThirdTypeName(String thirdTypeName) {
        this.thirdTypeName = thirdTypeName;
    }
    public String[] getIds() {
        return ids;
    }
    public void setIds(String[] ids) {
        this.ids = ids;
    }
    
    


}
