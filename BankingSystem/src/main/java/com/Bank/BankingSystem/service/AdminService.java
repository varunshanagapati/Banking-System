package com.Bank.BankingSystem.service;

import com.Bank.BankingSystem.dto.AdminDTO;
import com.Bank.BankingSystem.dto.CustomerDTO;

public interface AdminService {

	public AdminDTO getAdmin(int loginId);

	public String updateCustomer(CustomerDTO cDto);

}
