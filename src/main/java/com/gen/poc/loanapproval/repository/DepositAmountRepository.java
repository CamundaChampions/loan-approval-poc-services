package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.model.entity.DepositAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositAmountRepository extends JpaRepository<DepositAmount, Integer> {
}
