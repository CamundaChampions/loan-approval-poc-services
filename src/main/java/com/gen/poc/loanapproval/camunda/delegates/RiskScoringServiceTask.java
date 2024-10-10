package com.gen.poc.loanapproval.camunda.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("riskScoringServiceTask")
@Slf4j
public class RiskScoringServiceTask implements BaseDelegate {
    /**
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Integer creditScore = (Integer) delegateExecution.getVariable("creditScore");
        log.info("test riskScoringServiceTask worker");
        delegateExecution.setVariable("highRisk",creditScore < 800);
    }
}
