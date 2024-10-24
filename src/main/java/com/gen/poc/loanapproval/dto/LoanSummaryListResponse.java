package com.gen.poc.loanapproval.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoanSummaryListResponse {

    private boolean allowToCreateLoan;

    private List<LoanSummaryDto> loanSummaryList;
}
