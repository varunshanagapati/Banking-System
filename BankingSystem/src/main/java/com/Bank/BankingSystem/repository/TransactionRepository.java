package com.Bank.BankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bank.BankingSystem.model.TransactionEntity;
import java.util.List;


public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>
{
	List<TransactionEntity> findByAccountNumberOrderByDateDesc(int accountNumber);
	void deleteByAccountNumber(int accountNumber);
}
