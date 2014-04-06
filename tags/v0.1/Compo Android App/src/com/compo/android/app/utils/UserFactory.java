package com.compo.android.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.dao.UserDao;
import com.compo.android.app.model.User;

public class UserFactory {

    private static UserFactory _instance;
    private User _user;

    private UserFactory() {
    }

    public static UserFactory getInstance() {
	if (_instance == null) {
	    _instance = new UserFactory();
	}
	return _instance;
    }

    public User getUser(Context aContext) {
	if (null == _user) {
	    UserDao dao = new UserDao(aContext);
	    _user = dao.getUser();
	}
	return _user;
    }

    public User getUser(SQLiteDatabase session, Context aContext) {
	if (null == _user) {
	    UserDao dao = new UserDao(aContext);
	    _user = dao.getUser(session);
	}
	return _user;
    }

    public void updateUser(Context aContext) {
	UserDao dao = new UserDao(aContext);
	dao.save(_user);
    }

    public void updateUser(SQLiteDatabase session, Context aContext) {
	UserDao dao = new UserDao(aContext);
	dao.save(session, _user);
    }

}
