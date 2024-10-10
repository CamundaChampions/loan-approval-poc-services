package com.gen.poc.loanapproval.camunda.delegates;


import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("creditCheckServiceTask")
@Slf4j
public class CreditCheckWorker implements BaseDelegate{

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("test creditCheckServiceTask worker");
        delegateExecution.setVariable("creditScore",820);
    }
}
