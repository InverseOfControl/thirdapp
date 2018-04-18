package com.zendaimoney.thirdpp.channel.util.shunionpay;



/**
 * @author mencius
 * 2015-7-3上午11:31:40
 * 
 */
public class ShunionpayConstants {
	/**上海银联代收请求 签名 属性名称数组,不包含purpose(用途)、chkValue(签名值)**/
	public static final String[] shunionpaycollectingSignPropertyNameArray={"merId","transDate","orderNo","transType","openBankId","cardType","cardNo","usrName","certType","certId","curyId","transAmt","priv1","version","gateId"};
	
	/**上海银联代收请求 发送 属性名称数组**/
	public static final String[] shunionpaycollectingSendPropertyNameArray={"merId","transDate","orderNo","transType","openBankId","cardType","cardNo","usrName","certType","certId","curyId","transAmt","purpose","priv1","version","gateId","chkValue"};
	
	/**上海银联代收请求查询 签名 属性名称数组,不包含priv1(商户私有域)、chkValue(签名值)**/
	public static final String[] shunionpaycollecting_qrytransSignPropertyNameArray={"merId","transType","orderNo","transDate","version"};
	
	/**上海银联代收请求查询 发送 属性名称数组**/
	public static final String[] shunionpaycollecting_qrytransSendPropertyNameArray={"merId","transType","orderNo","transDate","version","priv1","chkValue"};
	
	/**上海银联代付请求 签名 属性名称数组,不包含signFlag(签名标志)、chkValue(签名值)**/
	public static final String[] shunionpaypayingSignPropertyNameArray={"merId","merDate","merSeqId","cardNo","usrName","openBank","prov","city","transAmt","purpose","subBank","flag","version"};
	
	/**上海银联代付请求 发送 属性名称数组**/
	public static final String[] shunionpaypayingSendPropertyNameArray={"merId","merDate","merSeqId","cardNo","usrName","openBank","prov","city","transAmt","purpose","subBank","flag","version","signFlag","chkValue"};
	
	/**上海银联代付请求查询 签名 属性名称数组,不包含signFlag(签名标志)、chkValue(签名值)**/
	public static final String[] shunionpaypaying_qrytransSignPropertyNameArray={"merId","merDate","merSeqId","version"};
	
	/**上海银联代付请求查询 发送 属性名称数组**/
	public static final String[] shunionpaypaying_qrytransSendPropertyNameArray={"merId","merDate","merSeqId","version","signFlag","chkValue"};
	
	/**上海银联对应公钥PGID**/
	public static final String PGID="999999999999999";
	/**银联签名数据长度**/
	public static final int CHINAUNIONPAY_SIGN_DATA_LEN=256;
	
	public static final String CUT_STRING = "0";
	public static final String ALT_UNDERSCORE = "_";
	public static final String STYLE_COMMA = ",";
	public static final String STYLE_NEWLINE = "\r\n";
	public static final String STYLE_DECOLLATOR = "|";
	public static final String SPLIT_STYLE_DECOLLATOR = "\\|";
	
	public static final String FILE_POSTFIX=".txt";
	public static final String ZIP_FILE_POSTFIX=".zip";
	
	public static final String STYLE_COLON = ":";
	public static final int RECORD_SEQUENCE_NUMBER_LEN=2;
	//回盘汇总信息位于倒数第一行
	public static final int SUM_INFO_LINE_NUMBER = 1;
	//回盘汇总信息后签名信息长度
	public static final int SUM_INFO_SIGN_LENGTH = 256;
	//报盘数据分割大小
	public static final int OFFER_SPLIT_DATA_SIZE = 4000;
	public static final String THIRD_PARTY_TYPE = "4";
	public static final String REGEX_STYLE_UNDERLINE = "\\_";
	public static final String DATE_FORMAT_STYLE = "yyyyMMdd";
	public static final String MINUTE_FORMAT_STYLE = "HHmm";
	
