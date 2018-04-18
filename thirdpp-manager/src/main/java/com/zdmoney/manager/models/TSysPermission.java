package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zdmoney.manager.Validate.InsertCheck;

public class TSysPermission implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7033310961591406781L;

	private Long id;

    @NotBlank(message = "权限名称为空", groups = InsertCheck.class)
	@Length(max = 100, message = "权限名称过长")
    private String permName;

    @NotBlank(message = "权限url为空", groups = InsertCheck.class)
	@Length(max = 100, message = "权限url过长")
    private String permUrl;
    
    @NotBlank(message = "权限类型为空", groups = InsertCheck.class)
	@Length(max = 2, message = "权限类型过长")
    private String permType;
    
    @Length(max = 10, message = "菜单位置过长")
    private String position;

    private Long parentId;
    
    private String parentName;

    private String creator;

    private String updator;

    private Date createTime;

    private Date updateTime;

    private String resvFld1;

    private String resvFld2;

    private String resvFld3;
    
    private String isOwn;  //当前角色是否拥有此权限:1表示拥有
    
    private List<TSysPermission> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName == null ? null : permName.trim();
    }

    public String getPermUrl() {
        return permUrl;
    }

    public void setPermUrl(String permUrl) {
        this.permUrl = permUrl == null ? null : permUrl.trim();
    }

    public String getPermType() {
        return permType;
    }

    public void setPermType(String permType) {
        this.permType = permType == null ? null : permType.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public String getIsOwn() {
		return isOwn;
	}

	public void setIsOwn(String isOwn) {
		this.isOwn = isOwn;
	}

	public List<TSysPermission> getChildren() {
		return children;
	}

	public void setChildren(List<TSysPermission> children) {
		this.children = children;
	}
    
}