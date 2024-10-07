package com.gen.poc.loanapproval.mappers;

import com.gen.poc.loanapproval.dto.response.DashboardLoanDto;
import com.gen.poc.loanapproval.model.entity.LoanRequest;

public class DashboardLoanDtoMapper {

    public static DashboardLoanDto mapper(LoanRequest loanRequest) {
        DashboardLoanDto dashboardLoanDto = new DashboardLoanDto();
        dashboardLoanDto.setLoanId(loanRequest.getId());
        dashboardLoanDto.setTerm(loanRequest.getTerm());
        dashboardLoanDto.setStatus(loanRequest.getStatus().getDisplayName());
        dashboardLoanDto.setReason(loanRequest.getReason());
        dashboardLoanDto.setAmount(loanRequest.getAmount());
        return dashboardLoanDto;
    }
}
