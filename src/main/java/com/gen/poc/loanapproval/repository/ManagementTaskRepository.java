package com.gen.poc.loanapproval.repository;

import com.gen.poc.loanapproval.model.entity.ManagementTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementTaskRepository extends JpaRepository<ManagementTask, Integer> {
}
