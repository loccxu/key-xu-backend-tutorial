package com.simoncomputing.sample.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3444704727145118118L;

	public NotFoundException(String msg) {
		super(msg);
	}
}