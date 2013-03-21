package com.compo.android.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.User;

public class UserDao {
    private DataBaseHelper dataBaseHeleper;

    public UserDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public User getUser() {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = dataBaseHeleper.getReadableDatabase();

	// The columns to return
	String[] projection = { TableConstant.UserTable._ID, TableConstant.UserTable.COLUMN_CREDIT,
		TableConstant.UserTable.COLUMN_POINT, TableConstant.UserTable.COLUMN_OVERALL_TIME };

	// The columns for the WHERE clause
	String selection = null;
	// The values for the WHERE clause
	String[] selectionArgs = null;
	// Order
	String sortOrder = null;
	// Group
	String group = null;
	// don't filter by row groups
	String having = null;
	// How you want the results sorted in the resulting Cursor
	Cursor c = session.query(TableConstant.UserTable.TABLE_NAME, projection, selection, selectionArgs, group,
		having, sortOrder);

	User u = new User();
	if (c.getCount() != 0) {
	    c.moveToFirst();
	    long itemId = c.getLong(c.getColumnIndexOrThrow(TableConstant.UserTable._ID));
	    Integer itemCredit = c.getInt(c.getColumnIndexOrThrow(TableConstant.UserTable.COLUMN_CREDIT));
	    Integer itemPoint = c.getInt(c.getColumnIndexOrThrow(TableConstant.UserTable.COLUMN_POINT));
	    Long itemOverallTime = c.getLong(c.getColumnIndexOrThrow(TableConstant.UserTable.COLUMN_OVERALL_TIME));

	    u.setId(itemId);
	    u.setCredit(itemCredit);
	    u.setPoint(itemPoint);
	    u.setOverallTime(itemOverallTime);
	}

	dataBaseHeleper.close();
	return u;
    }
}
