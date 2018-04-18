package com.zendaimoney.thirdpp.channel.util;

import com.zendaimoney.thirdpp.common.vo.Response;

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
			if (!Constants.TppConstants.TRADE_STATE_FAILER.getCode().equals(
					response.getCode())
					&& !Constants.TppConstants.TRADE_STATE_MIDDLE.getCode()
							.equals(response.getCode())
					&& !Constants.TppConstants.TRADE_STATE_SUCCESS.getCode()
							.equals(response.getCode())) {
				response.setCode(Constants.TppConstants.TRADE_STATE_ABNORMAL
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
			if (!Constants.TppConstants.TRADE_STATE_FAILER.getCode().equals(
					response.getCode())
					&& !Constants.TppConstants.TRADE_STATE_SUCCESS.getCode()
							.equals(response.getCode())) {
				response.setCode(Constants.TppConstants.TRADE_STATE_MIDDLE
						.getCode());
			}
		}
		return response;
	}

}
