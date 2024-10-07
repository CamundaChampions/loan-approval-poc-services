package com.gen.poc.loanapproval.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class LoanRequestDTO {
	private BigDecimal amount;
	private Integer term;
	private String reason;
	private List<LoanDocumentDTO> loanDocuments;
	private CashDepositCollateralDTO cashDepositCollateral;
	private CustomerDTO customer;
}