package com.Bank.BankingSystem.dto;

public class CustomerDTO {
	
	private int accountNumber;
	private String AcType;
	private double balance;
	private String email;
	private String name;
	private Long phoneNumber;
	private String status;
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAcType() {
		return AcType;
	}
	public void setAcType(String acType) {
		AcType = acType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CustomerDTO [accountNumber=" + accountNumber + ", AcType=" + AcType + ", balance=" + balance
				+ ", email=" + email + ", name=" + name + ", phoneNumber=" + phoneNumber + ", status=" + status + "]";
	}
	

}
