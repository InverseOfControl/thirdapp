package com.zendaimoney.thirdpp.route.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value = "TPP_SYS_T_THIRD_CHANNEL_INFO")
public class SysThirdChannelInfoEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 第三方通道编码
     */
    private String thirdTypeNo;

    // 商户号类型
    private String merchantType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 证书路径
     */
    private String certificateName;

    /**
     * 证书密码
     */
    private String certificatePwd;

    // 创建人
    private String creater;

    // 创建时间
    private String createTime;

    // 更新时间
    private String updateTime;

    // 修改人
    private String updater;

    // 商户号
    private String certificateNo;

    // 状态(0业务通道关闭1业务通道开启)
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getThirdTypeNo() {
        return thirdTypeNo;
    }

    public void setThirdTypeNo(String thirdTypeNo) {
        this.thirdTypeNo = thirdTypeNo;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getCertificatePwd() {
        return certificatePwd;
    }

    public void setCertificatePwd(String certificatePwd) {
        this.certificatePwd = certificatePwd;
    }
}