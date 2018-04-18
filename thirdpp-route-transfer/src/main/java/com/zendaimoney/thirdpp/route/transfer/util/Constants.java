package com.zendaimoney.thirdpp.route.transfer.util;


public class Constants {
	
	/**
	 * Tpp系统相关参数定义
	 * @author 00231257
	 *
	 */
	public enum TppConstants{
		
		
		
		ROUTE_STATUS_UNDO("0","未处理"),
		ROUTE_STATUS_DOING("1","处理中"),
		ROUTE_STATUS_DONE("2","已处理"),
		
		TRADE_STATE_SUCCESS("000000","交易成功"),
        TRADE_STATE_FAILER("111111","交易失败"),
        TRADE_STATE_MIDDLE("222222","交易处理中"),
        TRADE_STATE_ABNORMAL("333333","交易异常"),
        TRADE_STATE_PARTY_SUCCESS("444444","交易部分成功"),
        
        TASK_IS_SEPARATE_YES("1","任务拆分"),
        TASK_IS_SEPARATE_NO("0","任务不拆分"),
        
        SEND_STATUS_WAIT("0","待发送"),
        SEND_STATUS_SENDING("1","发送中"),
        SEND_STATUS_SENDED("2","已发送")
		
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
