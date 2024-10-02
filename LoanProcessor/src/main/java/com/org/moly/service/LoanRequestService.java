package com.org.moly.service;

import org.springframework.stereotype.Service;
import com.org.moly.dto.CustomerRequestDTO;
import com.org.moly.dto.CustomerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanRequestService {
	
	
	private CustomerResponseDTO custRes;
	
	
public CustomerResponseDTO greetCustomer(String id) {
		
		CustomerResponseDTO custRes = new CustomerResponseDTO();
		custRes.setCustomerName("Suryakant Chaturvedi");
		custRes.setId(Long.valueOf(id));
		custRes.setEmail("surChatu@genpact.com");
		custRes.setPhoneNumber("9784669996");
		
		
		return custRes;
	}
	
	public CustomerResponseDTO saveCustomer(CustomerRequestDTO custReqDTO) {
		
		CustomerResponseDTO custRes = new CustomerResponseDTO();
		custRes.setCustomerName("Suryakant Chaturvedi");
		custRes.setId(Long.valueOf(custReqDTO.getId()));
		custRes.setEmail("surChatu@genpact.com");
		custRes.setPhoneNumber("9784669996");
		return custRes;
		
	}


	
}
