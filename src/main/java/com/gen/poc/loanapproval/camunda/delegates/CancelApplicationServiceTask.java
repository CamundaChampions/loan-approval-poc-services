package com.gen.poc.loanapproval.camunda.delegates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component("cancelApplicationServiceTask")
@Slf4j
@RequiredArgsConstructor
public class CancelApplicationServiceTask implements BaseDelegate {
    /**
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("test cancelApplicationServiceTask worker");
    }
}
