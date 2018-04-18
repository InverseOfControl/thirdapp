package com.zendaimoney.thirdpp.alarm.util;

import java.io.Serializable;

public class MailConfig implements Serializable {

	private static final long serialVersionUID = 8433608222120950801L;

	private String host;
	private Integer port;
	private String username;
	private String password;
	private String display;
	private String displayName;
	private Boolean auth;
	private Boolean debug;

	public MailConfig() {

	}

	public MailConfig(String host, Integer port, String username,
			String password, String display, String displayName, Boolean auth,
			Boolean debug) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.display = display;
		this.displayName = displayName;
		this.auth = auth;
		this.debug = debug;
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


}