package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 业务类型
 * @author srainsk
 *
 */
public enum AccountBizTypeEnum {
	/**
	 *  居间人模式代收
	 */
	BROKER_COLLECT("001"),
	/**
	 *  居间人模式代付
	 */
	BROKER_PAY("002"),
	/**
	 *  资金托管融资
	 */
	TRUST_FINANCE("003"),
	/**
	 *  资金托管还款
	 */
	TRUST_REFUND("004"),
	/**
	 *  资金托管开户
	 */
	TRUST_OPEN_ACCOUNT("005"),
	/**
	 *  资金托管绑卡
	 */
	TRUST_BIND_CARD("006"),
	/**
	 *  资金托管开户绑卡
	 */
	TRUST_OPEN_ACCOUNT_BIND_CARD("007"),
	/**
	 *  资金托管充值
	 */
	TRUST_RECHARGE("008"),
	/**
	 *  资金托管提现
	 */
	TRUST_WITHDRWA("009"),
	/**
	 *  实名认证
	 */
	CERTIFICATION("010"),
	/**
	 *  线上充值
	 */
	ONLINE_RECHARGE("011"),
	/**
	 *  线上提现
	 */
	ONLINE_WITHDRWA("012"),
	/**
	 *  线上支付
	 */
	ONLINE_PAY("013"),
	/**
	 *  线上退款
	 */
	ONLINE_REFUND("014"),
	/**
	 *  资金托管转账
	 */
	TRUST_TRANSFER("015"),
	/**
	 *  资金托管换卡
	 */
	TRUST_CHANGE_CARD("016"),
	/**
	 *  资金托管解绑
	 */
	TRUST_UNBIND_CARD("017"),
	/**
	 *  卡bin查询
	 */
	BIN_QUERY("018"),
	/**
	 *  第三方对账
	 */
	THIRD_PARTY_ACCOUNT("019")
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountBizTypeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountBizTypeEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountBizTypeEnum, String>(AccountBizTypeEnum.class);
    	map.put(AccountBizTypeEnum.BROKER_COLLECT , "居间人模式代收");
    	map.put(AccountBizTypeEnum.BROKER_PAY , "居间人模式代付");
    	map.put(AccountBizTypeEnum.TRUST_FINANCE , "资金托管融资");
    	map.put(AccountBizTypeEnum.TRUST_REFUND , "资金托管还款");
    	map.put(AccountBizTypeEnum.TRUST_OPEN_ACCOUNT , "资金托管开户");
    	map.put(AccountBizTypeEnum.TRUST_BIND_CARD , "资金托管绑卡");
    	map.put(AccountBizTypeEnum.TRUST_OPEN_ACCOUNT_BIND_CARD , "资金托管开户绑卡");
    	map.put(AccountBizTypeEnum.TRUST_RECHARGE , "资金托管充值");
    	map.put(AccountBizTypeEnum.TRUST_WITHDRWA , "资金托管提现");
    	map.put(AccountBizTypeEnum.CERTIFICATION , "实名认证");
    	map.put(AccountBizTypeEnum.ONLINE_RECHARGE , "线上充值");
    	map.put(AccountBizTypeEnum.ONLINE_WITHDRWA , "线上提现");
    	map.put(AccountBizTypeEnum.ONLINE_PAY , "线上支付");
    	map.put(AccountBizTypeEnum.ONLINE_REFUND , "线上退款");
    	map.put(AccountBizTypeEnum.TRUST_TRANSFER , "资金托管转账");
    	map.put(AccountBizTypeEnum.TRUST_CHANGE_CARD , "资金托管换卡");
    	map.put(AccountBizTypeEnum.TRUST_UNBIND_CARD , "资金托管解绑");
    	map.put(AccountBizTypeEnum.BIN_QUERY , "卡bin查询");
    	map.put(AccountBizTypeEnum.THIRD_PARTY_ACCOUNT , "第三方对账");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountBizTypeEnum getEnum(String value) {
    	AccountBizTypeEnum tmpKey = null;
        for (AccountBizTypeEnum tmpEnum : AccountBizTypeEnum.values()) {
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
        return AccountBizTypeEnum.map.get(AccountBizTypeEnum.getEnum(value));
    }
}
