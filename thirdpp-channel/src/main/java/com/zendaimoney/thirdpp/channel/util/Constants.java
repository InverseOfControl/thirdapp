package com.zendaimoney.thirdpp.channel.util;


public class Constants {

	// 报文是否需要入库-需要
	public static final String MSG_IN_STORAGE_YES = "1";

	// 报文是否需要入库-不需要
	public static final String MSG_IN_STORAGE_NO = "0";

	// 运营方式(0线下运营)
	public static final String OP_MODE_OFFLINE = "0";

	// 运营方式(1线上运营)
	public static final String OP_MODE_ONLINE = "1";

	/** 与用友md5加密隐藏字段*/
	public static final String MD5_ENCRYPT = "zendaimoney";

	public static final String NULL = "null";

	/**
	 * Communication模块状态
	 * 
	 * @author 00231257
	 *
	 */
	public enum CommunicationStatus {
		HTTP_OK_STATUS("200", "http成功请求"),
		HTTP_NOT_OK_STATUS("222", "调用第三方HTTP接口返回状态码不正确"),
		HTTP_SEND_EXCEPTION("555", "调用第三方HTTP接口异常"),
		PARSE_MESSAGE_ERROR("666", "响应报文解析失败");

		private final String code;
		private final String desc;

		private CommunicationStatus(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static CommunicationStatus get(String code) {
			for (CommunicationStatus status : CommunicationStatus.values()) {
				if (status.getCode().equals(code))
					return status;
			}
			throw new IllegalArgumentException(
					"CommunicationStatus is not exist : " + code);
		}
	}

	/**
	 * 报文状态
	 * 
	 * @author 00231257
	 *
	 */
	public enum MessageStatus {

		/** 初始化 */
		MESSAGE_INIT("0", "初始化"),

		/** 请求报文解析失败 */
		SEND_REQUEST_PARSE_ERROR("1", "请求报文解析失败"),

		/** 请求报文已发送 */
		SEND_REQUEST("2", "请求报文已发送"),

		/** 请求报文发送失败 */
		SEND_REQUEST_ERROR("3", "请求报文发送失败"),

		/** 收到响应报文 */
		RECEIVED_RESPONSE("4", "收到响应报文"),
		/** 响应报文解析失败 */
		RESPONSE_PARSE_ERROR("5", "响应报文解析失败"),

		;

		private final String code;
		private final String desc;

		private MessageStatus(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static MessageStatus get(String code) {
			for (MessageStatus status : MessageStatus.values()) {
				if (status.getCode().equals(code))
					return status;
			}
			throw new IllegalArgumentException("MessageStatus is not exist : "
					+ code);
		}
	}

	/**
	 * Tpp系统相关参数定义
	 * 
	 * @author 00231257
	 *
	 */
	public enum TppConstants {

		TRADE_STATE_SUCCESS("000000", "交易成功"),
		TRADE_STATE_FAILER("111111", "交易失败"),
		TRADE_STATE_MIDDLE("222222", "交易处理中"),
		TRADE_STATE_ABNORMAL("333333", "交易异常"),
		TRADE_STATE_PARTY_SUCCESS("444444", "交易部分成功");

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
	 * 
	 * @author 00231257
	 *
	 */
	public enum AllinpayConstants {
		TRADE_STATE_SUCC("0000", "交易成功"),
		TRADE_STATE_MID("2000,2001,2003,2005,2007,2008,0003,0014", "交易中间态"), // 以下状态是和通联确认的,通联正在于银行交互的中间状态
		TRADE_STATE_QUERY_MID_V2("0001,2007,2008,1000,1001", "交易中间态"),
		TRADE_STATE_COLLECT_MID_V2("2007,2008", "交易中间态"),
		UNKNOW_RETURNCODE("1002", "发送失败/未查询到符合条件的记录"),
		PAYING_BUSINESS_CODE("00600","付款"),
		COLLECT_BUSINESS_CODE("10101","代收"), 
		PAY_BUSINESS_CODE("10102","代付");

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
			throw new IllegalArgumentException(
					"allinpayConstants is not exist : " + code);
		}
	}

	/**
	 * 银联响应码信息
	 * 
	 * @author mencius
	 *
	 */
	public enum ShunionpayConstants {

