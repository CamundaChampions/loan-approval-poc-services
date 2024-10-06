package com.gen.poc.loanapproval.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Employment")
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employer_name", nullable = false, length = 80)
    private String employerName;

    @Column(name = "jobTitle", nullable = false, length = 50)
    private String jobTitle;

    @Column(name = "annual_income", nullable = false)
    private Integer annualIncome;

    @Column(name = "experience", nullable = false)
    private Integer experience;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Getters and Setters
}
