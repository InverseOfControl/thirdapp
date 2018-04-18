package com.zendaimoney.thirdpp.route.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;

/**
 * Created by YM20051 on 2017/6/5.
 */
@TableName(value = "TPP_ROUTE_CHANNEL_COST")
public class RouteChannelCostEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String thirdTypeNo;

    private String costType;
    private String hasLimitAmount;

    private BigDecimal limitAmount;
    private BigDecimal fixedAmount;
    private BigDecimal percent;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "RouteChannelCostEntity [id=" + id + ", thirdTypeNo=" + thirdTypeNo + ", costType="
               + costType + ", hasLimitAmount=" + hasLimitAmount + ", limitAmount=" + limitAmount
               + ", fixedAmount=" + fixedAmount + ", percent=" + percent + ", minAmount="
               + minAmount + ", maxAmount=" + maxAmount + "]";
    }
    
}
