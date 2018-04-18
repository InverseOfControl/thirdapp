package websvc.models;


public class Model_500001 {
	
	private String projectNo;
	
	private String pshType;
	
	private String phoneNo;
	
	private String sn;

	private String type;
	
	private long reqTimestamp;
	
	private String templateId;
	
	private String paramJson;
	
	private String text1;
	
	private String text2;
	
	private String text3;
	
	public String getPshType() {
		return pshType;
	}

	public void setPshType(String pshType) {
		this.pshType = pshType;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getParamJson() {
		return paramJson;
	}

	public void setParamJson(String paramJson) {
		this.paramJson = paramJson;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	private String sign;

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public long getReqTimestamp() {
		return reqTimestamp;
	}

	public void setReqTimestamp(long reqTimestamp) {
		this.reqTimestamp = reqTimestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
}
