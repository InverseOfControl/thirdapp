package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 业务类型
 * @author srainsk
 *
 */
public enum BizTypeEnum {
	/**
	 * 居间人模式代收
	 */
	MEDIATOR_COLLECT("001"),
	/**
	 * 居间人模式代付
	 */
	MEDIATOR_PAY("002"),
	/**
	 * 资金托管融资
	 */
	CAPITAL_FINANCING("003"),
	/**
	 * 资金托管还款
	 */
	CAPITAL_REPAYMENT("004"),
	/**
	 * 资金托管开户
	 */
	CAPITAL_ACCOUNT("005"),
	/**
	 * 资金托管绑卡
	 */
	CAPITAL_TICECARD("006"),
	/**
	 * 资金托管开户绑卡
	 */
	CAPITAL_ACCOUNTCARD("007"),
	/**
	 * 资金托管充值
	 */
	CAPITAL_RECHARGE("008"),
	/**
	 * 资金托管提现  
	 */
	CAPITAL_WITHDRAWALS("009"),
	/**
	 * 实名认证
	 */
	CAPITAL_REALNAME("010"),
	/**
	 * 线上充值
	 */
	ONLINE_RECHARGE("011"),
	
	/**
	 * 线上提现
	 */
	ONLINE_EXTRACTION("012"),
	/**
	 * 线上支付
	 */
	ONLINE_PAY("013"),
	/**
	 * 线上退款
	 */
	ONLINE_REFUND("014"),
	/**
	 * 资金托管转账
	 */
	CAPITAL_TRANSFER("015"),
	/**
	 * 换卡
	 */
	CAPITAL_CHANGECARD("016"),
	/**
	 * 解绑
	 */
	CAPITAL_UNBUNDLING("017"),
	/**
	 * 卡bin查询
	 */
	CAPITAL_CARDBIN("018"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    BizTypeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<BizTypeEnum, String> map ;
    
    static{
    	map = new EnumMap<BizTypeEnum, String>(BizTypeEnum.class);
    	map.put(BizTypeEnum.MEDIATOR_COLLECT , "代收");
    	map.put(BizTypeEnum.MEDIATOR_PAY , "代付");
    	map.put(BizTypeEnum.CAPITAL_FINANCING , "融资");
    	map.put(BizTypeEnum.CAPITAL_REPAYMENT , "还款");
    	map.put(BizTypeEnum.CAPITAL_ACCOUNT , "开户");
    	map.put(BizTypeEnum.CAPITAL_TICECARD , "绑卡");
    	map.put(BizTypeEnum.CAPITAL_ACCOUNTCARD , "开户绑卡");
    	map.put(BizTypeEnum.CAPITAL_RECHARGE , "充值");
    	map.put(BizTypeEnum.CAPITAL_WITHDRAWALS , "提现");
    	map.put(BizTypeEnum.CAPITAL_REALNAME , "实名认证");
    	map.put(BizTypeEnum.ONLINE_RECHARGE , "线上充值");
    	map.put(BizTypeEnum.ONLINE_EXTRACTION , "线上提现");
    	map.put(BizTypeEnum.ONLINE_PAY , "线上支付");
    	map.put(BizTypeEnum.ONLINE_REFUND , "线上退款");
    	map.put(BizTypeEnum.CAPITAL_TRANSFER , "转账");
    	
    	map.put(BizTypeEnum.CAPITAL_CHANGECARD , "换卡");
    	map.put(BizTypeEnum.CAPITAL_UNBUNDLING , "解绑");
    	map.put(BizTypeEnum.CAPITAL_CARDBIN , "卡bin查询");
    	 
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static BizTypeEnum getEnum(String value) {
    	BizTypeEnum tmpKey = null;
        for (BizTypeEnum tmpEnum : BizTypeEnum.values()) {
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
        return BizTypeEnum.map.get(BizTypeEnum.getEnum(value));
    }
}
