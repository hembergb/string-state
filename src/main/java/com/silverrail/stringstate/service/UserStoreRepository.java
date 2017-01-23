package com.silverrail.stringstate.service;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import com.silverrail.stringstate.domain.UserStore;

/**
 * Class to store users data
 * @author Bret Hemberg
 *
 */
@Component
public class UserStoreRepository implements IUserStoreRepository{

	private HashMap<String, UserStore> userMap = new HashMap<String, UserStore>();
	
	@Override
	public UserStore getStoreByBrowser(String browser) {
		if(!userMap.containsKey(browser)){
			UserStore store = new UserStore();
			//initialize the values
			store.setValueString("");
			store.setBrowserName(browser);
			return store;
		}
		return userMap.get(browser);
	}
	
	/**
	 * Method used to append data to a user
	 */
	@Override
	public void saveUserStoreByBrowser(UserStore store){
		userMap.put(store.getBrowserName(), store);
	}

}
