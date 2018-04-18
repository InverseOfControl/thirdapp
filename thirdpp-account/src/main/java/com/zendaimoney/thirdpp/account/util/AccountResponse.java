package com.zendaimoney.thirdpp.account.util;

import java.io.Serializable;

public class AccountResponse implements Serializable {

	private static final long serialVersionUID = 3064141784628012318L;

	public static final int ACCOUNT_RESPONSE_STATUS_SUCCESS = 1;

	public static final int ACCOUNT_RESPONSE_STATUS_FAILED = 0;

	public static final int ACCOUNT_RESPONSE_MESSAGEID_DEFAULT = 8888;

	// 对账状态 0=失败，1=成功
	private int status;

	private int messageId;

	private String messageContent;

	private long sleepTime = 0l;

	public AccountResponse(int status, int messageId) {
		this.status = status;
		this.messageId = messageId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public enum CommonMessage {
		CONFIG_NOT_START(0000, "当前时间小于对账配置表中配置的对账时间"),
		SQL_EXCEPTION(1111, "数据库异常"),
		PLATFORM_EXCEPTION(2222, "运行时异常"),
		EXCEPTION(3333, "普通异常"),
		THIRD_PARTY_ACCOUNT_NOT_FINISHED(4444, "业务类型涉及的第三方对账尚未完成"),
		THIRD_PARTY_ACCOUNT_NO_ACCOUNT_TIME(5555, "业务系统对账未配置对账时间"),
		FINISHED(8888, "对账已经完成");
		private int mId;
		private String mContent;

		private CommonMessage(int mId, String mContent) {
			this.mId = mId;
			this.mContent = mContent;
		}

		public int getmId() {
			return mId;
		}

		public void setmId(int mId) {
			this.mId = mId;
		}

		public String getmContent() {
			return mContent;
		}

		public void setmContent(String mContent) {
			this.mContent = mContent;
		}

	}
}
