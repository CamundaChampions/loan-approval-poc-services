package com.gen.poc.loanapproval.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EmploymentDTO {
	private String employerName;
	private String jobTitle;
	private BigDecimal annualIncome;
	private Integer experience;
}
