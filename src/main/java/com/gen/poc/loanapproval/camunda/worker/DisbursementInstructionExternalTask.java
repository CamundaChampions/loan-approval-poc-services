package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DisbursementInstructionExternalTask {

    private final LoanApplicationRepository loanApplicationRepository;
    /**
     * @param job
     */
    @JobWorker(type = "disbursementInstructionServiceTask")
    public void execute(final ActivatedJob job) {
        log.info("test disbursementInstructionServiceTask worker");

        long loanApplicationId =Long.valueOf((Integer) job.getVariable("loan-id"));
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        loanApplication.get().setStatus(LoanApplicationStatus.APPROVE_AND_DISBURSED);
        loanApplicationRepository.save(loanApplication.get());
        log.info("executed disbursementInstructionServiceTask external task client");
    }
}
