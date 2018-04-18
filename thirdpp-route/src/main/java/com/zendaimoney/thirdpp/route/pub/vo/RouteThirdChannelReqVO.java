package com.zendaimoney.thirdpp.route.pub.vo;

/**
 * Created by YM20051 on 2017/5/27.
 */
public class RouteThirdChannelReqVO {

    private Long id;

    // 第三方通道编码
    private String thirdTypeNo;
    private String thirdChannelName;
    private String isAvailable;
    private Integer priority;
    private Integer failTimes;
    private Integer transferTimes;
    private Integer precipitation;

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

    public String getThirdChannelName() {
        return thirdChannelName;
    }

    public void setThirdChannelName(String thirdChannelName) {
        this.thirdChannelName = thirdChannelName;
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

    public Integer getTransferTimes() {
        return transferTimes;
    }

    public void setTransferTimes(Integer transferTimes) {
        this.transferTimes = transferTimes;
    }

    public Integer getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }

    @Override
    public String toString() {
        return "RouteThirdChannelReqVO [id=" + id + ", thirdTypeNo=" + thirdTypeNo
               + ", thirdChannelName=" + thirdChannelName + "]";
    }
}
