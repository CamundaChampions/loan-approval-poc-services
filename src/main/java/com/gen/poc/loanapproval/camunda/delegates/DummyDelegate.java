package com.gen.poc.loanapproval.camunda.delegates;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Slf4j
@Component("DummyDelegate")
@Repository
public class DummyDelegate implements BaseDelegate{
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Dummy Delegate");
    }
}