		RESPONSE_CODE("00", "响应成功"),
		TRADE_STATE_SUCC("1001", "交易成功"),
		TRADE_STATE_MID("2000,2045,2009", "交易中间态"), // 以下状态是和银联确认的,银联正在于银行交互的中间状态
		QUERY_STATE_MID("0001,2000,2045,2009", "交易中间态"), 
		COLLECT_BUSINESS_CODE("0003","代收"),
		RETCODE_STATE_MID("09,45", "响应码code处于中间态"),
		UNKNOW_RETURNCODE("0001", "发送失败/未查询到符合条件的记录"),
		UNVALID_IP_RETURNCODE("20TY", "IP不通过"), //20TY响应码与描述 存在不一致的情况，需要特殊转换成：IP不通过
		
		PAY_RESPONSE_SUC_CODE("0000","接收成功"),
		PAY_RESPONSE_FAIL_CODE("0100,0101,0102,0103,0104","待查询"),
		PAY_STAT_SUC_CODE("s","交易成功"),
		PAY_STAT_FAIL_CODE("6,9","交易失败")
		;

		private final String code;
		private final String desc;

		private ShunionpayConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static ShunionpayConstants get(String code) {
			for (ShunionpayConstants constants : ShunionpayConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException(
					"ShunionpayConstants is not exist : " + code);
		}
	}

	/**
	 * 宝付响应码信息
	 *
	 * @author mencius
	 *
	 */
	public enum BaofoopayConstants {

		RESPONSE_CODE("00", "响应成功"),
		TRADE_STATE_SUCC("0000,BF00114", "交易成功"),
		TRADE_STATE_MID("BF00100,BF00112,BF00113,BF00115,BF00144,BF00202", "交易中间态"), // 以下状态是和银联确认的,银联正在于银行交互的中间状态
		QUERY_STATE_SUCC("S","交易成功"),
		QUERY_STATE_ERROR("F","交易失败"),
		QUERY_STATE_MID("I,FF", "交易中间态"),
		QUERY_NONEXIST_CODE("BF00128", "该笔订单不存在"),
		COLLECT_BUSINESS_CODE("0003","代收"),

		PAY_RESPONSE_SUC_CODE("0000","接收成功"),
		PAY_RESPONSE_FAIL_CODE("0100,0101,0102,0103,0104","待查询"),
		PAY_STAT_SUC_CODE("s","交易成功"),
		PAY_STAT_FAIL_CODE("6,9","交易失败")
		;

		private final String code;
		private final String desc;

		private BaofoopayConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static BaofoopayConstants get(String code) {
			for (BaofoopayConstants constants : BaofoopayConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException(
					"BaofoopayConstants is not exist : " + code);
		}
	}

	/**
	 * 银生宝响应码信息
	 *
	 * @author mencius
	 *
	 */
	public enum UnspayConstants {

		RESPONSE_CODE("0000", "响应成功"),
		TRADE_STATE_SUCC("00", "交易成功"),
		TRADE_STATE_MID("10", "交易中间态"),
		TRADE_STATE_ERROR("20","交易失败"),
		QUERY_RESPONSE_ERROR_CODE("1003,1023", "查询交易失败")
		;

		private final String code;
		private final String desc;

		private UnspayConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static UnspayConstants get(String code) {
			for (UnspayConstants constants : UnspayConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException(
					"UnspayConstants is not exist : " + code);
		}
	}
	
	/**
	 * 快捷通响应码信息
	 * 
	 * @author mencius
	 *
	 */
	public enum KjtpayConstants {

		RESPONSE_SUC_CODE("T", "响应成功"),
		RESPONSE_MID_CODE("X", "处理中"),
		RESPONSE_ERR_CODE("F", "响应失败"),
		TRADE_STATE_SUCC("TRADE_SUCCESS,TRADE_FINISHED", "交易成功"),
		TRADE_STATE_MID("WAIT_BUYER_PAY,PAY_FINISHED", "交易中间态"),
		SUBMITTED("submitted","提交银行成功"),
		FAILED("failed","出款失败"),
		SUCCESS("success","出款成功")
		;

