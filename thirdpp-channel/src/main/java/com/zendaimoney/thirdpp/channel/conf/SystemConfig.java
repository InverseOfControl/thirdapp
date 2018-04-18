package com.zendaimoney.thirdpp.channel.conf;

import java.io.Serializable;

public class SystemConfig implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 938684883375092040L;

	
	//合并队列keyName
	private String merge_online_key;
	
	//合并队列keyName
	private String merge_offline_key;
	
	// #全局配置文件路径
	public static String globalConfigPath;
	
	public static String getGlobalConfigPath() {
		return globalConfigPath;
	}
	
	public static void setGlobalConfigPath(String globalConfigPath) {
		SystemConfig.globalConfigPath = globalConfigPath;
	}


	public String getMerge_online_key() {
		return merge_online_key;
	}
	
	public void setMerge_online_key(String merge_online_key) {
		this.merge_online_key = merge_online_key;
	}
	
	public String getMerge_offline_key() {
		return merge_offline_key;
	}
	
	public void setMerge_offline_key(String merge_offline_key) {
		this.merge_offline_key = merge_offline_key;
	}

	
}
