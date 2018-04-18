package com.zendaimoney.thirdpp.channel.conf.allinpay;


/**
 * 访问支付平台用户信息(包括用户名、密码)
 * @author 00231257
 *
 */
public class UserInfoConfig {
	
	
	/**
	 * 用户名
	 */
	private String userName;
	
	
	//用户密码
	private String userPwd;
	

	public UserInfoConfig(String userName,String userPwd){
		this.userName = userName;
		this.userPwd = userPwd;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserPwd() {
		return userPwd;
	}


	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	
	

}
