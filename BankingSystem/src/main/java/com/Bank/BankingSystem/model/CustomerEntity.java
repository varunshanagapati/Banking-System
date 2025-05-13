package com.Bank.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
	    name = "customer",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = "accountNumber"),
	        @UniqueConstraint(columnNames = "customerEmail")
	    }
	)
public class CustomerEntity {

	@Id
	private int accountNumber;
	
	private String Name;
	private String Email;
	private String AcType;
	private Long PhoneNumber;
	private double Balance;
	private String Status;
	private String Password;
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getAcType() {
		return AcType;
	}
	public void setAcType(String acType) {
		AcType = acType;
	}
	public Long getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public double getBalance() {
		return Balance;
	}
	public void setBalance(double balance) {
		Balance = balance;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	@Override
	public String toString() {
		return "CustomerEntity [accountNumber=" + accountNumber + ", Name=" + Name + ", Email=" + Email + ", AcType="
				+ AcType + ", PhoneNumber=" + PhoneNumber + ", Balance=" + Balance + ", Status=" + Status
				+ ", Password=" + Password + "]";
	}
	
	
	
}
