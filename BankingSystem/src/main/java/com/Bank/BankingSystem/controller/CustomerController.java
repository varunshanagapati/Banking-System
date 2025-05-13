package com.Bank.BankingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Bank.BankingSystem.dto.CustomerDTO;
import com.Bank.BankingSystem.model.TransactionEntity;
import com.Bank.BankingSystem.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
		
	@Autowired
	private CustomerService cService;
	
	@GetMapping("/customerdashboard")
	public String showCustomerDashboard(HttpSession httpSession, Model model){
		if (httpSession.getAttribute("LoggedInCustomer") == null) {
	        return "redirect:/?logintip=true";
	    }
		
		int acNumber=(int)httpSession.getAttribute("LoggedInCustomer");   
		List<TransactionEntity> transactions = cService.getAllTransactions(acNumber);
//
	    model.addAttribute("transactions", transactions);
		
		CustomerDTO cDto = cService.getCustomer(acNumber);
		model.addAttribute("customer",cDto);
		
		return "customerdashboard";
	}
	
	
	
	@PostMapping("/customerdashboard/deposit")
	public String customerDepositOperation(@RequestParam("depositAmount") double depositAmount,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		if (httpSession.getAttribute("LoggedInCustomer") == null) {
	        return "redirect:/?logintip=true";
	    }
		
		System.out.println(depositAmount);
		
		int acNumber=(int)httpSession.getAttribute("LoggedInCustomer");
		String transferProcess=cService.depositOperation(depositAmount, acNumber);
		redirectAttributes.addFlashAttribute("depositStatus", transferProcess);
		return "redirect:/customerdashboard?dpositStatus=true";
	}
	
	@PostMapping("/customerdashboard/withdraw")
	public String customerWithdrawOperation(@RequestParam("withdrawAmount") double withdrawAmount,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		
		if (httpSession.getAttribute("LoggedInCustomer") == null) {
	        return "redirect:/?logintip=true";
	    }
		
		int acNumber=(int)httpSession.getAttribute("LoggedInCustomer");
		double currentBalance=cService.getCustomer(acNumber).getBalance();
		if(withdrawAmount<=currentBalance) {
			String transferProcess=cService.withdrawOperation(withdrawAmount, acNumber);
			redirectAttributes.addFlashAttribute("withdrawStatus", transferProcess);
		}else {
			redirectAttributes.addFlashAttribute("withdrawStatus", "Low Balance");
		
		}
		
		
		return "redirect:/customerdashboard?withdrawStatus=true";
	}
	
	@PostMapping("/customerdashboard/transfer")
	public String customerTransferOperation(@RequestParam("fromAccount") int fromAcNumber,
			@RequestParam("toAccount") int toAcNumber,
			@RequestParam("transferAmount") double amount,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpSession httpSession) {
		
		if (httpSession.getAttribute("LoggedInCustomer") == null) {
	        return "redirect:/?logintip=true";
	    }
		
		double fromAccountBalance=cService.getCustomer(fromAcNumber).getBalance();
		
		System.out.println(fromAcNumber+"  "+toAcNumber);
		if(amount<=fromAccountBalance) {
			String transferProcess=cService.transferOperation(fromAcNumber, toAcNumber, amount);
			redirectAttributes.addFlashAttribute("transferstatus", transferProcess);
			return "redirect:/customerdashboard?transferstatus=true";
		}else {
			redirectAttributes.addFlashAttribute("transferstatus", "Low Balance");
			return "redirect:/customerdashboard?transferstatus=true";
		}
		
		
	}
	
	
	
	@GetMapping("/customerlogout")
	public String logoutCustomer(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false); // Get session if exists

	    session.invalidate(); // Destroys session
	    

	    // Optional: Clear cache to prevent browser from accessing back pages
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    
	    return "redirect:/?logout=true";
	}
}
