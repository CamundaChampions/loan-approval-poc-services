package com.gen.poc.loanapproval.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@RequiredArgsConstructor
public class DashboardLoanDto {

    private Integer loanId;

    private Integer amount;

    private Integer term;

    private String status;

    private String reason;
}
