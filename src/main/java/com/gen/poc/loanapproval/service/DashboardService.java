package com.gen.poc.loanapproval.service;

import com.gen.poc.loanapproval.dto.response.DashboardLoanDto;
import com.gen.poc.loanapproval.mappers.DashboardLoanDtoMapper;
import com.gen.poc.loanapproval.model.entity.LoanRequest;
import com.gen.poc.loanapproval.repository.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    public List<DashboardLoanDto> getListOfLoansForCustomer(Integer customerId){
       List<LoanRequest> loansForCustomer = loanRequestRepository.findByCustomer_Id(customerId);
       List<DashboardLoanDto> dashboardLoanDtoList = new ArrayList<>();
       loansForCustomer.forEach(l -> dashboardLoanDtoList.add(DashboardLoanDtoMapper.mapper(l)));
       return dashboardLoanDtoList;
    }

}
