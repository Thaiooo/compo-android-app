package com.compo.android.app.utils;

import com.compo.android.app.model.User;

public class UserFactory {

    private static UserFactory instance;
    private User user;

    private UserFactory() {
    }

    public static UserFactory getInstance() {
	if (instance == null) {
	    instance = new UserFactory();
	}
	return instance;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User anUser) {
	user = anUser;
    }

}
