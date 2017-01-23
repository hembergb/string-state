package com.silverrail.stringstate.service;

import com.silverrail.stringstate.domain.UserStore;

public interface IUserStoreRepository {
	
	public UserStore getStoreByBrowser(String browser);
	public void saveUserStoreByBrowser(UserStore store);
}
