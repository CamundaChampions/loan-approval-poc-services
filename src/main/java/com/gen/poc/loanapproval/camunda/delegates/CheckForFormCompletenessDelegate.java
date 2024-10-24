package com.gen.poc.loanapproval.camunda.delegates;


import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Component("checkForFormCompletenessServiceTask")
@Slf4j
@RequiredArgsConstructor
public class CheckForFormCompletenessDelegate implements BaseDelegate {

    private final LoanApplicationRepository loanApplicationRepository;

    /**
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("test checkForFormCompletenessServiceTask worker");


        Long loanApplicationId = (Long) getValueByKey(delegateExecution, "loan-id");
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);

        // validate if all the data is provided correctly

        loanApplication.ifPresent(loanApp -> {
            if (ObjectUtils.allNotNull(loanApp.getAmount(), loanApp.getLoanCategory(), loanApp.getTerm())
                    && loanApp.getAmount().longValue() > 0 && loanApp.getTerm() > 0) {
                delegateExecution.setVariable("isApplicationComplete", true);
            } else {
                log.info("Mandatory data is invalid or not provided");
                delegateExecution.setVariable("isApplicationComplete", false);

            }
        });

    }
}
