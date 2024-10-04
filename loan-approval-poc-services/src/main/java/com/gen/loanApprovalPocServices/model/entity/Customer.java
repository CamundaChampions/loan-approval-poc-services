package com.gen.loanApprovalPocServices.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Access(AccessType.FIELD)
public class Customer {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_Name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "ssn")
    private String ssn;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "MARITAL_STATUS")
    private String maritalStatus;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
}
