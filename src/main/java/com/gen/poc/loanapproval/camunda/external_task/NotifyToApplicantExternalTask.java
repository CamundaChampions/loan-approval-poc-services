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
        loanApplication.ifPresent(loadApp -> {

            if (LoanApplicationStatus.CREATED.equals(loadApp.getStatus()) && !isApplicationComplete) {
                loadApp.setStatus(LoanApplicationStatus.PENDING_DATA_CORRECTION);
                //This is not needed - will remove this line.
                variables.put("missingAppDataReceivedAcknowledgement", String.format(AppConstants.APP_UPDATED_CORRELATION_KEY, externalTask.getProcessInstanceId()));
            } else if ("docSigning".equals(taskType)) {
                loadApp.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
                //This is not needed - will remove this line.
                variables.put("documentSigningAcknowledgement", String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, externalTask.getProcessInstanceId()));
            } else if (LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.equals(loadApp.getStatus())
                    && (boolean) externalTask.getVariable("hasMissingData")) {
                loadApp.setStatus(LoanApplicationStatus.AWAITING_MISSING_DOCUMENT);
                //This is not needed - will remove this line.
                variables.put("missingDocProvidedAcknowledgement", String.format(AppConstants.MISSING_DOC_CORRELATION_KEY, externalTask.getProcessInstanceId()));
            }
            loanApplicationRepository.save(loadApp);
            log.info("test notifyToApplicantServiceTask Completed");
        });

        externalTaskService.complete(externalTask, variables);
    }
}
