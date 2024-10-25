package com.gen.poc.loanapproval.camunda.external_task;

import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@ExternalTaskSubscription("checkForFormCompletenessServiceTask")
public class CheckForFormCompletenessServiceTask implements ExternalTaskHandler {

    private final LoanApplicationRepository loanApplicationRepository;
    /**
     * @param externalTask
     * @param externalTaskService
     */
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        log.info("test checkForFormCompletenessServiceTask worker");
        Long loanApplicationId = (Long)  externalTask.getVariable("loan-id");

        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);

        // validate if all the data is provided correctly
        VariableMap variables = Variables.createVariables();
        loanApplication.ifPresent(loanApp -> {
            if (ObjectUtils.allNotNull(loanApp.getAmount(), loanApp.getLoanCategory(), loanApp.getTerm())
                    && loanApp.getAmount().longValue() > 0 && loanApp.getTerm() > 0) {
                variables.put("isApplicationComplete", true);
            } else {
                log.info("Mandatory data is invalid or not provided");
                variables.put("isApplicationComplete", false);

            }
        });
        externalTaskService.complete(externalTask, variables);
        log.info("executed checkForFormCompletenessServiceTask external task client");

    }
}
