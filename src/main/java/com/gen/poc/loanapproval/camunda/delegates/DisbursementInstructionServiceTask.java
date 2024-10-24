package com.gen.poc.loanapproval.camunda.delegates;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("disbursementInstructionServiceTask")
@Slf4j
@RequiredArgsConstructor
public class DisbursementInstructionServiceTask implements BaseDelegate {

    private final LoanApplicationRepository loanApplicationRepository;

    /**
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("test disbursementInstructionServiceTask worker");

        Long loanApplicationId = (Long) delegateExecution.getVariable("loan-id");
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        loanApplication.get().setStatus(LoanApplicationStatus.APPROVE_AND_DISBURSED);
        loanApplicationRepository.save(loanApplication.get());
    }
}
