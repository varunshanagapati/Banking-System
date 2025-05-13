package com.Bank.BankingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Bank.BankingSystem.*;
import com.Bank.BankingSystem.dto.AdminLoginDTO;
import com.Bank.BankingSystem.dto.CustomerLoginDTO;
import com.Bank.BankingSystem.model.AdminEntity;
import com.Bank.BankingSystem.model.CustomerEntity;
import com.Bank.BankingSystem.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	
	@Autowired
	private AuthService authService;

	
	
	@GetMapping("/")
	public String showIndexPage() {
		return "index";
	}
	
	@GetMapping("/adminlogin")
	public String showAdminLoginPage() {
		return "adminloginpage";
	}
	
	@GetMapping("/customerlogin")
	public String showCustomerLoginPage() {
		return "customerloginpage";
	}
	
	@GetMapping("/adminsignup")
	public String showAdminSignupPage() {
		return "adminsignuppage";
	}
	
	@GetMapping("/customersignup")
	public String showCustomerSignupPage() {
		return "customersignuppage";
	}
	
	

	
	@PostMapping("/adminsignup")
	public String adminSignup(@RequestParam("adminSignupName") String signupName,
			@RequestParam("adminSignupId") int signupId,
			@RequestParam("adminSignupEmail") String signupEmail,
			@RequestParam("adminSignupPassword") String signupPassword,
			Model model) {
		try {
			
			AdminEntity ae=new AdminEntity();
			ae.setAdminId(signupId);
			ae.setAdminName(signupName);
			ae.setAdminEmail(signupEmail);
			ae.setAdminPassword(signupPassword);
			
			String signupStatus=authService.addAdmin(ae);
			
			System.out.println(signupStatus+" "+ae);
			return "redirect:/adminlogin";
			
		}catch(Exception e){
			System.out.println("error: "+e);
			return "redirect:/";
		}
		
	
	}
	
	@PostMapping("/adminlogin")
	public String adminLogin(@RequestParam("adminLoginId") int loginId,
			@RequestParam("adminLoginPassword") String loginPassword,
			HttpSession httpSession) {
		
		try {
			AdminLoginDTO adminDto=new AdminLoginDTO();
			adminDto.setAdminLoginId(loginId);
			adminDto.setAdminPassword(loginPassword);
			boolean isValid=authService.verifyAdmin(adminDto);
			if(isValid) {
				
				httpSession.setAttribute("LoggedInAdmin", loginId);
				System.out.println(loginId);
				return "redirect:/admindashboard";     // Here model won't work because it is redirecting, use httpsession instead
			}else {
				httpSession.setAttribute("error", "Invalid username or password");
				System.out.println("error occured");
		        return "redirect:/adminlogin?tryagain=true";

			}
		}catch (Exception e) {
			//server related error mention her
			System.out.println(e);
			return "redirect:/adminlogin?tryagain=true";
		}
	}
	
	@PostMapping("/customersignup")
	public String customerSignup(@RequestParam("customerSignupName") String signupName,
			@RequestParam("customerSignupId") int signupId,
			@RequestParam("customerSignupEmail") String signupEmail,
			@RequestParam("accountType") String signupAcType,
			@RequestParam("customerPhoneNumber") Long signupPhoneNumber,
			
			@RequestParam("customerSignupPassword") String signupPassword,
			Model model) {
		try {
			
			
			CustomerEntity ce=new CustomerEntity();
			
			ce.setAccountNumber(signupId);
			ce.setName(signupName);
			ce.setEmail(signupEmail);
			ce.setAcType(signupAcType);
			ce.setPhoneNumber(signupPhoneNumber);
			ce.setPassword(signupPassword);
			
			
			String signupStatus=authService.addCustomer(ce);  
			
			System.out.println(signupStatus+" "+ce);
			
			return "redirect:/customerlogin";
			
		}catch(Exception e){
			System.out.println("error: "+e);
			return "redirect:/";
		}
		
	}
	
	@PostMapping("/customerlogin")
	public String customerLogin(@RequestParam("customerACNumber") int acNumber,
			@RequestParam("customerACPassword") String acPasswordPassword,
			Model model, HttpSession httpSession) {
		try {
			CustomerLoginDTO customerDto=new CustomerLoginDTO();
			customerDto.setAccountNumber(acNumber);
			customerDto.setPassword(acPasswordPassword);
			boolean isValid=authService.verifyCustomer(customerDto);
			if(isValid) {
				
				httpSession.setAttribute("LoggedInCustomer", acNumber);
				System.out.println(acNumber);
				
				return "redirect:/customerdashboard";      // Here model won't work because it is redirecting, use httpsession instead
			}else {
				model.addAttribute("loginError", "Invalid username or password");
		        return "redirect:/customerlogin?tryagain=true";

			}
		}catch (Exception e) {
			//server related error mention her
			System.out.println(e);
			return "redirect:/customerlogin?tryagain=true";
		}
	
	}
	
	
	
}
