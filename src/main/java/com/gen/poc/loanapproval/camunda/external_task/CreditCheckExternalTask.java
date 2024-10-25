package com.gen.poc.loanapproval.camunda.external_task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ExternalTaskSubscription("creditCheckServiceTask")
public class CreditCheckExternalTask implements ExternalTaskHandler {
    /**
     * @param externalTask
     * @param externalTaskService
     */
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        log.info("test creditCheckServiceTask");
        String userId = externalTask.getVariable("userId") ;
        VariableMap variables = Variables.createVariables();
        variables.put("creditScore", "applicant_1".equals(userId) ? 820 : 700);
        externalTaskService.complete(externalTask, variables);
        log.info("executed creditCheckServiceTask external task client");
    }
}
