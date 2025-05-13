package com.Bank.BankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bank.BankingSystem.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>
{

}
