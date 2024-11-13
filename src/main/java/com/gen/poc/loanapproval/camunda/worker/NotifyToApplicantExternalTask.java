package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.constant.AppConstants;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyToApplicantExternalTask {

    private final LoanApplicationRepository loanApplicationRepository;

    /**
     * @param job
     */
    @JobWorker(type = "notifyToApplicantServiceTask", autoComplete = true)
    public Map<String, Object> execute(final JobClient client, final ActivatedJob job, @Variable("taskType") String taskType) {

        Map<String, Object> variables = job.getVariablesAsMap();
        long loanApplicationId =Long.valueOf((Integer) job.getVariable("loan-id"));
        boolean isApplicationComplete = (boolean) job.getVariable("isApplicationComplete");
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);

        loanApplication.ifPresent(loanApp -> {

            if (LoanApplicationStatus.CREATED.equals(loanApp.getStatus()) && !isApplicationComplete) {
                loanApp.setStatus(LoanApplicationStatus.PENDING_DATA_CORRECTION);
                variables.put("missingAppDataReceivedAcknowledgement", String.format(AppConstants.APP_UPDATED_CORRELATION_KEY, job.getProcessInstanceKey()));
            } else if ("docSigning".equals(taskType)) {
                loanApp.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
                variables.put("documentSigningAcknowledgement", String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, job.getProcessInstanceKey()));
            } else if (LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.equals(loanApp.getStatus())
                    && (boolean) job.getVariable("hasMissingData")) {
                loanApp.setStatus(LoanApplicationStatus.AWAITING_MISSING_DOCUMENT);
                variables.put("missingDocProvidedAcknowledgement", String.format(AppConstants.MISSING_DOC_CORRELATION_KEY, job.getProcessInstanceKey()));
            }
            loanApplicationRepository.save(loanApp);
            log.info("test notifyToApplicantServiceTask Completed");
        });

      return variables;
    }
}
