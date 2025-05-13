package com.Bank.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
	    name = "admin",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = "adminId"),
	        @UniqueConstraint(columnNames = "adminEmail")
	    }
	)
public class AdminEntity {

	@Id
	private int adminId;
	private String adminName;
	private String adminEmail;
	private String AdminPassword;
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminPassword() {
		return AdminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		AdminPassword = adminPassword;
	}
	@Override
	public String toString() {
		return "AdminEntity [adminId=" + adminId + ", adminName=" + adminName + ", adminEmail=" + adminEmail
				+ ", AdminPassword=" + AdminPassword + "]";
	}
	
}
