package com.gen.poc.loanapproval.camunda.delegates;

import com.gen.poc.loanapproval.constant.AppConstants;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.exception.LoanNotFoundException;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("notifyToApplicantServiceTask")
@Slf4j
@RequiredArgsConstructor
public class NotifyToApplicantServiceTask implements BaseDelegate {

    private final LoanApplicationRepository loanApplicationRepository;

    /**
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Long loanApplicationId = Long.valueOf((Integer) delegateExecution.getVariable("loan-id"));
        boolean isApplicationComplete = (boolean) delegateExecution.getVariable("isApplicationComplete");
        String taskType = (String) delegateExecution.getVariable("taskType");
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);

        loanApplication.ifPresent(loadApp -> {

            if (LoanApplicationStatus.CREATED.equals(loadApp.getStatus()) && !isApplicationComplete) {
                loadApp.setStatus(LoanApplicationStatus.PENDING_DATA_CORRECTION);
                delegateExecution.setVariable("missingAppDataReceivedAcknowledgement", String.format(AppConstants.APP_UPDATED_CORRELATION_KEY, delegateExecution.getProcessInstanceId()));
            } else if ("docSigning".equals(taskType)) {
                loadApp.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
                delegateExecution.setVariable("documentSigningAcknowledgement", String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, delegateExecution.getProcessInstanceId()));
            } else if (LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL.equals(loadApp.getStatus())
                    && Boolean.parseBoolean((String) delegateExecution.getVariable("hasMissingData"))) {
                loadApp.setStatus(LoanApplicationStatus.AWAITING_MISSING_DOCUMENT);
                delegateExecution.setVariable("missingDocProvidedAcknowledgement", String.format(AppConstants.MISSING_DOC_CORRELATION_KEY, delegateExecution.getProcessInstanceId()));
            }
            loanApplicationRepository.save(loadApp);
            log.info("test notifyToApplicantServiceTask worker");
        });

    }

    private LoanApplication findLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplication.isEmpty())
            throw new LoanNotFoundException(loanApplicationId);

        return loanApplication.get();

    }
}
