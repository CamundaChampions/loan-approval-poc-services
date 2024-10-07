package com.gen.poc.loanapproval.model.entity;

import com.gen.poc.loanapproval.constants.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "LOAN_REQUEST")
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "term", nullable = false)
    private Integer term;

    @Column(name = "reason", length = 256)
    private String reason;

    @Column(name = "status", length = 10)
    private LoanStatus status;

    @Column(name = "COMMENTS", length = 256)
    private String comments;

    @Column(name = "workflow_id", nullable = false)
    private Integer workflowId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Getters and Setters
}
