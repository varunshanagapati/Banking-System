package com.Bank.BankingSystem.service;

import java.util.List;

import com.Bank.BankingSystem.dto.CustomerDTO;
import com.Bank.BankingSystem.model.TransactionEntity;

public interface CustomerService {

	public CustomerDTO getCustomer(int acNumber);

	public String depositOperation(double depositAmount, int acNumber);

	public String withdrawOperation(double withdrawAmount, int acNumber);

	public String transferOperation(int fromAcNumber, int toAcNumber, double amount);
	
	public List<TransactionEntity> getAllTransactions(int acNumber);

	public List<CustomerDTO> getAllAccounts();

	public String removeCustomer(int acNumber);
}
