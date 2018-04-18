package com.zendaimoney.thirdpp.channel.util;

import com.zendaimoney.thirdpp.channel.communication.Message;

public class DoshBoardUtil {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(DoshBoardUtil.class);
	
	/**
	 * 挡板设置(压测) 通联代付
	 * @param message
	 */
	public static void AllPaydoshBoard (Message message) {
		
		message.setHttpStatusCode("200");
		
		StringBuffer responseXml = new StringBuffer();
		responseXml.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		responseXml.append("<AIPG><INFO>");
		responseXml.append("<TRX_CODE>100011</TRX_CODE>");
		responseXml.append("<VERSION>03</VERSION>");
		responseXml.append("<DATA_TYPE>2</DATA_TYPE>");
		responseXml.append("<REQ_SN>" + message.getTradeSn() + "</REQ_SN>");
		responseXml.append("<RET_CODE>0000</RET_CODE>");
		responseXml.append("<ERR_MSG>处理成功</ERR_MSG>");
		responseXml.append("</INFO><TRANSRET>");
		responseXml.append("<RET_CODE>0000</RET_CODE>");
		responseXml.append("<SETTLE_DAY>" + CalendarUtils.getShortFormatNow() + "</SETTLE_DAY>");
		responseXml.append("<ERR_MSG>处理成功</ERR_MSG>");
		responseXml.append("</TRANSRET></AIPG>");
		
		logger.info("通联代付挡板：" + responseXml);
		// 模拟第三方响应成功结果
		message.setResponseInfo(responseXml.toString());
	}
	
	/**
	 * 挡板设置(压测) 通联查询
	 * @param message
	 */
	public static void AllPayQuerydoshBoard(Message message) {
		
		message.setHttpStatusCode("200");
		
		StringBuffer responseXml = new StringBuffer();
		responseXml.append("<?xml version=\"1.0\" encoding=\"GBK\"?><AIPG>");
		responseXml.append("<INFO>");
		responseXml.append("<TRX_CODE>200004</TRX_CODE>");
		responseXml.append("<VERSION>03</VERSION>");
		responseXml.append("<DATA_TYPE>2</DATA_TYPE>");
		responseXml.append("<REQ_SN>" + message.getTradeSn() + "</REQ_SN>");
		responseXml.append("<RET_CODE>0000</RET_CODE>");
		responseXml.append("<ERR_MSG>处理成功</ERR_MSG>");
		responseXml.append("</INFO><QTRANSRSP>");
		responseXml.append("<QTDETAIL>");
		responseXml.append("<BATCHID>" + message.getTradeSn() + "</BATCHID>");
		responseXml.append("<SN>0</SN>");
		responseXml.append("<TRXDIR>1</TRXDIR>");
		responseXml.append("<SETTLE_DAY>" + CalendarUtils.getShortFormatNow() + "</SETTLE_DAY>");
		responseXml.append("<FINTIME>"+ CalendarUtils.getFormatNow() + "</FINTIME>");
		responseXml.append("<SUBMITTIME>"+ CalendarUtils.getFormatNow() + "</SUBMITTIME>");
		responseXml.append("<ACCOUNT_NO>9558801001114990713</ACCOUNT_NO>");
		responseXml.append("<ACCOUNT_NAME>代付</ACCOUNT_NAME>");
		responseXml.append("<AMOUNT>111100</AMOUNT>");
		responseXml.append("<ERR_MSG>处理成功</ERR_MSG>");
		responseXml.append("<RET_CODE>0000</RET_CODE>");
		responseXml.append("</QTDETAIL>");
		responseXml.append("</QTRANSRSP></AIPG>");
		
		logger.info("通联代付挡板：" + responseXml);
		// 模拟第三方响应成功结果
		message.setResponseInfo(responseXml.toString());
	}
	
}
