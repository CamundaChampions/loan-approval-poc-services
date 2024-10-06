package com.gen.poc.loanapproval.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "management_task")
public class ManagementTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Column(name = "stage", nullable = false, length = 10)
    private String stage;

    @ManyToOne
    @JoinColumn(name = "loan_request_id", nullable = false)
    private LoanRequest loanRequest;

    // Getters and Setters
}
