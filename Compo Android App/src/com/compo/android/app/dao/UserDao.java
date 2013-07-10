package com.compo.android.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.User;

public class UserDao {
    private DataBaseHelper dataBaseHeleper;

    public UserDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public void save(User o) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    values.put(TableConstant.UserTable.COLUMN_CREDIT, o.getCredit());
	    values.put(TableConstant.UserTable.COLUMN_POINT, o.getPoint());

	    session.update(TableConstant.UserTable.TABLE_NAME, values, TableConstant.UserTable._ID + " = ?",
		    new String[] { String.valueOf(o.getId()) });

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}
    }

    public User getUser() {
	User u = null;

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();

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
	    c = session.query(TableConstant.UserTable.TABLE_NAME, projection, selection, selectionArgs, group, having,
		    sortOrder);

	    u = new User();
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
	} finally {
	    if (c != null) {
		c.close();
	    }
	    if (session != null) {
		session.close();
	    }
	    dataBaseHeleper.close();
	}

	return u;
    }
}
