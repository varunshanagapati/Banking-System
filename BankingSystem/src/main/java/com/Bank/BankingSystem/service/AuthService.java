package com.Bank.BankingSystem.service;

import com.Bank.BankingSystem.dto.AdminLoginDTO;
import com.Bank.BankingSystem.dto.CustomerLoginDTO;
import com.Bank.BankingSystem.model.AdminEntity;
import com.Bank.BankingSystem.model.CustomerEntity;

public interface AuthService {
	
	public String addAdmin(AdminEntity ae);
	public String addCustomer(CustomerEntity ce);
	public boolean verifyAdmin(AdminLoginDTO adminDto);
	public boolean verifyCustomer(CustomerLoginDTO customerDto);
	public String getAdminName(int loginId);
	public String getCustomerName(int acNumber);
	
}
