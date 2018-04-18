package com.zendaimoney.trust.channel.util;

/**
 * 通道常量类
 * @author mencius
 *
 */
public class Constants {

	// 报文是否需要入库-需要
	public static final String MSG_IN_STORAGE_YES = "1";

	// 报文是否需要入库-不需要
	public static final String MSG_IN_STORAGE_NO = "0";
	
	// 支付渠道编码
	public static final String CMB_PAY_SYS_NO = "12";
	
	// utf-8
	public static final String ENCODE_UTF_8 = "utf-8";
	
	// GBK
	public static final String ENCODE_GBK = "GBK";
	
	// 文件路径分隔符
	public static final String FILEPATH_SEPARATOR = "/";
	
	/**
	 * 空格字符串
	 */
	public static final String BLANK = " ";
	
	/**
	 * 字符串 0
	 */
	public static final String ZERO = "0";
	
	// 操作成功
	public static final int OPER_STATUS_SUCCESS = 1;
	
	// 操作失败
	public static final int OPER_STATUS_FAIL = 2;
	
	// 运营方式(0线下运营)
	public static final String OP_MODE_OFFLINE = "0";
	
	// 运营方式(1线上运营)
	public static final String OP_MODE_ONLINE = "1";
	
	// 运营方式(2资金托管)
	public static final String OP_MODE_TRUST = "2";

	// 充值成功
	public static final int RECHARGE_STATUS_SUCCESS = 2;
	
	// 充值失败
	public static final int RECHARGE_STATUS_FAIL = 3;
	
	
	// 提现中
	public static final int DRAW_STATUS_MIDDLE = 2;
	
	// 提现成功
	public static final int DRAW_STATUS_SUCCESS = 3;
	
	// 提现失败
	public static final int DRAW_STATUS_FAIL = 4;
	
	// 转账中
	public static final int TRANSFER_STATUS_MIDDLE = 2;
		
	// 转账成功
	public static final int TRANSFER_STATUS_SUCCESS = 3;
		
	// 转账失败
	public static final int TRANSFER_STATUS_FAIL = 4;
	
	// 银行账户类型-对公
	public static final String BANK_ACCOUNT_TYPE_P = "P";
	
	// 银行账户类型-对私
	public static final String BANK_ACCOUNT_TYPE_C = "C";
		

	/**
	 * 报文状态
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
	 * @author mencius
	 *
	 */
	public enum CmbConstants {

		TRADE_STATE_SUCCESS("000000", "交易成功"), 
		TRADE_STATE_FAILER("111111", "交易失败"), 
		TRADE_STATE_MIDDLE("222222", "交易处理中"), 
		TRADE_STATE_ABNORMAL("333333", "交易异常")

		;
		private final String code;
		private final String desc;

		private CmbConstants(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static CmbConstants get(String code) {
			for (CmbConstants constants : CmbConstants.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			throw new IllegalArgumentException("CmbConstants is not exist : "
					+ code);
		}
	}
	
	public static final String NULL = "null";
	
	
	/**
	 * Communication模块状态
	 * 
	 * @author mencius
	 *
	 */
	public enum CommunicationStatus {
		SOCKET_REQUEST_FAIL("101", "socket请求发送失败"),
		SOCKET_RESPONSE_FAIL("102", "socket接收响应失败"),
		SOCKET_REQUEST_SUCCESS("001", "socket请求成功"),
		SOCKET_OK_STATUS("000", "socket通讯成功"),
		SOCKET_NOT_OK_STATUS("111", "socket通讯失败"),
		PARSE_MESSAGE_ERROR("ERROR", "响应报文解析失败"),
		;

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
	 * 资金托管第三方交易状态
	 * 
	 * @author mencius
	 *
	 */
	public enum CmbStatus {
		
		CMBMB99("CMBMB99", "成功"),
		CMBMBXX("CMBMBXX", "系统错误"), 
		CMBMB02("CMBMB02", "账户余额不足"),
		CMBMB03("CMBMB03", "没有协议"),
		CMBMB05("CMBMB05", "通讯故障，请稍后查询结果"),
		CMBMB06("CMBMB06", "暂停服务"),
		CMBMB07("CMBMB07", "账户已冻结"),
		CMBMB10("CMBMB10", "证件号码不符"),
		CMBMB14("CMBMB14", "非交易时间"),
		CMBMB25("CMBMB25", "MAC校验错"),
		CMBMB35("CMBMB35", "户名不符"),
		CMBMB40("CMBMB40", "银行无此账号"),
		CMBMB70("CMBMB70", "证件类型有误"),
		CMBMB83("CMBMB83", "不支持异地交易"),
		CMBMB84("CMBMB84", "报文格式非法"),
		CMBMB95("CMBMB95", "原交易不存在"),
		CMBMB96("CMBMB96", "交易重复"),
		CMBUS01("CMBUS01", "已经开户"),
		CMBUS02("CMBUS02", "开户失败"),
		CMBUS03("CMBUS03", "用户不存在"),
		CMBUS04("CMBUS04", "用户未绑定此卡"),
		CMBUS05("CMBUS05", "虚拟户账户余额不为0"),
		CMBUS06("CMBUS06", "虚拟户已关户"),
		CMBUS07("CMBUS07", "银行卡户名和开户名不一致"),
		CMBUS08("CMBUS08", "用户类型异常"),
		CMBUS09("CMBUS09", "银行账户仍在实名验证中"),
		CMBPR01("CMBPR01", "散标不存在"),
		CMBPR02("CMBPR02", "散标重复登记"),
		CMBPR03("CMBPR03", "此用户无法借款"),
		CMBPR04("CMBPR04", "散标尚未还款，无法关闭"),
		CMBGR01("CMBGR01", "团不存在"),
		CMBGR02("CMBGR02", "团重复登记"),
		CMBGR03("CMBGR03", "团用户类型不匹配"),
		CMBGR04("CMBGR04", "团风险金用户类型不匹配"),
		CMBGR05("CMBGR05", "无法关闭"),
		CMBTR01("CMBTR01", "原冻结交易不存在"),
		CMBTR02("CMBTR02", "原冻结交易异常"),
		CMBTR03("CMBTR03", "交易金额异常"),
		CMBFI01("CMBFI01", "读取文件错误"),
		CMBFI02("CMBFI02", "文件批次异常"),
		CMBFI03("CMBFI03", "文件汇总数据和明细数据不一致"),
		CMBST01("CMBST01", "没有原充值记录"),
		CMBST02("CMBST02", "清算记录和原充值记录不一致"),
		CMBST03("CMBST03", "原充值记录已经清算"),
		;

		private final String code;
		private final String desc;

		private CmbStatus(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static CmbStatus get(String code) {
			for (CmbStatus status : CmbStatus.values()) {
				if (status.getCode().equals(code))
					return status;
			}
			return  null;
		}
	}
}
