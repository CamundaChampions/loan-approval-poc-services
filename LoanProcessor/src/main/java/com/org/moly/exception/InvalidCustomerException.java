package com.org.moly.exception;

public class InvalidCustomerException extends RuntimeException{
	
    private String msg;
	
	public InvalidCustomerException() {};
	
	public InvalidCustomerException(String msg) {
		super(msg);
		this.msg=msg;
	}

}
