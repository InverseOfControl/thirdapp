package com.zendaimoney.thirdpp.route.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;

@TableName(value = "DD_T_THIRD_FIELD_MAPPER")
public class ThirdFieldMapperEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 第三方支付平台编码
    private String thirdPartyType;

    // tpp字段编码
    private String tppFieldCode;

    // 第三方支付平台字段编码
    private String thirdFieldCode;

    // 字段名称
    private String fieldName;

    // 字段类型
    private String fieldType;

    // FIELD_TYPE=0该值有效，对应是支付通道代收业务银行最高限额
    private BigDecimal collectMaxMoney;

    // FIELD_TYPE=0该值有效，对应是支付通道代付业务银行最高限额
    private BigDecimal payMaxMoney;

    // FIELD_TYPE=0该值有效，对应是支付通道快捷业务银行最高限额
    private BigDecimal quickPayMaxMoney;
    //FIELD_TYPE=0该值有效，状态(0银行通道关闭1银行通道开启)
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(String thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }

    public String getTppFieldCode() {
        return tppFieldCode;
    }

    public void setTppFieldCode(String tppFieldCode) {
        this.tppFieldCode = tppFieldCode;
    }

    public String getThirdFieldCode() {
        return thirdFieldCode;
    }

    public void setThirdFieldCode(String thirdFieldCode) {
        this.thirdFieldCode = thirdFieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public BigDecimal getCollectMaxMoney() {
        return collectMaxMoney;
    }

    public void setCollectMaxMoney(BigDecimal collectMaxMoney) {
        this.collectMaxMoney = collectMaxMoney;
    }

    public BigDecimal getPayMaxMoney() {
        return payMaxMoney;
    }

    public void setPayMaxMoney(BigDecimal payMaxMoney) {
        this.payMaxMoney = payMaxMoney;
    }

    public BigDecimal getQuickPayMaxMoney() {
        return quickPayMaxMoney;
    }

    public void setQuickPayMaxMoney(BigDecimal quickPayMaxMoney) {
        this.quickPayMaxMoney = quickPayMaxMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}