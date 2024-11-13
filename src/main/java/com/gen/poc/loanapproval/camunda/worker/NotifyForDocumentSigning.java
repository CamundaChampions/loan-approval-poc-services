package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.constant.AppConstants;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.exception.LoanNotFoundException;
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
public class NotifyForDocumentSigning {

    private final LoanApplicationRepository loanApplicationRepository;
    /**
     * @param job
     */
    @JobWorker(type = "notifyForDocumentSigning", autoComplete = true)
    public Map<String, Object> execute(final ActivatedJob job) {

        Map<String, Object> returnType = new HashMap<>();
        long loanApplicationId =Long.valueOf((Integer) job.getVariable("loan-id"));
        LoanApplication loanApplication = findLoanApplicationById(loanApplicationId);

        loanApplication.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
        loanApplicationRepository.save(loanApplication);
        returnType.put("documentSigningAcknowledgement", String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, job.getProcessInstanceKey()));

        log.info("test notifyForDocumentSigning worker");
        return returnType;
    }

    private LoanApplication findLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplication.isEmpty())
            throw new LoanNotFoundException(loanApplicationId);

        return loanApplication.get();

    }
}
