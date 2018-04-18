package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 操作请求状态
 * @author srainsk
 *
 */
public enum AccountChannelRequestStatusEnum {
	/**
	 *  初始状态
	 */
	INIT("0"),
	/**
	 *  下载文件失败
	 */
	DOWNLOAD_FILE_FAIL("1"),
	/**
	 *  下载文件成功
	 */
	DOWNLOAD_FILE_SUCCESS("2"),
	/**
	 *  入流水表失败
	 */
	INSERT_ACCOUNT_INFO_FAIL("3"),
	/**
	 *  入流水表成功
	 */
	INSERT_ACCOUNT_INFO_SUCCESS("4"),
	/**
	 *  对账操作失败
	 */
	ACCOUNT_FAILED("5"),
	/**
	 *  对账操作成功
	 */
	ACCOUNT_SUCCESS("6"),
	/**
	 *  备份操作失败
	 */
	BACKUP_FAIL("7"),
	/**
	 *  备份操作成功
	 */
	BACKUP_SUCCESS("8"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountChannelRequestStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountChannelRequestStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountChannelRequestStatusEnum, String>(AccountChannelRequestStatusEnum.class);
    	map.put(AccountChannelRequestStatusEnum.INIT , "初始状态");
    	map.put(AccountChannelRequestStatusEnum.DOWNLOAD_FILE_FAIL , "下载文件失败");
    	map.put(AccountChannelRequestStatusEnum.DOWNLOAD_FILE_SUCCESS , "下载文件成功");
    	map.put(AccountChannelRequestStatusEnum.INSERT_ACCOUNT_INFO_FAIL , "入流水表失败");
    	map.put(AccountChannelRequestStatusEnum.INSERT_ACCOUNT_INFO_SUCCESS , "入流水表成功");
    	map.put(AccountChannelRequestStatusEnum.ACCOUNT_FAILED , "对账操作失败");
    	map.put(AccountChannelRequestStatusEnum.ACCOUNT_SUCCESS , "对账操作成功");
    	map.put(AccountChannelRequestStatusEnum.BACKUP_FAIL , "备份操作失败");
    	map.put(AccountChannelRequestStatusEnum.BACKUP_SUCCESS , "备份操作成功");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountChannelRequestStatusEnum getEnum(String value) {
    	AccountChannelRequestStatusEnum tmpKey = null;
        for (AccountChannelRequestStatusEnum tmpEnum : AccountChannelRequestStatusEnum.values()) {
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
        return AccountChannelRequestStatusEnum.map.get(AccountChannelRequestStatusEnum.getEnum(value));
    }
}
