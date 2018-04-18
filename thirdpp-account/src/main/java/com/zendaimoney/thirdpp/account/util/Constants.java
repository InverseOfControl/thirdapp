package com.zendaimoney.thirdpp.account.util;


public class Constants {
	
	public static final String STRING_POINT = ".";
	
	public static final String STRING_COMMA = ",";
	
	public static final String STRING_SLASH = "/";
	
	public static final String STRING_CA = "|";
	
	public static final String STRING_JIN = "#";
	
	public static final String STRING_CA_TR = "\\|";
	
	public static final String STRING_BLANK = " ";
	
	public static final String STRING_DOWN_LINE = "_";
	
	public static final String FETCH_METHOD_FTP = "FTP";
	
	public static final String FETCH_METHOD_HTTP = "HTTP";
	
	public static final String FETCH_METHOD_HTTPS = "HTTPS";
	
	public static final String FETCH_METHOD_SFTP = "SFTP";
	
	public static final String FILE_SUFFIX_CSV = "csv";
	
	public static final String FILE_SUFFIX_XLS = "xls";
	
	public static final String FILE_SUFFIX_XLSX = "xlsx";
	
	public static final String FILE_SUFFIX_TXT = "txt";
	
	public static final String MONEY_FORMAT_POINT_TWO = "#.##";
	
	public static final int DEFAULT_EXCEPTION_MESSAGE_LENGTH = 480;
	
	
	public static final String ENCODE_CHARACTER_ISO = "ISO-8859-1";
	public static final String ENCODE_CHARACTER_GBK = "GBK";
	public static final String ENCODE_CHARACTER_UTF8 = "UTF-8";
	
	public class ChannelAccountConfigStatus {
		// 关闭
		public static final int CLOSED = 0;
		// 开启
		public static final int OPEN = 1;
	}
	
	public class BizsysAccountConfigStatus {
		// 关闭
		public static final int CLOSED = 0;
		// 开启
		public static final int OPEN = 1;
	}
	
	
	
	public class Fetchtype {
		// 获取第三方对账文件的方式 0=主动方式
		public static final int FETCH_TYPE_ACTIVE = 0;
		// 获取第三方对账文件的方式 1=推送方式
		public static final int FETCH_TYPE_PUSH = 1;
		// 获取第三方对账文件的方式 2=手动方式
		public static final int FETCH_TYPE_BY_HAND = 0;
	}
	
	
	public class ChannelAccountRequestStatus {
		// 第三方对账请求表 状态常量 0=初始化状态
		public static final int ACCOUNT_REQUEST_STATUS_INITIAL = 0;
		// 第三方对账请求表 状态常量 1=下载文件失败
		public static final int ACCOUNT_REQUEST_STATUS_DOWNLOAD_FILE_FAILED = 1;
		
		// 第三方对账请求表 状态常量 2=下载文件成功
		public static final int ACCOUNT_REQUEST_STATUS_DOWNLOAD_FILE_SUCCESS = 2;
		// 第三方对账请求表 状态常量 3=入对账明细表失败
		public static final int ACCOUNT_REQUEST_STATUS_INSERT_TABLE_FAILED = 3;
		// 第三方对账请求表 状态常量 4=入对账明细表成功
		public static final int ACCOUNT_REQUEST_STATUS_INSERT_TABLE_SUCCESS = 4;
		// 第三方对账请求表 状态常量 5=对账失败
		public static final int ACCOUNT_REQUEST_STATUS_ACCOUNT_FAILED = 5;
		// 第三方对账请求表 状态常量 6=对账成功
		public static final int ACCOUNT_REQUEST_STATUS_ACCOUNT_SUCCESS = 6;
		
		// 第三方对账请求表 状态常量 7=备份操作失败
		public static final int ACCOUNT_REQUEST_STATUS_BACKUP_FAILED = 7;
		// 第三方对账请求表 状态常量 8=备份操作成功
		public static final int ACCOUNT_REQUEST_STATUS_BACKUP_SUCCESS = 8;
	}
	
	public class HandleAccountStatus{
		// 手工对账状态 0=位处理
		public static final int HANDLE_ACCOUNT_STATUS_INITIAL = 0;
		// 手工对账状态 1=处理中
		public static final int HANDLE_ACCOUNT_STATUS_DEALING = 1;
		
