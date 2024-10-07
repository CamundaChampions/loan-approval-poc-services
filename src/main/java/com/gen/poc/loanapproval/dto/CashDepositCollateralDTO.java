package com.gen.poc.loanapproval.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CashDepositCollateralDTO {
	private String bankName;
	private String accountNumber;
	private BigDecimal amountBalance;
}
