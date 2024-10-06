package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.model.entity.LoanDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDocumentRepository extends JpaRepository<LoanDocument, Integer> {
}
