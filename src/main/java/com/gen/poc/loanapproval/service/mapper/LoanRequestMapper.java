package com.gen.poc.loanapproval.service.mapper;

import com.gen.poc.loanapproval.dto.LoanRequestDTO;
import com.gen.poc.loanapproval.dto.LoanSummaryDto;
import com.gen.poc.loanapproval.dto.LoanSummaryResponse;
import com.gen.poc.loanapproval.repository.entity.*;

import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {
	@Mapping(target = "loanApplicationId", ignore = true)
	@Mapping(target = "processInstanceId", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "customerId", ignore = true)
	LoanApplication toLoanRequestEntityOnCreate(LoanRequestDTO dto);

	List<LoanSummaryDto> mapTo(List<LoanSummary> source);

	@Mapping(target = "loanTypeCode", source = "loanType")
	LoanSummaryResponse mapToResponse(LoanSummary source);
}