package com.zendaimoney.trust.channel.conf;

import java.io.Serializable;

/**
 * 招商银行通道配置
 * @author mencius
 *
 */
public class CmbConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	// 签名私钥
	private String macKey;
	
	// 招商银行资金托管本地下载文件存放路径
	private String tppCmbDownloadFilePath;
	
	// 招商银行资金托管本地待上传文件存放路径
	private String tppCmbUploadFilePath;
	
	// 包标识/文件标识
	private String cmbFlag;
	
	// 文件结构标识
	private String structureFlag;
	
	// 请求标识
	private String cmbRequestFlag;
	
	// 返回标识
	private String cmbResponseFlag;
	
	// 文件扩展名
	private String cmbFileType;
	
	
	public static CmbConfig cmbConfig;
	
	public String getMacKey() {
		return macKey;
	}
	
	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}
	
	public CmbConfig getCmbConfig() {
		
		if (cmbConfig == null) {
			cmbConfig = new CmbConfig();
		}
		return cmbConfig;
	}
	
	public static void setCmbConfig(CmbConfig cmbConfig) {
		CmbConfig.cmbConfig = cmbConfig;
	}

	public String getTppCmbDownloadFilePath() {
		return tppCmbDownloadFilePath;
	}

	public void setTppCmbDownloadFilePath(String tppCmbDownloadFilePath) {
		this.tppCmbDownloadFilePath = tppCmbDownloadFilePath;
	}

	public String getTppCmbUploadFilePath() {
		return tppCmbUploadFilePath;
	}

	public void setTppCmbUploadFilePath(String tppCmbUploadFilePath) {
		this.tppCmbUploadFilePath = tppCmbUploadFilePath;
	}

	public String getCmbFlag() {
		return cmbFlag;
	}

	public void setCmbFlag(String cmbFlag) {
		this.cmbFlag = cmbFlag;
	}

	public String getStructureFlag() {
		return structureFlag;
	}

	public void setStructureFlag(String structureFlag) {
		this.structureFlag = structureFlag;
	}

	public String getCmbRequestFlag() {
		return cmbRequestFlag;
	}

	public void setCmbRequestFlag(String cmbRequestFlag) {
		this.cmbRequestFlag = cmbRequestFlag;
	}

	public String getCmbResponseFlag() {
		return cmbResponseFlag;
	}

	public void setCmbResponseFlag(String cmbResponseFlag) {
		this.cmbResponseFlag = cmbResponseFlag;
	}

	public String getCmbFileType() {
		return cmbFileType;
	}

	public void setCmbFileType(String cmbFileType) {
		this.cmbFileType = cmbFileType;
	}
	
}
