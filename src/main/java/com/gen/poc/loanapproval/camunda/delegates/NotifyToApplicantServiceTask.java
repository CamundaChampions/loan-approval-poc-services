package com.gen.poc.loanapproval.camunda.delegates;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
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
            String taskType = getStringValueByKey(delegateExecution,"taskType");
            Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
            if ("docSigning".equals(taskType)) {
                loanApplication.get().setStatus(LoanApplicationStatus.PENDING_DOCUMENT_SIGNING);
                loanApplicationRepository.save(loanApplication.get());
                delegateExecution.setVariable("MSGEVNT_SIGNED_DOC_RECIEVED", "MSGEVNT_SIGNED_DOC_RECIEVED-" + loanApplication.get().getProcessInstanceId());
            }
            log.info("test notifyToApplicantServiceTask worker");

        }
    }
