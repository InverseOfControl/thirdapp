package com.zendaimoney.thirdpp.channel.conf.allinpay;

/**
 * 访问支付平台证书信息(包括证书路径、密码)
 * @author 00231257
 *
 */
public class CertificationInfo {
	
	/**
	 * 证书路径
	 */
    private String certPath;
    
    /**
     * 证书密码 
     */
    private String certPwd;
    
    public CertificationInfo(String certPath,String certPwd){
    	this.certPath = certPath;
    	this.certPwd = certPwd;
    } 

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getCertPwd() {
		return certPwd;
	}

	public void setCertPwd(String certPwd) {
		this.certPwd = certPwd;
	}
    
    
    

}
