package com.zendaimoney.thirdpp.alarm.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.zendaimoney.thirdpp.alarm.common.exception.CsswebException;

public class ServerConfig implements Serializable {

	private static final long serialVersionUID = 8433608222120950801L;
	public static Log logger = LogFactory.getLog(ServerConfig.class);

	private String appName;
	private String name;
	private String alarmMobiles;
	private String alarmMails;
	private Integer sleepTime;
	private Integer errorSleepTime;

	private String host;
	private Integer port;
	private String username;
	private String password;
	private String display;
	private String displayName;
	private Boolean auth;
	private Boolean debug;
	private MailConfig mailConfig;

	private TytxServerConfig tytxServerConfig;
	private String sysId;
	private String empId;
	private String mType;
	private String encoding;

	public ServerConfig() {
	}

	public void loadFromFile(String path) throws CsswebException {
		try {
			File file = new File(path);
			if (!file.exists()) {
				logger.error("File " + path + " is not exists");
				throw new CsswebException("File " + path + " is not exists");
			}
			Ini conf = new Ini(file);
			this.populateAttributes(conf);
			logger.info("Load file " + path + " sucessful");
		} catch (IOException e) {
			logger.error("Parse configuration failed,path=" + path);
			throw new CsswebException(
					"Parse configuration failed,path=" + path, e);
		} catch (Exception e) {
			logger.error("Parse configuration failed,path=" + path);
			throw new CsswebException(
					"Parse configuration failed,path=" + path, e);
		}

	}

	private void populateAttributes(Ini conf) {
		this.populateSystemConf(conf);
		this.populateMailConf(conf);
		this.populateTytxServerConf(conf);
	}

	private void populateSystemConf(Ini conf) {
		Section systemConf = conf.get("system");
		this.appName = systemConf.get("appName");
		this.name = systemConf.get("name");
		this.sleepTime = getInt(systemConf, "sleepTime");
		this.errorSleepTime = getInt(systemConf, "errorSleepTime");
		this.alarmMobiles = systemConf.get("alarmMobiles");
		this.alarmMails = systemConf.get("alarmMails");
		StringBuffer buffer = new StringBuffer();
		buffer.append("[appName=" + this.appName + ",name=" + this.name
				+ ",alarmMobile=" + this.alarmMobiles + ",alarmMails="
				+ this.alarmMails + "]");
		logger.info(buffer.toString());
	}

	private void populateMailConf(Ini conf) {
		Section mailConf = conf.get("mail");
		if (mailConf != null) {
			this.host = mailConf.get("mail.host");
			this.port = this.getInt(mailConf, "mail.port");
			this.username = mailConf.get("mail.username");
			this.password = mailConf.get("mail.password");
			this.display = mailConf.get("mail.display");
			this.displayName = mailConf.get("mail.displayName");
			this.auth = this.getBoolean(mailConf, "mail.auth");
			this.debug = this.getBoolean(mailConf, "mail.debug");
			mailConfig = new MailConfig(host, port, username, password,
					display, displayName, auth, debug);
			logger.info(new StringBuffer().append("[host=").append(this.host)
					.append(",port=)").append(this.port).append(",username=")
					.append(this.username).append(",password=")
					.append(this.password).append(",display=)")
					.append(this.password).append(",displayName=)")
					.append(this.displayName).append(",auth=)")
					.append(this.auth).append(",debug=)").append(this.debug)
					.append("]").toString());
		}
	}

	private void populateTytxServerConf(Ini conf) {
		Section tytxServerConf = conf.get("tytx");
		if (tytxServerConf != null) {
			this.sysId = tytxServerConf.get("sysId");
			this.empId = tytxServerConf.get("empId");
			this.mType = tytxServerConf.get("mType");
			this.encoding = tytxServerConf.get("encoding");

			tytxServerConfig = new TytxServerConfig(sysId, empId,
					mType, encoding);
			StringBuffer buffer = new StringBuffer();
			buffer.append("[sysId=").append(this.sysId)
					.append(",empId=").append(this.empId)
					.append(",mType=").append(this.mType)
					.append(",encoding=").append(this.encoding).append("]");
			logger.info(buffer.toString());
		}
	}

	private int getInt(Section section, String key) {
		String value = section.get(key);
		if (StringUtils.isBlank(value)) {
			throw new CsswebException("Blank value for " + key);
		} else {
			Integer rt = (Integer) Integer.parseInt(value);
			return rt.intValue();
		}
	}

	private boolean getBoolean(Section section, String key)
			throws CsswebException {
		String value = section.get(key);
		if (StringUtils.isBlank(value)) {
			throw new CsswebException("Blank value for " + key);
		} else {
			Boolean obj = (Boolean) Boolean.parseBoolean(value);
			return obj.booleanValue();
		}
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MailConfig getMailConfig() {
		return mailConfig;
	}

	public void setMailConfig(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	public String getAlarmMobiles() {
		return alarmMobiles;
	}

	public void setAlarmMobiles(String alarmMobiles) {
		this.alarmMobiles = alarmMobiles;
	}

	public String getAlarmMails() {
		return alarmMails;
	}

	public void setAlarmMails(String alarmMails) {
		this.alarmMails = alarmMails;
	}

	public Integer getSleepTime() {
		return sleepTime;
	}

	public Integer getErrorSleepTime() {
		return errorSleepTime;
	}

	public TytxServerConfig getTytxServerConfig() {
		return tytxServerConfig;
	}

	public void setTytxServerConfig(TytxServerConfig tytxServerConfig) {
		this.tytxServerConfig = tytxServerConfig;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getAuth() {
		return auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setErrorSleepTime(Integer errorSleepTime) {
		this.errorSleepTime = errorSleepTime;
	}

	
}