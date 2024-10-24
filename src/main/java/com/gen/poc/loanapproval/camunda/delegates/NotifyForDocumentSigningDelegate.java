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

@Component("notifyForDocumentSigning")
@Slf4j
@RequiredArgsConstructor
public class NotifyForDocumentSigningDelegate implements BaseDelegate {

    private final LoanApplicationRepository loanApplicationRepository;
    /**
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Map<String, Object> returnType = new HashMap<>();
        Long loanApplicationId = Long.valueOf((Integer) delegateExecution.getVariable("loan-id"));
        LoanApplication loanApplication = findLoanApplicationById(loanApplicationId);

        loanApplication.setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
        loanApplicationRepository.save(loanApplication);
        delegateExecution.setVariable("documentSigningAcknowledgement", String.format(AppConstants.DOC_SIGN_CORRELATION_KEY, delegateExecution.getProcessInstanceId()));

        log.info("test notifyForDocumentSigning worker");

    }

    private LoanApplication findLoanApplicationById(Long loanApplicationId) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplication.isEmpty())
            throw new LoanNotFoundException(loanApplicationId);

        return loanApplication.get();

    }
}
