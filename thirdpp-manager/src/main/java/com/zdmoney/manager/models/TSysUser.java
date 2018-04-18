package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zdmoney.manager.Validate.InsertCheck;

public class TSysUser  implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1188348910783807830L;

	private Long id;
    
    @NotBlank(message = "用户名不能为空", groups = InsertCheck.class)
	@Length(max = 50, message = "用户名过长")
    private String userName;

    @NotBlank(message = "登录名不能为空", groups = InsertCheck.class)
	@Length(max = 12, message = "登录名过长")
    private String loginUserName;

	@Length(max = 50, message = "密码过长")
    private String password;

    @NotBlank(message = "邮箱不能为空", groups = InsertCheck.class)
	@Length(max = 50, message = "邮箱过长")
    private String email;

    private Date createTime;

    private Date updateTime;
    
    @NotBlank(message = "请选择是否生效", groups = InsertCheck.class)
	@Length(max = 2, message = "是否生效过长")
    private String isActive;
    
    private String merchantName;

    private String merchantCode;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;

    private String phoneNo;

    private String creator;

    private String updator;
    
    private List<TSysRole> roles;
    
    private List<String> appIds;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName == null ? null : loginUserName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getResvFld1() {
        return resvFld1;
    }

    public void setResvFld1(String resvFld1) {
        this.resvFld1 = resvFld1 == null ? null : resvFld1.trim();
    }

    public String getResvFld2() {
        return resvFld2;
    }

    public void setResvFld2(String resvFld2) {
        this.resvFld2 = resvFld2 == null ? null : resvFld2.trim();
    }

    public String getResvFld3() {
        return resvFld3;
    }

    public void setResvFld3(String resvFld3) {
        this.resvFld3 = resvFld3 == null ? null : resvFld3.trim();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

	public List<TSysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TSysRole> roles) {
		this.roles = roles;
	}

	public List<String> getAppIds() {
		return appIds;
	}

	public void setAppIds(List<String> appIds) {
		this.appIds = appIds;
	}
}