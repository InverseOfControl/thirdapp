package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 对账文件下载方式
 * @author srainsk
 *
 */
public enum AccountFetchMethodEnum {
	/**
	 *  FTP
	 */
	FTP("FTP"),
	/**
	 *  HTTP
	 */
	HTTP("HTTP"),
	/**
	 *  HTTPS
	 */
	HTTPS("HTTPS"),
	/**
	 *  SFTP
	 */
	SFTP("SFTP"),
	/**
	 *  手动
	 */
	MANUAL("MANUAL"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountFetchMethodEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountFetchMethodEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountFetchMethodEnum, String>(AccountFetchMethodEnum.class);
    	map.put(AccountFetchMethodEnum.FTP , "FTP");
    	map.put(AccountFetchMethodEnum.HTTP , "HTTP");
    	map.put(AccountFetchMethodEnum.HTTPS , "HTTPS");
    	map.put(AccountFetchMethodEnum.SFTP , "SFTP");
    	map.put(AccountFetchMethodEnum.MANUAL , "手动");
    	
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountFetchMethodEnum getEnum(String value) {
    	AccountFetchMethodEnum tmpKey = null;
        for (AccountFetchMethodEnum tmpEnum : AccountFetchMethodEnum.values()) {
            if (tmpEnum.value.equals(value)) {
                tmpKey = tmpEnum;
                break;
            }
        }
        return tmpKey;
    }
    /**
     * 返回对应的描述.
     * @param value int.
     * @return String
     */
    public static String getEnumDesc(final String value) {
        return AccountFetchMethodEnum.map.get(AccountFetchMethodEnum.getEnum(value));
    }
}
