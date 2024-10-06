package com.gen.poc.loanapproval.dto.response;

import lombok.Data;

@Data
public class DashboardLoanDto {

    private Integer loanId;

    private Integer amount;

    private Integer term;

    private String status;

    private String reason;
}
