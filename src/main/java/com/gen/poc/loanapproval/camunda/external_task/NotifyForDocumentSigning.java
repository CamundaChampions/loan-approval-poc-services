package com.gen.poc.loanapproval.camunda.external_task;

import com.gen.poc.loanapproval.constant.AppConstants;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.exception.LoanNotFoundException;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ExternalTaskSubscription("notifyForDocumentSigning")
public class NotifyForDocumentSigning implements ExternalTaskHandler {

    private final LoanApplicationRepository loanApplicationRepository;
    /**
     * @param externalTask
     * @param externalTaskService
     */
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        Long loanApplicationId =  externalTask.getVariable("loan-id");
        LoanApplication loanApplication = findLoanApplicationById(loanApplicationId);

        loanApplication.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
        loanApplicationRepository.save(loanApplication);
        externalTaskService.complete(externalTask);
        log.info("test notifyForDocumentSigning worker");
    }

    private LoanApplication findLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplication.isEmpty())
            throw new LoanNotFoundException(loanApplicationId);

        return loanApplication.get();

    }
}
