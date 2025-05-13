package com.Bank.BankingSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bank.BankingSystem.dto.AdminDTO;
import com.Bank.BankingSystem.dto.CustomerDTO;
import com.Bank.BankingSystem.model.AdminEntity;
import com.Bank.BankingSystem.model.CustomerEntity;
import com.Bank.BankingSystem.repository.AdminRepository;
import com.Bank.BankingSystem.repository.CustomerRepository;

@Service
public class AdminServiceImpl implements AdminService
{

	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public AdminDTO getAdmin(int loginId) {
		// TODO Auto-generated method stub
		AdminEntity ae = adminRepo.findById(loginId).get();
		AdminDTO aDto= new AdminDTO();
		aDto.setLoginId(loginId);
		aDto.setEmail(ae.getAdminEmail());;
		aDto.setName(ae.getAdminName());;
		
		return aDto;
	}

	@Override
	public String updateCustomer(CustomerDTO cDto) {
		
		try {
		int acNumber=cDto.getAccountNumber();
		Optional<CustomerEntity> userOptional=customerRepo.findById(acNumber);
		
		if(userOptional.isPresent()) {
			CustomerEntity ce = userOptional.get();
			ce.setAccountNumber(acNumber);
			ce.setAcType(cDto.getAcType());
			ce.setBalance(cDto.getBalance());
			ce.setEmail(cDto.getEmail());
			ce.setName(cDto.getName());
			ce.setPhoneNumber(cDto.getPhoneNumber());
			ce.setStatus(cDto.getStatus());
			customerRepo.save(ce);
			return "Update on "+acNumber+" Successfull.";
			
		}else {
			return "Update failed";
		}
		}catch(Exception e) {
			System.out.println(e);
			return "Something went wrong!";
		}
		
		
	}
}
