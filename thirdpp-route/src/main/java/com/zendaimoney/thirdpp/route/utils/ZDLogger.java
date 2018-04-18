package com.zendaimoney.thirdpp.route.utils;

public interface ZDLogger{
    
    public void debug(ZDLogDto dto);


    public void error(ZDLogDto dto);


    public void fatal(ZDLogDto dto);


    public void info(ZDLogDto dto);


    public void warn(ZDLogDto dto);
    
}
