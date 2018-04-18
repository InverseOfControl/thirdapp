package com.zendaimoney.trust.channel.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.zendaimoney.trust.channel.conf.CmbConfig;
import com.zendaimoney.trust.channel.conf.FtpConfig;
import com.zendaimoney.trust.channel.conf.SystemConfig;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;


public class ConfigUtil implements Serializable {

	private static final long serialVersionUID = 8433608222120950801L;

	private static final LogPrn log = new LogPrn(ConfigUtil.class);

	// 系统模块配置
	private SystemConfig systemConfig;

	// 招商银行第三方配置
	private CmbConfig cmbConfig;
	
	// TPP2.0之FTP配置
	private FtpConfig tppFtpConfig;
	
	// 招行之FTP配置
	private FtpConfig cmbFtpConfig;

	private static ConfigUtil single = null;

	private ConfigUtil() {
		// 加载thirdpp.properties配置文件
		loadThirdppFile();
	}

	// 单例.
	public static ConfigUtil getInstance() {
		if (single == null) {
			single = new ConfigUtil();
		}
		return single;
	}

	/**
	 * 加载thirdpp.properties配置文件.
	 */
	public void loadThirdppFile() throws PlatformException {
		
		// 读取配置文件路径
		String path = "";
		try {
			if (StringUtils.isEmpty(System.getenv("TPPGLOBALCONFIGPATH"))) {
				
				path = Thread.currentThread().getContextClassLoader().getResource("conf/thirdpp-trust-channel.properties").getPath();
			} else {
				path = System.getenv("TPPGLOBALCONFIGPATH") + File.separator + "thirdpp-trust-channel.properties";
			}
			File thirdFile = new File(path);
			log.info("thirdpp-trust: " + path);
			Ini conf = new Ini(thirdFile);
			this.populateAttributes(conf);
			log.info("LoadThirdppFile sucessful");
		} catch (IOException e) {
			log.error("Parse thirdpp configuration failed,path=" + path, e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		} catch (PlatformException e) {
			throw e;
		} catch (Exception e) {
			log.error("Parse thirdpp configuration failed,path=" + path, e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}

	}

	/**
	 * 加载配置信息入口
	 * @param conf 配置文件信息流
	 * @throws PlatformException
	 */
	private void populateAttributes(Ini conf) throws PlatformException {
		
		// 系统配置
		this.populateSystemConf(conf);
		
		// 招商银行配置
		this.populateCmbConf(conf);
		
		// TPP2.0内部FTP配置
		this.populateFtpConfig(conf);
		
		// 招行之FTP配置
		this.populateCmbFtpConfig(conf);
		
	}

	/**
	 * 加载[system]系统配置信息
	 * @param conf
	 * @throws PlatformException
	 */
	@SuppressWarnings("static-access")
	private void populateSystemConf(Ini conf) throws PlatformException {
		Section section = conf.get("system");
		if (section != null) {
			systemConfig = new SystemConfig();
			systemConfig.setMerge_offline_key(section.get("merge_offline_key"));
			systemConfig.setMerge_online_key(section.get("merge_online_key"));
			
			// 设置全局变量
			log.info("TPPGLOBALCONFIGPATH: " + System.getenv("TPPGLOBALCONFIGPATH"));
			systemConfig.setGlobalConfigPath(System.getenv("TPPGLOBALCONFIGPATH"));
		}
	}
	
	/**
	 * 加载[cmb]招商银行配置信息
	 * @param conf
	 * @throws PlatformException
	 */
	private void populateCmbConf(Ini conf) throws PlatformException {
		Section section = conf.get("cmb");
		if (section != null) {
			cmbConfig = new CmbConfig();
			
			cmbConfig.setMacKey(section.get("MAC_KEY")); // 签名密钥
			
			cmbConfig.setTppCmbDownloadFilePath(section.get("TPP_CMB_DOWNLOAD_FILE_PATH")); // 招商银行资金托管本地下载文件存放路径
			cmbConfig.setTppCmbUploadFilePath(section.get("TPP_CMB_UPLOAD_FILE_PATH")); // 招商银行资金托管本地待上传文件存放路径
			cmbConfig.setCmbFlag(section.get("CMB_FLAG")); // 包标识/文件标识
			
			cmbConfig.setStructureFlag(section.get("STRUCTURE_FLAG")); // 文件结构标识
			cmbConfig.setCmbRequestFlag(section.get("CMB_REQUEST_FLAG")); // 请求标识
			cmbConfig.setCmbResponseFlag(section.get("CMB_RESPONSE_FLAG")); // 返回标识
			cmbConfig.setCmbFileType(section.get("CMB_FILE_TYPE")); // 扩展名 
		}
	}
	
	/**
	 * 加载[tpp_ftp]TPP2.0的系统内部FTP配置信息
	 * @param conf
	 */
	private void populateFtpConfig(Ini conf) {
		Section section = conf.get("tpp_ftp");
		if (section != null){
			
			tppFtpConfig = new FtpConfig();
			
			// 服务器地址
			tppFtpConfig.setFtpServer(section.get("ftp_server"));
			// 服务器端口
			tppFtpConfig.setFtpPort(section.get("ftp_port"));
			// 服务器登录名
			tppFtpConfig.setFtpUsername(section.get("ftp_username"));
			// 服务器密码
			tppFtpConfig.setFtpPwd(section.get("ftp_pwd"));
			// tpp上传下载存放FTP目录路径  + 通道编码
			tppFtpConfig.setFtpUploadPath(section.get("ftp_upload_path"));
			
			// tpp通道解析整理后上传存放FTP目录路径  + 通道编码
			tppFtpConfig.setFtpDownloadPath(section.get("ftp_download_path"));
		}
	}
	
	
	/**
	 * 加载[cmb_ftp]招行FTP配置信息
	 * @param conf
	 */
	private void populateCmbFtpConfig(Ini conf) {
		Section section = conf.get("cmb_ftp");
		if (section != null){
			
			cmbFtpConfig = new FtpConfig();
			
			// 服务器地址
			cmbFtpConfig.setFtpServer(section.get("ftp_server"));
			// 服务器端口
			cmbFtpConfig.setFtpPort(section.get("ftp_port"));
			// 服务器登录名
			cmbFtpConfig.setFtpUsername(section.get("ftp_username"));
			// 服务器密码
			cmbFtpConfig.setFtpPwd(section.get("ftp_pwd"));
			// tpp上传下载存放FTP目录路径  + 通道编码
			cmbFtpConfig.setFtpUploadPath(section.get("ftp_upload_path"));
			
			// tpp通道解析整理后上传存放FTP目录路径  + 通道编码
			cmbFtpConfig.setFtpDownloadPath(section.get("ftp_download_path"));
		}
	}


	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	public CmbConfig getCmbConfig() {
		return cmbConfig;
	}
	
	public FtpConfig getTppFtpConfig() {
		return tppFtpConfig;
	}
	
	
	public FtpConfig getCmbFtpConfig() {
		return cmbFtpConfig;
	}

	public static void main(String args[]) {

	}

}