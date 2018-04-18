package com.zendaimoney.thirdpp.route.entity;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value = "TPP_ROUTE_CONF")
public class RouteConfEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String routeName;
    private Integer priority;
    private String isAvailable;
    private String routeClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getRouteClass() {
        return routeClass;
    }

    public void setRouteClass(String routeClass) {
        this.routeClass = routeClass;
    }
}