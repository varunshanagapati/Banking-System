package com.Bank.BankingSystem.dto;

public class AdminDTO {
	
	private int loginId;
	private String name;
	private String email;
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "AdminDTO [loginId=" + loginId + ", name=" + name + ", email=" + email + "]";
	}

}
