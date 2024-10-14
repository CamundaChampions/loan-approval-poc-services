package com.gen.poc.loanapproval.camunda.listeners;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public interface BaseListener extends TaskListener {

    default Object getValueByKey(DelegateTask delegateTask, String key){
        return delegateTask.getVariable(key);
    }

    default String getStringValueByKey(DelegateTask delegateTask, String key){
        Object returnObject = getValueByKey(delegateTask, key);
        return returnObject == null ? null : returnObject.toString();
    }
}
