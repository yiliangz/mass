package org.mass.framework.org.model;import java.util.Date;import org.mass.framework.core.model.BaseModel;public class AppUserModel extends BaseModel {		private Integer userId;//   	private String login;//   	private String loginPassword;//   	private String userCode;//   	private String userMobile;//   	private Integer refId;//   	private String refType;//   	private Date createTime;//   	public Integer getUserId() {	    return this.userId;	}	public void setUserId(Integer userId) {	    this.userId=userId;	}	public String getLogin() {	    return this.login;	}	public void setLogin(String login) {	    this.login=login;	}	public String getLoginPassword() {	    return this.loginPassword;	}	public void setLoginPassword(String loginPassword) {	    this.loginPassword=loginPassword;	}	public String getUserCode() {	    return this.userCode;	}	public void setUserCode(String userCode) {	    this.userCode=userCode;	}	public String getUserMobile() {	    return this.userMobile;	}	public void setUserMobile(String userMobile) {	    this.userMobile=userMobile;	}	public Integer getRefId() {	    return this.refId;	}	public void setRefId(Integer refId) {	    this.refId=refId;	}	public String getRefType() {	    return this.refType;	}	public void setRefType(String refType) {	    this.refType=refType;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	@Override	public String toString() {		return "AppUser[userId=" + this.userId + ",login=" + this.login + ",loginPassword=" + this.loginPassword		 + ",userCode=" + this.userCode + ",userMobile=" + this.userMobile + ",refId=" + this.refId		 + ",refType=" + this.refType + ",createTime=" + this.createTime + ",]";	}}