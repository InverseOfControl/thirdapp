package websvc.models;

import java.util.Date;

/** 
 *
 * @author 渡心
 * @version 2015年3月24日 下午4:19:09 
 */
public class Model_400001 {

	private String projectNo;
	
	private String userEmailAddress;
	
	private String subject;
	
	private String content;
	
	private long reqTimestamp;
	
	private String sn;
	
	private String sendTime;
	
	private String pshType;
	
	private String sign;
	
	private String attachmentPaths;
	
	private String emailType;
	
	private String maxCount;

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getUserEmailAddress() {
		return userEmailAddress;
	}

	public void setUserEmailAddress(String userEmailAddress) {
		this.userEmailAddress = userEmailAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getReqTimestamp() {
		return reqTimestamp;
	}

	public void setReqTimestamp(long reqTimestamp) {
		this.reqTimestamp = reqTimestamp;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAttachmentPaths() {
		return attachmentPaths;
	}

	public void setAttachmentPaths(String attachmentPaths) {
		this.attachmentPaths = attachmentPaths;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public String getPshType() {
		return pshType;
	}

	public void setPshType(String pshType) {
		this.pshType = pshType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
