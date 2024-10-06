package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.model.entity.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Integer> {
}
