package com.zendaimoney.thirdpp.channel.communication.unspay;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class CommonUtil {
    private CommonUtil() {
 
    }
 
    /**
     * 判断字符串是否为空
     * 
     * @param s
     * @return 如果字符串为空或者字符串去除首尾空格为空字符串则返回true,反之返回false
     */
    public static boolean isEmpty(String s) {
        if (s == null || s.trim().length() == 0) {
            return true;
        }
        return false;
    }
 
    /**
     * 判断map是否为空
     * 
     * @param map
     *            map对象
     * @return 如果map==null或者map.size()==0则返回true,反之返回false
     */
    @SuppressWarnings("all")
    public static boolean isEmpty(Map map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }
 
    /***
     * 判断list是否为空
     * 
     * @param list
     *            list对象
     * @return 如果list==null或者list.size==则返回true,反之返回false
     */
    @SuppressWarnings("all")
    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }
 
    /**
     * 将map转成http url请求参数的格式
     * 
     * @param map
     * @return map为空返回null,反之返回类似name=zhangsan&age=14的这样的格式
     */
    public static String map2UrlParams(Map<String, String> map) {
        if (isEmpty(map)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : map.entrySet()) {
            if (!isEmpty(entry.getValue())) {
                String key = entry.getKey();
                try {
                    String value = URLEncoder.encode(entry.getValue(), "UTF-8");
                    sb.append("&" + key + "=" + value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (sb.length() > 0) {
            return sb.substring(1);
        }
        return null;
    }
 
    public static String uuid() {
        return UUID.randomUUID().toString();
    }
     
    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }
 
    /*public static Map<String, String> auth(String token) {
        Map<String, String> header = new HashMap<String, String>();
        header.put(RisecloudSdkConstant.HEAD_X_AUTH_TOKEN, token);
        return header;
    }*/
}