package com.gen.poc.loanapproval.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoanDetailsDTO extends LoanRequestDTO {

	public String comments;
	public String status;
}
