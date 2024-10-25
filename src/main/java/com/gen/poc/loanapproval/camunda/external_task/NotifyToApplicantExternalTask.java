package com.gen.poc.loanapproval.camunda.external_task;

import com.gen.poc.loanapproval.constant.AppConstants;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
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

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ExternalTaskSubscription("notifyToApplicantServiceTask")
public class NotifyToApplicantExternalTask implements ExternalTaskHandler {

    private final LoanApplicationRepository loanApplicationRepository;

    /**
     * @param externalTask
     * @param externalTaskService
     */
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {


        Long loanApplicationId = (Long) externalTask.getVariable("loan-id");
        boolean isApplicationComplete = (boolean) externalTask.getVariable("isApplicationComplete");
        String taskType = (String) externalTask.getVariable("taskType");
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        VariableMap variables = Variables.createVariables();
        loanApplication.ifPresent(loanApp -> {

            if (LoanApplicationStatus.CREATED.equals(loanApp.getStatus()) && !isApplicationComplete) {
                loanApp.setStatus(LoanApplicationStatus.PENDING_DATA_CORRECTION);
            } else if ("docSigning".equals(taskType)) {
                loanApp.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
            } else if (LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.equals(loanApp.getStatus())
                    && (boolean) externalTask.getVariable("hasMissingData")) {
                loanApp.setStatus(LoanApplicationStatus.AWAITING_MISSING_DOCUMENT);
            }
            loanApplicationRepository.save(loanApp);
            log.info("test notifyToApplicantServiceTask Completed");
        });

        externalTaskService.complete(externalTask, variables);
    }
}
