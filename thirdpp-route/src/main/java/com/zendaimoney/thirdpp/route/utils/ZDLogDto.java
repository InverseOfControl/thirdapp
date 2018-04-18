package com.zendaimoney.thirdpp.route.utils;

public class ZDLogDto {

    private Object    heInfo;//头尾部
    private Object    titleInfo;//标题
    private Object    msg;//body
    private Object    custom;
    private Throwable throwable;
    private Class<?>  clazz; 
    private String uuid;
    
    public ZDLogDto(Class<?>  clazz) {
        this.clazz = clazz;
    }

    public Object getHeInfo() {
        return heInfo;
    }

    public ZDLogDto setHeInfo(Object heInfo) {
        this.heInfo = heInfo;
        return this;
    }

    public Object getTitleInfo() {
        return titleInfo;
    }

    public ZDLogDto setTitleInfo(Object titleInfo) {
        this.titleInfo = titleInfo;
        return this;
    }

    public Object getMsg() {
        return msg;
    }

    public ZDLogDto setMsg(Object msg) {
        this.msg = msg;
        return this;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public ZDLogDto setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public ZDLogDto setClazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }
    
    
    
    public Object getCustom() {
        return custom;
    }

    public ZDLogDto setCustom(Object custom) {
        this.custom = custom;
        return this;
    }
    
    public ZDLogDto setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public ZDLogDto clear() {
            this.setHeInfo(null);
            this.setMsg(null);
            this.setThrowable(null);
            this.setTitleInfo(null);
            this.setCustom(null);
           // dto.setClazz(null);
        return this;
    }
    
    

}
