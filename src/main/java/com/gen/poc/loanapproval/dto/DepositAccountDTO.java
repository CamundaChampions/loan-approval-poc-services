package com.gen.poc.loanapproval.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DepositAccountDTO {
	private String accountNumber;
	private String bankName;
	private BigDecimal amountCredited;
	private String accountType;
}
