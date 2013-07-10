package com.compo.android.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Theme;

import java.util.ArrayList;
import java.util.List;

public class PackDao {
    private DataBaseHelper dataBaseHeleper;

    public PackDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public List<Pack> findPacks(Theme aTheme) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	List<Pack> l = new ArrayList<Pack>();
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();

	    // The columns to return
	    String[] projection = { TableConstant.PackTable._ID, TableConstant.PackTable.COLUMN_ORDER_NUMBER,
		    TableConstant.PackTable.COLUMN_NAME, TableConstant.PackTable.COLUMN_DESCRIPTION,
		    TableConstant.PackTable.COLUMN_LOCK, TableConstant.PackTable.COLUMN_CREDIT_LIMIT };

	    // The columns for the WHERE clause
	    String selection = TableConstant.PackTable.COLUMN_THEME_ID + " = " + aTheme.getId();
	    // The values for the WHERE clause
	    String[] selectionArgs = null;
	    // Order
	    String sortOrder = TableConstant.PackTable.COLUMN_ORDER_NUMBER + " ASC";
	    // Group
	    String group = null;
	    // don't filter by row groups
	    String having = null;
	    // How you want the results sorted in the resulting Cursor
	    c = session.query(TableConstant.PackTable.TABLE_NAME, projection, selection, selectionArgs, group, having,
		    sortOrder);

	    while (c.moveToNext()) {
		long itemId = c.getLong(c.getColumnIndexOrThrow(TableConstant.PackTable._ID));
		String itemName = c.getString(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_NAME));
		String itemDesc = c.getString(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_DESCRIPTION));
		String itemLock = c.getString(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_LOCK));
		int itemCreditLimit = c.getInt(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_CREDIT_LIMIT));

		Pack p = new Pack();
		p.setId(itemId);
		p.setName(itemName);
		p.setDescription(itemDesc);
		p.setLock(Boolean.valueOf(itemLock));
		p.setCreditLimit(itemCreditLimit);

		l.add(p);
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
	return l;
    }
}
