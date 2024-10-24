package com.gen.poc.loanapproval.camunda.delegates;


import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component("creditCheckServiceTask")
@Slf4j
public class CreditCheckDelegate implements BaseDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("test creditCheckServiceTask worker");
        String userId = getStringValueByKey(delegateExecution, "userId");
        delegateExecution.setVariable("creditScore", "applicant_1".equals(userId) ? 820 : 700);
    }
}
