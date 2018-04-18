package com.zendaimoney.thirdpp.transfer.conf;

import com.zendaimoney.thirdpp.common.enums.ThirdType;

/**
 * 通联配置文件
 * 
 * @author 00231257
 *
 */
public class ChannelConfig {

	

	// 启动线程数
	private int numThread;

	// 第三方通道编码
	private ThirdType thirdType;
	
	

	public int getNumThread() {
		return numThread;
	}

	public void setNumThread(int numThread) {
		this.numThread = numThread;
	}

	public ThirdType getThirdType() {
		return thirdType;
	}

	public void setThirdType(ThirdType thirdType) {
		this.thirdType = thirdType;
	}

	

	

}
