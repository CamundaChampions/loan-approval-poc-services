package com.gen.poc.loanapproval.controller;

import com.gen.poc.loanapproval.dto.response.DashboardLoanDto;
import com.gen.poc.loanapproval.dto.response.GenericResponse;
import com.gen.poc.loanapproval.service.DashboardService;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ls/loanApprovalServices/api")
public class DashboardController {

    private DashboardService dashboardService;

    @Operation(summary = "Get Loans for Customer",
            description = "Retrieves Loans based on the customer ID provided.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
//            @ApiResponse(responseCode = "404", description = "User not found")
//    })
    @GetMapping(path = "v1/getLoansForCustomer/{customer_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<List<DashboardLoanDto>> getLoans(@PathVariable(name = "customer_id") Integer customerId) {
        GenericResponse<List<DashboardLoanDto>> genericResponse = new GenericResponse();
        List<DashboardLoanDto> loanList = dashboardService.getListOfLoansForCustomer(customerId);
        genericResponse.setData(loanList);
        genericResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
        genericResponse.setMessage("SUCCESS");
        return genericResponse;
    }


}
