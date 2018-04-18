package com.zendaimoney.thirdpp.query.util;

import java.util.Calendar;
import java.util.Random;

public class IDGenerateUtil {

	/**
	 * 生成TPP通道請求ID
	 * 
	 * @return
	 */
	public static String createReqId() {
		// 生成ReqId用戶表示TPP_CHANNEL_T_REQUEST表主鍵，yyyyMMddhhmiss+6位隨機數
		StringBuffer reqId = new StringBuffer();
		// 1，先生时间戳
		String prefix = CalendarUtils.parsefomatCalendar(
				Calendar.getInstance(), CalendarUtils.LONG_FORMAT);
		// 2,生成6位随机数
		String random = generateRandom(6);
		// 3，组装生成的reqId
		return reqId.append(prefix).append(random).toString();

	}

	/**
	 * 生成交易流水号
	 * 
	 * @return
	 */
	public static String createTradeFlow(String bizType) {
		// 生成交易流水号.bizType(3位) + ddhhmiss(8位) + 5位隨機數
		StringBuffer tradeFlow = new StringBuffer();
		// 1，先生时间戳
		String prefix = CalendarUtils.parsefomatCalendar(
				Calendar.getInstance(), CalendarUtils.SHORT_FORMAT_DAY);
		// 2,生成5位随机数
		String random = generateRandom(5);
		// 3，组装生成的tradeFlow
		return tradeFlow.append(bizType).append(prefix).append(random)
				.toString();
	}

	/**
	 * 生成多少位随机数
	 * 
	 * @param bit
	 * @return
	 */
	private static String generateRandom(int bit) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < bit; i++) {
			sb.append(r.nextInt(9));
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		System.out.println(createReqId());
	}
}
