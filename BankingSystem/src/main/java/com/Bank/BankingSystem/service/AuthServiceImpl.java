package com.Bank.BankingSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Bank.BankingSystem.dto.AdminLoginDTO;
import com.Bank.BankingSystem.dto.CustomerLoginDTO;
import com.Bank.BankingSystem.model.AdminEntity;
import com.Bank.BankingSystem.model.CustomerEntity;
import com.Bank.BankingSystem.repository.AdminRepository;
import com.Bank.BankingSystem.repository.CustomerRepository;

@Service
public class AuthServiceImpl implements AuthService
{
	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String addAdmin(AdminEntity ae) {
		
		AdminEntity a1=new AdminEntity();
		a1.setAdminId(ae.getAdminId());
		a1.setAdminName(ae.getAdminName());
		a1.setAdminEmail(ae.getAdminEmail());
		a1.setAdminPassword(passwordEncoder.encode(ae.getAdminPassword()));
		
		adminRepo.save(a1);
		
		return "Admin Signup Successfull";
	}

	@Override
	public String addCustomer(CustomerEntity ce) {
		
		//By default minimum balance is 1500 and account status will be active.
		double minBalance=1500;          
		String defaultStatus = "Active";
		
		CustomerEntity c1=new CustomerEntity();
		c1.setAccountNumber(ce.getAccountNumber());
		c1.setName(ce.getName());
		c1.setEmail(ce.getEmail());
		c1.setAcType(ce.getAcType());
		c1.setPhoneNumber(ce.getPhoneNumber());
		c1.setStatus(ce.getStatus());
		//set minimum balance 1500 to customer
		c1.setBalance(minBalance);
		
		
		c1.setStatus(defaultStatus);

		c1.setPassword(passwordEncoder.encode(ce.getPassword()));
		
		customerRepo.save(c1);
		
		return "Customer Signup Successfull";
	}

	@Override
	public boolean verifyAdmin(AdminLoginDTO adminDto) {
		Optional<AdminEntity> userOptional = adminRepo.findById(adminDto.getAdminLoginId());
        if (userOptional.isPresent()) {
            AdminEntity user = userOptional.get();
            return passwordEncoder.matches(adminDto.getAdminPassword(), user.getAdminPassword());
        }
		return false;
	}

	@Override
	public boolean verifyCustomer(CustomerLoginDTO customerDto) {
		Optional<CustomerEntity> userOptional = customerRepo.findById(customerDto.getAccountNumber());
        if (userOptional.isPresent()) {
            CustomerEntity user = userOptional.get();
            return passwordEncoder.matches(customerDto.getPassword(), user.getPassword());
        }
		return false;
	}

	@Override
	public String getAdminName(int loginId) {
		
		
		return adminRepo.findById(loginId).get().getAdminName();
	}

	@Override
	public String getCustomerName(int acNumber) {
		
		return customerRepo.findById(acNumber).get().getName();
	}

}
