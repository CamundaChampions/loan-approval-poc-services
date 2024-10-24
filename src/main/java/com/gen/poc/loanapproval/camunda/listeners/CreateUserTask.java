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
import org.apache.commons.lang3.StringUtils;
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
        log.info("TaskType: {}", taskType);
        LoanApplicationStatus status;
        String taskId;
        ApprovalCategory approvalCategory;
        if (StringUtils.equalsIgnoreCase("FINANCIAL_ASSESSMENT_MANAGER", taskType)) {
            log.info("Creating FM task");
            approvalCategory = ApprovalCategory.FINANCIAL_ASSESSMENT_MANAGER;
            taskId = "FM-".concat(loanApplicationId.toString());
            status = LoanApplicationStatus.PENDING_FINANCIAL_ASSESSMENT_MANAGER_APPROVAL;
        } else {
            log.info("Creating RM task");
            approvalCategory = ApprovalCategory.RISK_ASSESSMENT_MANAGER;
            taskId = "RM-".concat(loanApplicationId.toString());
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
        log.info("Loan Approval task: {}", task);
        loanApplicationRepository.save(loanApplication.get());
        log.info("Loan Application: {}", loanApplication);
        // Element Id

        log.info("task element id {}", delegateTask.getId());

    }
}
