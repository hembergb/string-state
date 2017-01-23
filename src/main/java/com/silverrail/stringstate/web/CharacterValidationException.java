package com.silverrail.stringstate.web;

import javax.validation.ValidationException;

public class CharacterValidationException extends ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CharacterValidationException(){
		super();
	}
	
	public CharacterValidationException(String message){
		super(message);
	}
}
