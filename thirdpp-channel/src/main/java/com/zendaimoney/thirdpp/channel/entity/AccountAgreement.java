package com.zendaimoney.thirdpp.channel.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 协议支付账户表
 *
 * @author wulj
 */
public class AccountAgreement implements Serializable {
    private static final long serialVersionUID = 7634319158460228443L;

    private Long id;

    private String name;

    private String idType;

    private String idNum;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