	/**银联代收成功交易状态**/
	public static final String CHINAUNIONPAY_COLLECTION_SUCCESS_STATUS="1001";
	/**银联代收成功交易响应码**/
	public static final String CHINAUNIONPAY_COLLECTION_SUCCESS_CODE="00";
	/**银联代付成功交易状态**/
	public static final String CHINAUNIONPAY_PAYING_SUCCESS_STATUS="s";
	/**银联代付中间交易状态**/
	public static final String CHINAUNIONPAY_PAYING_UNKNOW_STATUS="2,3,4,5,7,8";
	/**银联代付失败交易状态**/
	public static final String CHINAUNIONPAY_PAYING_FAIL_STATUS="6,9";

	/**第三方返回结果信息
	 *	0 初始态
	 *	1 成功
	 *	2 超时
	 *	3 失败
	 *	4 错误( 请求没有问题 解析出问题) 
	 *	5  错误(请求没有问题 有失败记录)
	*/
	public static final String CHINA_UNIONPAYUS_THIRD_PARTY_REQUEST_STATUS_INITIAL ="0";
	public static final String CHINA_UNIONPAYUS__THIRD_PARTY_REQUEST_STATUS_SUCCESS ="1";
	public static final String CHINA_UNIONPAYUS__THIRD_PARTY_REQUEST_STATUS_TIMEOUT ="2";
	public static final String CHINA_UNIONPAYUS__THIRD_PARTY_REQUEST_STATUS_FAILURE ="3";
	public static final String CHINA_UNIONPAYUS__THIRD_PARTY_REQUEST_STATUS_ANALYSE_ERROR ="4";
	public static final String CHINA_UNIONPAYUS__THIRD_PARTY_REQUEST_STATUS_RECORDS_ERROR ="5";
	
	/**
	 *	0 报盘开始
	 *	1 报盘异常
	 *	2 报盘结束
	 *	3 回盘开始
	 *	4 回盘结束
	 *	5 回盘异常
	 */
	public static final String COLLECTION_PAYMENT_STATUS_OFFER_START ="0";
	public static final String COLLECTION_PAYMENT_STATUS_OFFER_EXCEPTION ="1";
	public static final String COLLECTION_PAYMENT_STATUS_OFFER_END ="2";
	public static final String COLLECTION_PAYMENT_STATUS_COUNTEROFFER_START ="3";
	public static final String COLLECTION_PAYMENT_STATUS_COUNTEROFFER_END ="4";
	public static final String COLLECTION_PAYMENT_STATUS_COUNTEROFFER_EXCEPTION ="5";
	
	/**处理结果信息
	 * 0 初始
	 * 1失败
	 * 2成功
	 */
	public static final String CHINA_UNIONPAYUS_OFFER_PROCESS_STATUS_INITIAL ="0";
	public static final String CHINA_UNIONPAYUS_OFFER_PROCESS_STATUS_FAILURE ="1";
	public static final String CHINA_UNIONPAYUS_OFFER_PROCESS_STATUS_SUCCESS ="2";
	
	//字段长度校验
	public static final int CANTON_UNIONPAY_MAXLENGTH = 20;
	//字段长度校验
	public static final int CHINA_UNIONPAY_MAXLENGTH = 25;
	
	/**
	 * 银联响应码信息
	 * 
	 * @author mencius
	 *
	 */
	public enum ShunionpayStatus {
		CODE_0000("0000", "提交成功"),
		
		CODE_0100("0100", "商户提交的字段长度、格式错误"),
		CODE_0101("0101", "商户验签错误"),
		CODE_0102("0102", "手续费计算出错"),
		CODE_0103("0103", "商户备付金帐户金额不足"),
		CODE_0104("0104", "操作拒绝"),
		CODE_0105("0105", "重复交易"),
		CODE_0000S("0000s", "交易成功"),
		CODE_00002("00002", "交易已接受"),
		CODE_00003("00003", "财务已确认"),
		CODE_00004("00004", "财务处理中"),
		CODE_00005("00005", "已发往银行"),
		CODE_00006("00006", "银行已退单"),
		CODE_00007("00007", "重汇已提交"),
		CODE_00008("00008", "重汇已发送"),
		CODE_00009("00009", "重汇已退单"),
		;

		private final String code;
		private final String desc;

		private ShunionpayStatus(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

		public static ShunionpayStatus get(String code) {
			for (ShunionpayStatus constants : ShunionpayStatus.values()) {
				if (constants.getCode().equals(code))
					return constants;
			}
			return null;
		}
	}
	
}
