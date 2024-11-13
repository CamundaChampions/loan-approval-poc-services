package com.gen.poc.loanapproval.camunda.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditCheckExternalTask {
    /**
     * @param job
     */
    @JobWorker(type = "creditCheckServiceTask")
    public Map<String, Object> execute(final ActivatedJob job) {
        log.info("test creditCheckServiceTask");
        String userId = (String) job.getVariable("userId");
        Map<String, Object> variables = new HashMap<>();
        variables.put("creditScore", "applicant_1".equals(userId) ? 820 : 700);
        log.info("executed creditCheckServiceTask external task client");
        return variables;
    }
}
