package com.Bank.BankingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Bank.BankingSystem.dto.AdminDTO;
import com.Bank.BankingSystem.dto.CustomerDTO;
import com.Bank.BankingSystem.model.TransactionEntity;
import com.Bank.BankingSystem.service.AdminService;
import com.Bank.BankingSystem.service.AuthService;
import com.Bank.BankingSystem.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private AdminService aService;
	
	@Autowired
	private CustomerService cService;
	
	@Autowired
	private AuthService authService;
	
	@GetMapping("/admindashboard")
	public String showadminDashboard(HttpSession httpSession, Model model){
		
		if (httpSession.getAttribute("LoggedInAdmin") == null) {
	        return "redirect:/?logintip=true";
	    }
		int loginId=(int)httpSession.getAttribute("LoggedInAdmin");
		
		
		if(httpSession.getAttribute("transactionAc")!=null) {
			int transactionAc=(int)httpSession.getAttribute("transactionAc");
			List<TransactionEntity> transactions = cService.getAllTransactions(transactionAc);
			//
			model.addAttribute("transactions", transactions);
		}
		
				
		AdminDTO aDto = aService.getAdmin(loginId);
		model.addAttribute("admin",aDto);
		
		List<CustomerDTO> accounts=cService.getAllAccounts();
		model.addAttribute("accounts",accounts);
		System.out.println(aDto);
		return "admindashboard";
	}
	
	
	@GetMapping("/admindashboard/update/{acNumber}")
	public String updateAccountView(@PathVariable("acNumber") int acNumber,Model model, HttpSession httpSession) {
		if (httpSession.getAttribute("LoggedInAdmin") == null) {
	        return "redirect:/?logintip=true";
	    }
		CustomerDTO cDto = cService.getCustomer(acNumber);
		model.addAttribute("updateAccount",cDto);
//        se.setBranch(studentBranch);
//        se.setStudentEmail(studentEmail);
//        studentService.addStudent(se); // JPA will update the record

		return "updateaccount";
	}
	
	@PostMapping("/admindashboard/update/{acNumber}")
	public String updateStudent(@PathVariable("acNumber") int id,
			@RequestParam("accountNumber") int acNumber,
			@RequestParam("customerName") String customerName,
			@RequestParam("customerEmail") String customerEmail,
			@RequestParam("customerPhoneNumber") Long customerPhoneNumber,
			@RequestParam("customerAcType") String customerAcType,
			@RequestParam("acStatus") String acStatus,
			@RequestParam("customerBalance") double customerBalance,
			RedirectAttributes redirectAttributes,
			Model model) {
		CustomerDTO cDto = new CustomerDTO();
//		model.addAttribute("updateStudent",ce);
		cDto.setAccountNumber(acNumber);
		cDto.setName(customerName);
		cDto.setEmail(customerEmail);
		cDto.setPhoneNumber(customerPhoneNumber);
		cDto.setAcType(customerAcType);
		cDto.setStatus(acStatus);
		cDto.setBalance(customerBalance);
		
		String updateStatus=aService.updateCustomer(cDto); // JPA will update the record
		redirectAttributes.addFlashAttribute("status", updateStatus);
		return "redirect:/admindashboard?updateStatus=true";
		
	}
	
	@GetMapping("/admindashboard/delete/{acNumber}")
	public String deleteCustomer(@PathVariable("acNumber") int acNumber, 
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		if (httpSession.getAttribute("LoggedInAdmin") == null) {
	        return "redirect:/?logintip=true";
	    }
		String deleteStatus=cService.removeCustomer(acNumber);
		redirectAttributes.addFlashAttribute("status", deleteStatus);
		return "redirect:/admindashboard?deleteStatus=true";
	}
	
	@GetMapping("/admindashboard/transactiondetails/{acNumber}")
	public String showTransactionDetails(@PathVariable("acNumber") int acNumber, HttpSession httpSession) {
		if (httpSession.getAttribute("LoggedInAdmin") == null) {
	        return "redirect:/?logintip=true";
	    }
		httpSession.setAttribute("transactionAc", acNumber);
		return "redirect:/admindashboard";
	}
	
	@GetMapping("/adminlogout")
	public String logoutAdmin(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false); // Get session if exists

	    session.invalidate(); // Destroys session
	    

	    // Optional: Clear cache to prevent browser from accessing back pages
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    
	    return "redirect:/?logout=true";
	}
	
}
