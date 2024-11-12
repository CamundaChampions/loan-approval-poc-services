package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.exception.LoanNotFoundException;
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
public class NotifyForDocumentSigning {

    private final LoanApplicationRepository loanApplicationRepository;
    /**
     * @param job
     */
    @JobWorker(type = "notifyForDocumentSigning")
    public void execute(final ActivatedJob job) {

        long loanApplicationId =Long.valueOf((Integer) job.getVariable("loan-id"));
        LoanApplication loanApplication = findLoanApplicationById(loanApplicationId);

        loanApplication.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
        loanApplicationRepository.save(loanApplication);
        log.info("test notifyForDocumentSigning worker");
    }

    private LoanApplication findLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplication.isEmpty())
            throw new LoanNotFoundException(loanApplicationId);

        return loanApplication.get();

    }
}
