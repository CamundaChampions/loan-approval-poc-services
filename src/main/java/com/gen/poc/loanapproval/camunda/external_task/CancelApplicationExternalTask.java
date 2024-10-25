package com.gen.poc.loanapproval.camunda.external_task;

import com.gen.poc.loanapproval.enums.LoanApplicationStatus;
import com.gen.poc.loanapproval.enums.TaskStatus;
import com.gen.poc.loanapproval.exception.LoanNotFoundException;
import com.gen.poc.loanapproval.repository.LoanApplicationRepository;
import com.gen.poc.loanapproval.repository.LoanApprovalTaskRepository;
import com.gen.poc.loanapproval.repository.entity.LoanApplication;
import com.gen.poc.loanapproval.repository.entity.LoanApprovalTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@ExternalTaskSubscription("cancelApplicationServiceTask")
public class CancelApplicationExternalTask implements ExternalTaskHandler {

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanApprovalTaskRepository loanApprovalTaskRepository;

    /**
     * @param externalTask
     * @param externalTaskService
     */
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String cancelType = externalTask.getVariable("cancelType");
        log.info("cancelType: {}", cancelType);
        LoanApplicationStatus loanApplicationStatus = null;
        if (StringUtils.equals(cancelType, "Auto_Cancelled")) {
            loanApplicationStatus = LoanApplicationStatus.AUTO_CANCELLED;
        }
        if (StringUtils.equals(cancelType, "Cancelled")) {
            loanApplicationStatus = LoanApplicationStatus.CANCELLED;
        }
        if (StringUtils.equals(cancelType, "Rejected")) {
            loanApplicationStatus = LoanApplicationStatus.REJECTED;
        }
        Long loanApplicationId = (Long) externalTask.getVariable("loan-id");
        cancelLoanApplication(loanApplicationId, loanApplicationStatus);
        externalTaskService.complete(externalTask);
    }

    private void cancelLoanApplication(long loanApplicationId, LoanApplicationStatus status) {
        Optional<LoanApplication> loanApplicationOptional = loanApplicationRepository.findById(loanApplicationId);
        if (loanApplicationOptional.isEmpty())
            throw new LoanNotFoundException(loanApplicationId);


        LoanApplication loanApplication = loanApplicationOptional.get();

        loanApplication.setStatus(status);
        loanApplicationRepository.save(loanApplication);

        List<LoanApprovalTask> loanApprovalTaskList = loanApprovalTaskRepository.findByLoanApplicationIdAndStatus(loanApplicationId, TaskStatus.IN_PROGRESS);
        if (!CollectionUtils.isEmpty(loanApprovalTaskList)) {
            loanApprovalTaskList.forEach(task -> task.setStatus(TaskStatus.CANCELLED));
            loanApprovalTaskRepository.saveAll(loanApprovalTaskList);
        }
    }
}
