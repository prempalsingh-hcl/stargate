package com.hcl.stargate.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stargate_user")
public class StargateUser {
	@Id
	private String username;
	private String password;
	private String Group;
	private String isExternal;
	private String isSREUser;
	public String getGroup() {
		return Group;
	}
	public void setGroup(String group) {
		Group = group;
	}
	public String getIsExternal() {
		return isExternal;
	}
	public void setIsExternal(String isExternal) {
		this.isExternal = isExternal;
	}
	public String getIsSREUser() {
		return isSREUser;
	}
	public void setIsSREUser(String isSREUser) {
		this.isSREUser = isSREUser;
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

}
