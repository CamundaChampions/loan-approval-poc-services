package com.org.moly.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.org.moly.exception.ErrorResponse;
import com.org.moly.exception.InvalidCustomerException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=InvalidCustomerException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleException(InvalidCustomerException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