		// 手工对账状态 2=处理失败
		public static final int HANDLE_ACCOUNT_STATUS_FAILED = 2;
		// 手工对账状态 3=处理成功
		public static final int HANDLE_ACCOUNT_STATUS_SUCCESS = 3;
	}
	
	// 第三方对账请求表 对账初始失败次数 0
	public static final int ACCOUNT_REQUEST_DOWNLOAD_FAILED_TIMES_INITIAL = 0;
	
	public class AccountInfo {
		// 对账流水表 与第三方对账状态 0=未对账
		public static final int ACCOUNT_INFO_ACCOUNT_STATUS_INITIAL = 0;
		// 对账流水表 与第三方对账状态 0=对账失败
		public static final int ACCOUNT_INFO_ACCOUNT_STATUS_FAILED = 1;
		// 对账流水表 与第三方对账状态 1=对账成功
		public static final int ACCOUNT_INFO_ACCOUNT_STATUS_SUCCESS = 2;
		
		// 对账流水表 与第三方对账状态 0=对账失败 失败原因 原始交易订单不存在
		public static final String ACCOUNT_INFO_ACCOUNT_FAILED_REASON_NO_ORIGINAL_DEAL = "原始交易订单不存在";
		// 对账流水表 与第三方对账状态 0=对账失败 失败原因 订单状态不匹配（第三方成功，我方失败）
		public static final String ACCOUNT_INFO_ACCOUNT_FAILED_REASON_DEAL_STATUS_NOT_MATCH = "订单状态不匹配（第三方成功，我方失败）";
		// 对账流水表 与第三方对账状态 0=对账失败 失败原因 交易金额不匹配
		public static final String ACCOUNT_INFO_ACCOUNT_FAILED_REASON_DEAL_AMOUNT_NOT_MATCH = "交易金额不匹配";

		// 对账流水表 与业务系统对账状态 0=未对账
		public static final int BIZSYS_ACCOUNT_INFO_ACCOUNT_STATUS_INITIAL = 0;
		// 对账流水表 与业务系统对账状态 1=已对账
		public static final int BIZSYS_ACCOUNT_INFO_ACCOUNT_STATUS_DONE = 1;
	}
	
	// 对账文件中使用的货币单位 0 =分
	public static final String CURRENCY_UNIT_FEN = "0";
	// 对账文件中使用的货币单位1 =元 
	public static final String CURRENCY_UNIT_YUAN = "1";
	
	public class ChannelFilter {
		
		public static final int FILTER_ANNOTATION_INITIAL = -1;
		
		public static final int FILTER_ANNOTATION_DOWNLOAD = 0;
		
		public static final int FILTER_ANNOTATION_INSERT_ACCOUNT_INFO = 1;
		
		public static final int FILTER_ANNOTATION_ACCOUNT = 2;
		
		public static final int FILTER_ANNOTATION_BACKUP= 3;
	}
	
	public class BizsysFilter {
		
		public static final int FILTER_ANNOTATION_INITIAL = -1;
		
		public static final int FILTER_ANNOTATION_GENERATE_FILE = 0;
		
		public static final int FILTER_ANNOTATION_PUSH_FILE = 1;
		
	}
	
	public class BizsysAccountRequestStatus {
		
		// 0 = 初始状态
		public static final int BIZSYS_ACCOUNT_REQUEST_STATUS_INITIAL = 0;
		// 1 = 保存至本地失败
		public static final int BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED = 1;
		// 2 = 保存至本地成功
		public static final int BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_SUCCESS = 2;
		// 3 = 推送失败		
		public static final int BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_FAILED = 3;
		// 4 = 推送成功
		public static final int BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_SUCCESS = 4;
	}
	
	public class BizType {
		// 居间人模式代收
		public static final String BROKER_COLLECT = "001";
		// 居间人模式代付
		public static final String BROKER_PAY = "002";
		// 资金托管融资
		public static final String TRUST_FINANCE = "003";
		// 资金托管还款
		public static final String TRUST_REFUND = "004";
		// 资金托管充值
		public static final String TRUST_RECHARGE = "008";
		// 资金托管提现
		public static final String TRUST_WITHDRWA = "009";
		// 线上充值
		public static final String ONLINE_RECHARGE = "011";
		// 线上提现
		public static final String ONLINE_WITHDRWA = "012";
		// 线上支付
		public static final String ONLINE_PAY = "013";
		// 线上退款
		public static final String ONLINE_REFUND = "014";
		
	}
}
