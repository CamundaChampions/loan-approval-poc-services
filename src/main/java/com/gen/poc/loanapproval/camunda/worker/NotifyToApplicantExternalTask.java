package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
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
    @JobWorker(type = "notifyToApplicantServiceTask")
    public Map<String, Object> execute(final ActivatedJob job) {


        long loanApplicationId =Long.valueOf((Integer) job.getVariable("loan-id"));
        boolean isApplicationComplete = (boolean) job.getVariable("isApplicationComplete");
        String taskType = (String) job.getVariable("taskType");
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        Map<String, Object> variables = new HashMap<>();
        loanApplication.ifPresent(loanApp -> {

            if (LoanApplicationStatus.CREATED.equals(loanApp.getStatus()) && !isApplicationComplete) {
                loanApp.setStatus(LoanApplicationStatus.PENDING_DATA_CORRECTION);
            } else if ("docSigning".equals(taskType)) {
                loanApp.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
            } else if (LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.equals(loanApp.getStatus())
                    && (boolean) job.getVariable("hasMissingData")) {
                loanApp.setStatus(LoanApplicationStatus.AWAITING_MISSING_DOCUMENT);
            }
            loanApplicationRepository.save(loanApp);
            log.info("test notifyToApplicantServiceTask Completed");
        });

      return variables;
    }
}
