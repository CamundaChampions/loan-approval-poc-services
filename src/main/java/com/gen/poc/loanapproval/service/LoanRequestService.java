package com.gen.poc.loanapproval.service;

import org.springframework.stereotype.Service;

import com.gen.poc.loanapproval.repository.entity.Customer;
import com.gen.poc.loanapproval.repository.entity.LoanRequest;
import com.gen.poc.loanapproval.repository.CustomerRepository;
import com.gen.poc.loanapproval.repository.LoanRequestRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanRequestService {

	private final LoanRequestRepository loanRequestRepository;
	private final CustomerRepository customerRepository;

	@Transactional
	public void save(LoanRequest loanRequest, Customer customer) {
		customerRepository.save(customer);
		loanRequest.setCustomer(customer);
		loanRequestRepository.save(loanRequest);
	}
	public LoanRequest getLoanRequest(Long loanRequestId) {
		return loanRequestRepository.findById(loanRequestId).orElseThrow();
	}
}
