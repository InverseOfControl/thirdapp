package com.zdmoney.manager.utils;

import java.util.List;
import java.util.Map;

public class RouteTransformUtil {
    
    //将数据库 IS_AVAILABLE 字典解析成中文
    @SuppressWarnings("unchecked")
    public static void convertIsAvailable(List<Map> list){
        if(list != null && list.size() >0){
            for (Map d : list) {
                Object object = d.get("IS_AVAILABLE");
                if(object != null){
                    String s = object.toString();
                    if("1".equals(s)){
                        d.put("IS_AVAILABLE", "是");
                    }else if("2".equals(s)){
                        d.put("IS_AVAILABLE", "<span style='color:red'>否</span>");
                    }
                }
            }
        }
        
    }
    
    
    //将数据库 IS_AVAILABLE 字典解析成中文
    @SuppressWarnings("unchecked")
    public static void convertCostType(List<Map> list){
        if(list != null && list.size() >0){
            for (Map d : list) {
                Object object = d.get("COST_TYPE");
                if(object != null){
                    String s = object.toString();
                    if("1".equals(s)){
                        d.put("COST_TYPE", "固定");
                    }else if("2".equals(s)){
                        d.put("COST_TYPE", "百分比");
                    }
                }
            }
        }
        
    }
    
  //将数据库 IS_AVAILABLE 字典解析成中文
    @SuppressWarnings("unchecked")
    public static void convertHasLimitAmount(List<Map> list){
        if(list != null && list.size() >0){
            for (Map d : list) {
                Object object = d.get("HAS_LIMIT_AMOUNT");
                if(object != null){
                    String s = object.toString();
                    if("1".equals(s)){
                        d.put("HAS_LIMIT_AMOUNT", "是");
                    }else if("2".equals(s)){
                        d.put("HAS_LIMIT_AMOUNT", "否");
                    }
                }
            }
        }
        
    }

}
