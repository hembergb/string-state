package com.silverrail.stringstate.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.silverrail.stringstate.domain.StringState;
import com.silverrail.stringstate.service.IStateLogic;

/**
 * 
 * @author Bret Hemberg
 *
 */
@RestController
public class StringstateController {
	
	@Autowired
	private IStateLogic logic;
	
	/**
	 * Gets the current value of the String stored for the user based on browser type
	 * @param state
	 * @return String
	 */
	@RequestMapping(method=RequestMethod.GET, value="/getState")
	public StringState getStringState(@RequestParam(value="stringValue", defaultValue="") String stringValue, @RequestHeader(value=HttpHeaders.USER_AGENT) String browser){
		System.out.println("getStringState - the value of the state: " + stringValue + " and the browser: " + browser);
		StringState stringState = new StringState();
		stringState.setCharacters(logic.getStateString(browser));
		return stringState;
	}
	
	/**
	 * Gets the sum of the values of the digits within the saved String stored for the user based on browser type
	 * @param state
	 * @return integer value of the summed string
	 */
	@RequestMapping(method=RequestMethod.GET, value="/getSum")
	public StringState getStringSum(@RequestParam(value="stringValue", defaultValue="0") String stringValue, @RequestHeader(value=HttpHeaders.USER_AGENT) String browser){
		System.out.println("getStringSum - the value of the state: " + stringValue + " and the browser: " + browser);
		StringState ss = new StringState();
		ss.setAmount(logic.sumStateString(stringValue));
		return ss;
	}
	
	/**
	 * Saves the value of the string appending it to the currently stored string for the user based on browser type
	 * @param state
	 * @return the new saved String
	 */
	@RequestMapping(method=RequestMethod.POST, value="/saveState")
	public StringState saveStringState(@RequestBody StringState state, @RequestHeader(value=HttpHeaders.USER_AGENT) String browser){
		System.out.println("saveStringState - the value of the state: " + state.getCharacters() + " and the browser: " + browser);

		logic.appendString(state, browser);
		
		return state;
	}
	
	/**
	 * Handles the case where the string passed within the json is not a valid alphanumeric
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler(CharacterValidationException.class)
	public void handleBadRequests(HttpServletResponse response) throws IOException {
	    response.sendError(HttpStatus.BAD_REQUEST.value(), "Please use only alphanumeric characters in your string");
	}

}