		private final String code;
		private final String desc;

		private KjtpayConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static ShunionpayConstants get(String code) {
			for (ShunionpayConstants constants : ShunionpayConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException(
					"ShunionpayConstants is not exist : " + code);
		}
	}
	
	/**
	 * 证大爱特响应返回码
	 * @author 00233197
	 *
	 */
	public enum ZendaipayConstants {

		RESPONSE_CODE("000000", "响应成功"),
		RETCODE_STATE_MID("222222,333333", "响应码code处于中间态"),
		TRADE_STATE_SUCC("CRE_01", "交易成功"),
		TRADE_STATE_MID("", "交易中间态"),
		QUERY_STATE_MID("", "交易中间态")
		;

		private final String code;
		private final String desc;

		private ZendaipayConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static ZendaipayConstants get(String code) {
			for (ZendaipayConstants constants : ZendaipayConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException(
					"ZendaipayConstants is not exist : " + code);
		}
	}

	/**
	 * 富友相关参数定义
	 * 
	 * @author 00231257
	 *
	 */
	public enum FuioupayConstrans {

		TRADE_STATE_SUCC("000000", "交易成功"),
		
		TRADE_STATE_MID("999999,200001,1009,1019,1020,1068,111167,1096,1098,299998", "交易中间态"), 

		UNKNOW_RETURNCODE("1025,200029", "发送失败/未查询到符合条件的记录") ,
		
		QUERY_STATE_SUCC("1", "交易成功"),
		QUERY_STATE_MID("0,3,7", "交易中间态"),
		
		QRYTRANS_FUNCTION_CATEGORY("qrytransreq","查询交易"),
		PAYING_FUNCTION_CATEGORY("payforreq","付款(单笔)"),
		COLLECT_FUNCTION_CATEGORY("sincomeforreq","代收(单笔)"),
		PAYING_BUSINESS_CODE("AP01","付款,商户委托富友付款给相关客户"),
		COLLECT_BUSINESS_CODE("AC01","代收"),
		;
		

		private final String code;
		private final String desc;

		private FuioupayConstrans(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static FuioupayConstrans get(String code) {
			for (FuioupayConstrans fuioupayConstrans : FuioupayConstrans
					.values()) {
				if (fuioupayConstrans.getCode().equals(code))
					return fuioupayConstrans;
			}
			throw new IllegalArgumentException(
					"fuioupayConstants is not exist : " + code);
		}

	}

	/**
	 * 银联实名认证响应码信息
	 * 
	 * @author mencius
	 *
	 */
	public enum ShunionAuthConstants {

		AUTH_SUCCESS_CODE("0000", "操作成功"),
		AUTH_MIDDLE_CODE("1111,2222,9916", "操作超时，请重试"),
		;

		private final String code;
		private final String desc;

		private ShunionAuthConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static ShunionAuthConstants get(String code) {
			for (ShunionAuthConstants constants : ShunionAuthConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException(
					"ShunionAuthConstants is not exist : " + code);
		}
	}
	
	/**
	 * 用友通道响应码信息
	 * 
	 * @author mencius
	 *
	 */
	public enum YyoupayConstants {

		YYOU_REPAY_SUCCESS_CODE("000", "成功"),
		YYOU_REPAY_FAIL_CODE("001,002,003,004", "还款失败"),
		YYOU_REPAY_SIGN_CHECK_ERROR_CODE("333", "处理结果校验失败，信息有误！"),
		YYOU_QUERY_FAIL_RETCODE("002", "发送失败/未查询到符合条件的记录") ,
		YYOU_PAY_SUCCESS_CODE("000", "成功"),
		YYOU_PAY_FAIL_CODE("001,002,003,004", "融资失败"),
		YYOU_PAY_MIDDLE_CODE("004", "中间态"),
		YYOU_PAY_SIGN_CHECK_ERROR_CODE("333", "处理结果校验失败，信息有误！"),
		;

		private final String code;
		private final String desc;

		private YyoupayConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static YyoupayConstants get(String code) {
			for (YyoupayConstants constants : YyoupayConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException(
					"YyoupayConstants is not exist : " + code);
		}
	}
}
