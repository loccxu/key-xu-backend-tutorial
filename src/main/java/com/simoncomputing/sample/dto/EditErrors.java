package com.simoncomputing.sample.dto;

import java.util.Map;
import java.util.HashMap;
import com.simoncomputing.sample.exception.EditErrorException;

/**
 * 
 * If validation errors occur prior to a save action, then 
 * those errors are placed on a map where first parameter is
 * the field name and the second parameter is the error message.
 * 
 */
public class EditErrors {

	private Map<String, String> errors;

	public Map<String, String> getEditErrors() {
		return errors;
	}
	
	// Lazily create errors map
	private void allocErrors() {
		if (errors == null) 
			errors = new HashMap<>();
	}
	
	public void addError(String fieldName, String message ) {
		allocErrors();
		errors.put( fieldName,  message );
	}
	
	public String get(String fieldName) {
		return errors.get(fieldName);
	}
	
	public void throwIfErrors() {
		if (errors != null) 
			throw new EditErrorException(this);
	}

	@Override
	public String toString() {
		return "EditErrors [errors=" + errors + "]";
	}
}