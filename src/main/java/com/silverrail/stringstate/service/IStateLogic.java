package com.silverrail.stringstate.service;

import com.silverrail.stringstate.domain.StringState;

public interface IStateLogic {
	
	public int sumStateString(String stringToSum);
	public void appendString(StringState state, String browser);
	public String getStateString(String browser);
	public boolean isAlphaNumeric(String text);
}
