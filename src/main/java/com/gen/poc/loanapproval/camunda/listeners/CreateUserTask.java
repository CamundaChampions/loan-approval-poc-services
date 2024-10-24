package com.gen.poc.loanapproval.camunda.listeners;

import com.gen.poc.loanapproval.enums.ApprovalCategory;
import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.enums.TaskStatus;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.LoanApprovalTaskRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("createUserTask")
@Slf4j
@RequiredArgsConstructor
public class CreateUserTask implements BaseListener {


    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanApprovalTaskRepository loanApprovalTaskRepository;

    @Override
    public void notify(DelegateTask delegateTask) {

        Long loanApplicationId = (Long) delegateTask.getVariable("loan-id");
        String taskType = delegateTask.getVariable("taskType").toString();
        LoanApplicationStatus status;
        String taskId;
        ApprovalCategory approvalCategory;
        if("financialAssessment".equalsIgnoreCase(taskType)){
            approvalCategory = ApprovalCategory.FINANCIAL_ASSESSMENT_MANAGER;
            taskId = "FA-".concat(loanApplicationId.toString());
            status = LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL;
        } else {
            approvalCategory = ApprovalCategory.RISK_ASSESSMENT_MANAGER;
            taskId = "RA-".concat(loanApplicationId.toString());
            status = LoanApplicationStatus.PENDING_RISK_ASSESSMENT_MANAGER_APPROVAL;
        }
        LoanApprovalTask task = new LoanApprovalTask();
        task.setTaskId(taskId);
        task.setTaskCategory(approvalCategory);
        task.setTaskInstanceId(String.valueOf(delegateTask.getId()));
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setLoanApplicationId(loanApplicationId);
        loanApprovalTaskRepository.save(task);
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(loanApplicationId);
        loanApplication.get().setStatus(status);
        loanApplicationRepository.save(loanApplication.get());
        // Element Id

        log.info("task element id {}", delegateTask.getId());

    }
}
