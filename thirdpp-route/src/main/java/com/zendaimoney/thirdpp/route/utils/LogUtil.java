package com.zendaimoney.thirdpp.route.utils;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 * 统一日志格式  便于分析
 * @author YM10188
 * @version $Id: LogUtil.java, v 0.1 2017年6月13日 上午10:53:11 YM10188 Exp $
 */
public class LogUtil implements ZDLogger {

    //开启日志
    private final boolean mark   = true;
   

    private static class SingletonHolder {
        private static final LogUtil INSTANCE = new LogUtil();
    }

    private LogUtil() {
    }

    public static final LogUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void debug(ZDLogDto dto) {
        if(mark){
            Logger logger =  Logger.getLogger(dto.getClazz());
            logger.debug(this.getLogStr(dto),dto.getThrowable());
        }
    }

    @Override
    public void error(ZDLogDto dto) {
        if(mark){
            Logger logger =  Logger.getLogger(dto.getClazz());
            logger.error(this.getLogStr(dto),dto.getThrowable());
        }
    }

    @Override
    public void fatal(ZDLogDto dto) {
        if(mark){
            Logger logger =  Logger.getLogger(dto.getClazz());
            logger.fatal(this.getLogStr(dto));
        }
    }

    @Override
    public void info(ZDLogDto dto) {
        if(mark){
            Logger logger =  Logger.getLogger(dto.getClazz());
            logger.info(this.getLogStr(dto),dto.getThrowable());
        }
    }

    @Override
    public void warn(ZDLogDto dto) {
        if(mark){
            Logger logger =  Logger.getLogger(dto.getClazz());
            logger.warn(this.getLogStr(dto),dto.getThrowable());
        }
    }
    
    //获取日志字符串 （带样式）
    private String getLogStr(ZDLogDto dto) {
        StringBuilder sb = new StringBuilder();
        if (dto != null) {
        	if(StringUtil.isEmpty(dto.getUuid())){
        		dto.setUuid("");
        	}
            if(!StringUtil.isEmpty(dto.getCustom())){//自定义格式
                sb.append(dto.getUuid()+dto.getCustom());
            }else{
                if (!StringUtil.isEmpty(dto.getHeInfo())) {
                    sb.append(dto.getUuid()+"============" + dto.getHeInfo() + "============");
                    return sb.toString();
                }
    
                if (!StringUtil.isEmpty(dto.getTitleInfo())) {
                    sb.append(dto.getUuid()+dto.getTitleInfo() + "-");
                }
    
                if (!StringUtil.isEmpty(dto.getMsg())) {
                    sb.append("[");
                    sb.append(dto.getMsg());
                    sb.append("]");
                }
            }
            
        }
        return sb.toString();
    }

    

}
