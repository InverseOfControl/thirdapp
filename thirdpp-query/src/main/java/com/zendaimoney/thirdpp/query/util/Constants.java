package com.zendaimoney.thirdpp.query.util;

/**
 * 
 * @author mencius
 *
 */
public class Constants {

	// mongo查询控制集合名
	public static String TPP_CONFIG_COLLECTION = "TPP_CONFIG";
	
	public static String TPP_QUERY_POOL_NAME = "query";
	
	// 处理状态原始值为： 0
	public static String TPP_QUERY_DEALFLAG_INIT = "0";
	
	// 代表已处理和待备份
	public static String TPP_QUERY_DEALFLAG_DEALED = "1";
	
	// 通知合并状态(0待通知合并、1不需要通知合并、2已通知合并)
	public static String INFO_NOTIFY_MERGE_STATUS = "2";
	
	/**
	 * Tpp系统相关参数定义
	 * @author 00231257
	 *
	 */
	public enum TppConstants{
		
		TRADE_STATE_SUCCESS("000000","交易成功"),
		TRADE_STATE_FAILER("111111","交易失败"),
		TRADE_STATE_MIDDLE("222222","交易处理中"),
		TRADE_STATE_ABNORMAL("333333","交易异常"),
		TRADE_STATE_PARTY_SUCCESS("444444","交易部分成功"),
		
		
		CURRENCY_RMB("0","人民币"),
		
		
		PRIORITY_HIGHEST("3","最高級"),
		PRIORITY_HIGH("2","高級"),
		PRIORITY_MID("1","中级"),
		PRIORITY_COMMON("0","普通"),
		
		BANK_CARD_TYPE_DEPOSIT("1","借记卡"),
		BANK_CARD_TYPE_CREDIT("2","信用卡"),
		
		
		SEND_STATUS_WAIT("0","待发送"),
		SEND_STATUS_SENDING("1","发送中"),
		SEND_STATUS_SENDED("2","已发送"),
		
		TASK_IS_SEPARATE_YES("1","任务拆分"),
		TASK_IS_SEPARATE_NO("0","任务不拆分"),
		;
		private final String code;
		private final String desc;

		private TppConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static TppConstants get(String code) {
			for (TppConstants constants : TppConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException("TppConstants is not exist : "
					+ code);
		}
	}
	
	/**
	 * 通联相关参数定义
	 * @author 00231257
	 *
	 */
	public enum AllinpayConstants{
		
		
		TRADE_STATE_SUCC("0000","交易成功"),
		
		
		UNKNOW_RETURNCODE("1002","未查询到符合条件的记录"),
		;
		
		private final String code;
		private final String desc;

		private AllinpayConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static AllinpayConstants get(String code) {
			for (AllinpayConstants constants : AllinpayConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException("allinpayConstants is not exist : "
					+ code);
		}
		
	}
	
	// 运营方式(0线下运营)
	public static final String OP_MODE_OFFLINE = "0";
	
	// 运营方式(1线上运营)
	public static final String OP_MODE_ONLINE = "1";

}
