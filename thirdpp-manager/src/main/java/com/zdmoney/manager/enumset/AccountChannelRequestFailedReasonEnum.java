package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 操作请求失败原因
 * @author srainsk
 *
 */
public enum AccountChannelRequestFailedReasonEnum {
	/**
	 *  下载失败原因
	 */
	DOWNLOAD_FAIL("1"),
	/**
	 *  入操作流水表失败原因
	 */
	INSERT_ACCOUNT_INFO_FAIL("3"),
	/**
	 *  记录业务系统对账通知表失败原因
	 */
	INSERT_ACCOUNT_BIZSYS_NOTIFY_FAIL("5"),
	/**
	 *  备份失败原因
	 */
	BACKUP_FAIL("7"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountChannelRequestFailedReasonEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountChannelRequestFailedReasonEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountChannelRequestFailedReasonEnum, String>(AccountChannelRequestFailedReasonEnum.class);
    	map.put(AccountChannelRequestFailedReasonEnum.DOWNLOAD_FAIL , "下载失败原因");
    	map.put(AccountChannelRequestFailedReasonEnum.INSERT_ACCOUNT_INFO_FAIL , "入操作流水表失败原因");
    	map.put(AccountChannelRequestFailedReasonEnum.INSERT_ACCOUNT_BIZSYS_NOTIFY_FAIL , "记录业务系统对账通知表失败原因");
    	map.put(AccountChannelRequestFailedReasonEnum.BACKUP_FAIL , "备份失败原因");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountChannelRequestFailedReasonEnum getEnum(String value) {
    	AccountChannelRequestFailedReasonEnum tmpKey = null;
        for (AccountChannelRequestFailedReasonEnum tmpEnum : AccountChannelRequestFailedReasonEnum.values()) {
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
        return AccountChannelRequestFailedReasonEnum.map.get(AccountChannelRequestFailedReasonEnum.getEnum(value));
    }
}
