package com.gen.poc.loanapproval.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {

    private String message;

    private String status;

    private T data;

}
