package com.gen.poc.loanapproval.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public interface BaseDelegate extends JavaDelegate {

    default Object getValueByKey(DelegateExecution execution, String key){
        return execution.getVariable(key);
    }

    default String getStringValueByKey(DelegateExecution execution, String key){
        Object returnObject = getValueByKey(execution, key);
        return returnObject == null ? null : returnObject.toString();
    }
}
