package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

public class TppRouteConf implements Serializable {

    /**  */
    private static final long serialVersionUID = -1029731256184968894L;
    
    private Integer id;
    @NotBlank(message = "路由逻辑名不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String routeName;
    private Integer priority;
    @NotBlank(message = "请选择类型", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String isAvailable;
    @NotBlank(message = "路由逻辑类不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
    private String routeClass;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getRouteName() {
        return routeName;
    }
    public void setRouteName(String routeName) {
        this.routeName = routeName;
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
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    

}
