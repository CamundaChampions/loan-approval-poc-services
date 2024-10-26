package com.gen.poc.loanapproval.camunda.external_task;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ExternalTaskSubscription("disbursementInstructionServiceTask")
public class DisbursementInstructionExternalTask implements ExternalTaskHandler {

    private final LoanApplicationRepository loanApplicationRepository;
    /**
     * @param externalTask
     * @param externalTaskService
     */
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        log.info("test disbursementInstructionServiceTask worker");

        Long loanApplicationId = (Long) externalTask.getVariable("loan-id");
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        loanApplication.get().setStatus(LoanApplicationStatus.APPROVE_AND_DISBURSED);
        loanApplicationRepository.save(loanApplication.get());
        externalTaskService.complete(externalTask);
        log.info("executed disbursementInstructionServiceTask external task client");
    }
}
