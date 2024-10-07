package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.model.entity.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {
    List<LoanRequest> findByCustomer_Id(Integer customerId);
}
