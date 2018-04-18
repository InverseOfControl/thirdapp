package com.zendaimoney.trust.channel.util;

import com.zendaimoney.thirdpp.common.vo.Response;

/**
 * 响应状态转换工具类
 * @author mencius
 *
 */
public class ResponseUtil {

	/**
	 * 将非交易成功、交易失败、交易处理中三种状态统一转化成交易异常状态
	 * 
	 * @param r
	 * @return
	 */
	public static Response convert2UniqeErrorcode(Response response) {
		if (response != null) {
			//如果不是交易成功、交易失败、交易处理中三种状态，则统一转化成交易异常状态
			if (!Constants.CmbConstants.TRADE_STATE_FAILER.getCode().equals(
					response.getCode())
					&& !Constants.CmbConstants.TRADE_STATE_MIDDLE.getCode()
							.equals(response.getCode())
					&& !Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode()
							.equals(response.getCode())) {
				response.setCode(Constants.CmbConstants.TRADE_STATE_ABNORMAL
						.getCode());
			}
		}
		return response;
	}
	
	/**
	 * 将非交易成功、交易失败两种状态统一转化成交易处理中状态
	 * 
	 * @param r
	 * @return
	 */
	public static Response convert2UniqeMidCode(Response response) {
		if (response != null) {
			//将非交易成功、交易失败两种状态统一转化成交易处理中状态
			if (!Constants.CmbConstants.TRADE_STATE_FAILER.getCode().equals(
					response.getCode())
					&& !Constants.CmbConstants.TRADE_STATE_SUCCESS.getCode()
							.equals(response.getCode())) {
				response.setCode(Constants.CmbConstants.TRADE_STATE_MIDDLE
						.getCode());
			}
		}
		return response;
	}

}
