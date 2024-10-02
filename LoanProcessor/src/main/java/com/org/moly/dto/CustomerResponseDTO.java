package com.org.moly.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerResponseDTO {
	
	
	private Long id;
	private String customerName;
	private  String email;
	private  String phoneNumber;

}
