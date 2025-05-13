package com.Bank.BankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bank.BankingSystem.model.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer>
{

}
