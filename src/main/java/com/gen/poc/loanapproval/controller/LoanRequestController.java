package com.gen.poc.loanapproval.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gen.poc.loanapproval.controller.mapper.LoanRequestMapper;
import com.gen.poc.loanapproval.dto.LoanDetailsDTO;
import com.gen.poc.loanapproval.dto.LoanRequestDTO;
import com.gen.poc.loanapproval.handler.ResponseHandler;
import com.gen.poc.loanapproval.repository.entity.Customer;
import com.gen.poc.loanapproval.repository.entity.LoanRequest;
import com.gen.poc.loanapproval.service.LoanRequestService;
import com.gen.poc.loanapproval.util.Constants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loan-requests")
@RequiredArgsConstructor
public class LoanRequestController {

	private final LoanRequestService loanRequestService;
	private final LoanRequestMapper loanRequestMapper;

	@PostMapping
	public ResponseEntity<Object> createLoanRequest(@RequestBody LoanRequestDTO loanRequestDTO) {
		LoanRequest loanRequest = loanRequestMapper.toLoanRequestEntity(loanRequestDTO);
		Customer customer = loanRequestMapper.toCustomerEntity(loanRequestDTO.getCustomer());
		loanRequestService.save(loanRequest, customer);
		return ResponseHandler.generateResponse(Constants.SUCCESS, HttpStatus.CREATED, "");
	}

	@GetMapping("/{loanRequestId}")
	public ResponseEntity<Object> getLoanRequestDetails(@PathVariable Long loanRequestId) {
		LoanRequest loanReq = loanRequestService.getLoanRequest(loanRequestId);
		LoanDetailsDTO loanDetailsDto = loanRequestMapper.toLoanDetailsDto(loanReq);
		return ResponseHandler.generateResponse(Constants.SUCCESS, HttpStatus.OK, loanDetailsDto);
	}
}
