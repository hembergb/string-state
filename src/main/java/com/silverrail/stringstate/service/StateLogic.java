package com.silverrail.stringstate.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.silverrail.stringstate.domain.UserStore;
import com.silverrail.stringstate.web.CharacterValidationException;
import com.silverrail.stringstate.domain.StringState;

/**
 * This class handles all the logic required by the application - string manipulations
 * @author Bret Hemberg
 *
 */
 @Component
public class StateLogic implements IStateLogic{
	
	@Autowired
	IUserStoreRepository repo;

	/**
	 * This method takes a string and adds the digits found together to give a result.
	 * Each set of digits found are grouped e.g. zz45mep3 is 45 + 3 (not 4+5+3)
	 */
	@Override
	public int sumStateString(String stringToSum) {
		System.out.println("String to calculate the sum: " + stringToSum);
		int sum = 0;
		
		//indicate the type of search for regex d+ for digits only
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(stringToSum); 
		
		//Sum the digits for a total value
		while (m.find()) {
		   sum += Integer.parseInt(m.group());
		}
		return sum;
	}

	/**
	 * This method is used to append the new string passed to the value found in the cache for a particular user
	 */
	@Override
	public void appendString(StringState state, String browser) {
		//First make sure that the character string is alphanumeric
		if(isAlphaNumeric(state.getCharacters())){
			int qty = state.getAmount();
			UserStore us = repo.getStoreByBrowser(browser);
			
			//Output values
			System.out.println("String to append: " + state.getCharacters());
			System.out.println("Amount to append: " + qty);
			
			//Collect the values and append them to the existing string
			StringBuffer buffer = new StringBuffer(us.getValueString());
			if(qty > 1){
				for (int i = 0; i < qty; i++){
					buffer.append(state.getCharacters());
				}
			}else{
				buffer.append(state.getCharacters());
			}
			us.setValueString(buffer.toString());
			
			//Output the final string to the console
			System.out.println(us.getValueString());
			
			//Save the record to the repository
			repo.saveUserStoreByBrowser(us);
		}else{
			throw new CharacterValidationException("String contains characters other than alphanumeric");
		}
		
	}

	/**
	 * This method returns the string stored for a particular user
	 */
	@Override
	public String getStateString(String browser) {
		UserStore us = repo.getStoreByBrowser(browser);
		return us.getValueString();
	}
	
	/**
	 * Check that the string is only alphanumeric
	 */
	public boolean isAlphaNumeric(String text){
		return text.matches("^[a-zA-Z0-9]*$");
	}

}
