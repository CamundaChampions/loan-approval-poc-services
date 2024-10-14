package com.gen.poc.loanapproval.camunda.listeners;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

@Component("DummyListener")
@Slf4j
public class DummyListener implements BaseListener{
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("Dummy Listener");
    }
}
