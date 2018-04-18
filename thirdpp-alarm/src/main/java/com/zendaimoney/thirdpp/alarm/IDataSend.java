package com.zendaimoney.thirdpp.alarm;

import com.zendaimoney.thirdpp.alarm.common.exception.CsswebException;

public interface IDataSend {


	/**
	 * 从共享区获取一条消息,主要是短信和彩信
	 * 
	 * @return json格式的字符串
	 * @throws CsswebException
	 */
	public Object receive() throws CsswebException;
}
