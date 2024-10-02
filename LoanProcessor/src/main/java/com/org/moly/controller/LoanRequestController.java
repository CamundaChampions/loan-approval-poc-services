package com.org.moly.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.moly.dto.CustomerRequestDTO;
import com.org.moly.dto.CustomerResponseDTO;
//import com.org.moly.exception.InvalidCustomerException;
//import com.org.moly.handler.ResponseHandler;
import com.org.moly.exception.InvalidCustomerException;
import com.org.moly.handler.ResponseHandler;
import com.org.moly.util.Constants;
import com.org.moly.service.LoanRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loan-processor")
@RequiredArgsConstructor
public class LoanRequestController {
	
   private final LoanRequestService loanReqService;
	

	@GetMapping("/customer/{id}")
	public ResponseEntity<Object> greetCustomer(@PathVariable("id") String id) {
		if (id.equals("0000")) {
			throw new InvalidCustomerException("Invalid Customer id");
		}
		return ResponseHandler.generateResponse(Constants.SUCCESS,HttpStatus.OK,loanReqService.greetCustomer(id) );
		}

	
	@PostMapping(path="/add-customer", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveCustomer(@RequestBody CustomerRequestDTO custReqDTO) {
		
		CustomerResponseDTO custResDTO =loanReqService.saveCustomer(custReqDTO);
		return ResponseHandler.generateResponse(Constants.SUCCESS, HttpStatus.OK, custResDTO);
		}
	

}
