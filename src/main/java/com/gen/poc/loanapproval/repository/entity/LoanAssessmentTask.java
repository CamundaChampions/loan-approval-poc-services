//package com.gen.poc.loanapproval.repository.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.gen.poc.loanapproval.constant.enums.Role;
//import com.gen.poc.loanapproval.constant.enums.TaskStatus;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.ToString;
//
//@Entity
//@Table(name = "Loan_Assessment_task")
//@Data
//public class LoanAssessmentTask {
//
//	@Id
//	@Column(name = "task_id", nullable = false, length = 25)
//	private String taskId;
//
//	@Column(name = "task_instance_id", nullable = false, length = 50)
//	private String taskInstanceId;
//
//	@Column(name = "role", nullable = false, length = 10)
//	private Role role;
//
//	@Column(name = "status", nullable = false, length = 10)
//	private TaskStatus status;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "LOAN_REQUEST_ID")
//	@JsonIgnore
//	@NotNull
//	@ToString.Exclude
//	@EqualsAndHashCode.Exclude
//	private LoanRequest loanRequest;
//}
