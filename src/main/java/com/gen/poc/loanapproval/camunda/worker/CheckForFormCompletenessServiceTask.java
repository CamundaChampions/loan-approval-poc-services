package com.gen.poc.loanapproval.camunda.worker;

import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckForFormCompletenessServiceTask {

    private final LoanApplicationRepository loanApplicationRepository;
    /**
     * @param job
     */
    @JobWorker(type = "checkForFormCompletenessServiceTask")
    public Map<String, Object> execute( final ActivatedJob job) {
        log.info("test checkForFormCompletenessServiceTask worker");
        long loanApplicationId =Long.valueOf((Integer) job.getVariable("loan-id"));

        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);

        // validate if all the data is provided correctly
        Map<String, Object> variables = new HashMap<>();
        loanApplication.ifPresent(loanApp -> {
            if (ObjectUtils.allNotNull(loanApp.getAmount(), loanApp.getLoanCategory(), loanApp.getTerm())
                    && loanApp.getAmount().longValue() > 0 && loanApp.getTerm() > 0) {
                variables.put("isApplicationComplete", true);
            } else {
                log.info("Mandatory data is invalid or not provided");
                variables.put("isApplicationComplete", false);

            }
        });
        log.info("executed checkForFormCompletenessServiceTask external task client");
        return variables;
    }
}
