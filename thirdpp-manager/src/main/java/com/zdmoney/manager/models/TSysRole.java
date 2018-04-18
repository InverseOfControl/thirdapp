package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zdmoney.manager.Validate.InsertCheck;

public class TSysRole implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5754743417515137691L;

	private Long id;
    
    @NotBlank(message = "角色名称为空", groups = InsertCheck.class)
	@Length(max = 50, message = "权限名称过长")
    private String roleName;

    private String creator;

    private String updator;

    private Date createTime;

    private Date updateTime;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;
    
    @NotBlank(message = "请选择是否有效", groups = InsertCheck.class)
	@Length(max = 2, message = "是否有效过长")
    private String isActive;
    
//    private List<TSysPermission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive == null ? null : isActive.trim();
    }

//	public List<TSysPermission> getPermissions() {
//		return permissions;
//	}
//
//	public void setPermissions(List<TSysPermission> permissions) {
//		this.permissions = permissions;
//	}
}