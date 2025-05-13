package com.Bank.BankingSystem.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bank.BankingSystem.dto.CustomerDTO;
import com.Bank.BankingSystem.model.CustomerEntity;
import com.Bank.BankingSystem.model.TransactionEntity;
import com.Bank.BankingSystem.repository.CustomerRepository;
import com.Bank.BankingSystem.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService
{
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private TransactionRepository transRepo;

	@Override
	public CustomerDTO getCustomer(int acNumber) {
		// TODO Auto-generated method stub
		CustomerEntity ce = customerRepo.findById(acNumber).get();
		CustomerDTO cDto= new CustomerDTO();
		cDto.setAccountNumber(ce.getAccountNumber());
		cDto.setAcType(ce.getAcType());
		cDto.setBalance(ce.getBalance());
		cDto.setEmail(ce.getEmail());
		cDto.setName(ce.getName());
		cDto.setPhoneNumber(ce.getPhoneNumber());
		cDto.setStatus(ce.getStatus());
		
		
		
		return cDto;
		
		
	}

	@Override
	public String depositOperation(double depositAmount, int acNumber) {
		
		try {
			
			double currentBalance=customerRepo.findById(acNumber).get().getBalance();
			CustomerEntity ce = customerRepo.findById(acNumber).get();
			double result=currentBalance+depositAmount;
			ce.setBalance(result);
			customerRepo.save(ce);
			TransactionEntity te=new TransactionEntity();
			te.setAccountNumber(ce.getAccountNumber());
			te.setAmount(depositAmount);
			LocalDateTime currentDateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDateTime = currentDateTime.format(formatter);
			te.setDate(formattedDateTime);
			te.setFromAccount(0);
			te.setToAccount(acNumber);
			te.setTransactionType("Deposit");
			transRepo.save(te);
			
			return "Deposit Successfull";
		}catch(Exception e) {
			System.out.println(e);
			return "Something went Wrong!";
		}
		
	}

	@Override
	public String withdrawOperation(double withdrawAmount, int acNumber) {
		// TODO Auto-generated method stub
		try {
			
			double currentBalance=customerRepo.findById(acNumber).get().getBalance();
			CustomerEntity ce = customerRepo.findById(acNumber).get();
			double result=currentBalance-withdrawAmount;
			ce.setBalance(result);
			customerRepo.save(ce);
			TransactionEntity te=new TransactionEntity();
			te.setAccountNumber(ce.getAccountNumber());
			te.setAmount(withdrawAmount);
			LocalDateTime currentDateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDateTime = currentDateTime.format(formatter);
			te.setDate(formattedDateTime);
			te.setFromAccount(ce.getAccountNumber());
			te.setToAccount(0);
			te.setTransactionType("Withdraw");
			transRepo.save(te);
			
			return "Withdraw Successfull";

		}catch(Exception e) {
			System.out.println(e);
			return "Something Went Wrong!";
		}
	}

	@Override
	public String transferOperation(int fromAcNumber, int toAcNumber, double amount) {
		
		Optional<CustomerEntity> fromOptional=customerRepo.findById(fromAcNumber);
		Optional<CustomerEntity> toOptional=customerRepo.findById(toAcNumber);
		
		
		if(fromOptional.isPresent() && toOptional.isPresent()) {
			
		
			CustomerEntity fromAc = customerRepo.findById(fromAcNumber).get();
			double currentFromBalance=customerRepo.findById(fromAcNumber).get().getBalance();
			CustomerEntity toAc = customerRepo.findById(toAcNumber).get();
			double currentToBalance=customerRepo.findById(toAcNumber).get().getBalance();
			
			double fromAcBalance=currentFromBalance-amount;
			double toAcBalance=currentToBalance+amount;
			fromAc.setBalance(fromAcBalance);
			toAc.setBalance(toAcBalance);
			
			customerRepo.save(fromAc);
			customerRepo.save(toAc);
			
			TransactionEntity t1=new TransactionEntity();
			t1.setAccountNumber(fromAc.getAccountNumber());
			t1.setAmount(amount);
	        String date1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			t1.setDate(date1);
			t1.setFromAccount(fromAcNumber);
			t1.setToAccount(toAcNumber);
			t1.setTransactionType("Transfer");
			transRepo.save(t1);
			
	
			TransactionEntity t2=new TransactionEntity();
			t2.setAccountNumber(toAc.getAccountNumber());
			t2.setAmount(amount);
			String date2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			t2.setDate(date2);
			t2.setFromAccount(fromAcNumber);
			t2.setToAccount(toAcNumber);
			t2.setTransactionType("Transfer");
			transRepo.save(t2);
			return "Transfer Successfull";
			
		
		}else {
			return "To Account is not found";
		}
	}

	@Override
	public List<TransactionEntity> getAllTransactions(int acNumber) {
		
		return transRepo.findByAccountNumberOrderByDateDesc(acNumber);
	}

	@Override
	public List<CustomerDTO> getAllAccounts() {
		List<CustomerDTO> allAccounts=new ArrayList<>();
		List<CustomerEntity> ceList=customerRepo.findAll();
		
		for(CustomerEntity c: ceList) {
			CustomerDTO cDto=new CustomerDTO();
			cDto.setAccountNumber(c.getAccountNumber());
			cDto.setAcType(c.getAcType());
			cDto.setBalance(c.getBalance());
			cDto.setEmail(c.getEmail());
			cDto.setName(c.getName());
			cDto.setPhoneNumber(c.getPhoneNumber());
			cDto.setStatus(c.getStatus());
			allAccounts.add(cDto);
		}
		return allAccounts;

	}

	@Override
	@Transactional
	public String removeCustomer(int acNumber) {
		Optional<CustomerEntity> acOptional=customerRepo.findById(acNumber);
		
		
		if(acOptional.isPresent()) {
			customerRepo.deleteById(acNumber);
			transRepo.deleteByAccountNumber(acNumber);
			return "Account "+acNumber+" Removed.";
		}else {
			return "Account not found";
		}
		
	}





}
