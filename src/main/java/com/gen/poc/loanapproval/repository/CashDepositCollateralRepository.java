package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.model.entity.CashDepositCollateral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashDepositCollateralRepository extends JpaRepository<CashDepositCollateral, Integer> {
}
