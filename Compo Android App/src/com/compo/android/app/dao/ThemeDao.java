package com.compo.android.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.Theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeDao {
    private DataBaseHelper dataBaseHeleper;

    public ThemeDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public List<Theme> getAllTheme() {
	List<Theme> l = new ArrayList<Theme>();

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	session = dataBaseHeleper.getReadableDatabase();
	Cursor c = null;
	try {

	    // The columns to return
	    String[] projection = { TableConstant.ThemeTable._ID, TableConstant.ThemeTable.COLUMN_ORDER_NUMBER,
		    TableConstant.ThemeTable.COLUMN_CODE, TableConstant.ThemeTable.COLUMN_NAME };

	    // The columns for the WHERE clause
	    String selection = null;
	    // The values for the WHERE clause
	    String[] selectionArgs = null;
	    // Order
	    String sortOrder = TableConstant.ThemeTable.COLUMN_ORDER_NUMBER + " ASC";
	    // Group
	    String group = null;
	    // don't filter by row groups
	    String having = null;
	    // How you want the results sorted in the resulting Cursor
	    c = session.query(TableConstant.ThemeTable.TABLE_NAME, projection, selection, selectionArgs, group, having,
		    sortOrder);

	    while (c.moveToNext()) {
		long itemId = c.getLong(c.getColumnIndexOrThrow(TableConstant.ThemeTable._ID));
		String itemName = c.getString(c.getColumnIndexOrThrow(TableConstant.ThemeTable.COLUMN_NAME));
		String itemCode = c.getString(c.getColumnIndexOrThrow(TableConstant.ThemeTable.COLUMN_CODE));

		Theme p = new Theme();
		p.setId(itemId);
		p.setName(itemName);
		p.setCode(itemCode);

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
