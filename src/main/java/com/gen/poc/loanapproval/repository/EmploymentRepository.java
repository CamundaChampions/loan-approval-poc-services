package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.model.entity.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Integer> {
}
