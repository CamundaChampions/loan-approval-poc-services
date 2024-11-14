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
public class RiskScoringExternalTask {
    /**
     * @param job
     */
    @JobWorker(type = "riskScoringServiceTask")
    public Map<String, Object> execute(final ActivatedJob job) {
        Integer creditScore = (Integer) job.getVariable("creditScore");
        Map<String, Object> variables = new HashMap<>();
        log.info("test riskScoringServiceTask worker");
        boolean highRisk = creditScore < 800;
        variables.put("highRisk", highRisk);
        log.info("executed riskScoringServiceTask external task client");
        return variables;
    }
}
