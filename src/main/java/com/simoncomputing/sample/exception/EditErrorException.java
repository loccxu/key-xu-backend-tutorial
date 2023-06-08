package com.simoncomputing.sample.exception;
import com.simoncomputing.sample.dto.EditErrors;

public class EditErrorException extends RuntimeException {

	private final EditErrors errors;
	
	public EditErrorException( EditErrors errors ) {
		super(errors.toString());
		this.errors = errors;
	}
	
	public EditErrors getEditErrors() { return errors; }
}	