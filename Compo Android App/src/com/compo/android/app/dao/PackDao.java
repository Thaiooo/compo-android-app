package com.compo.android.app.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.Pack;

public class PackDao {
    private DataBaseHelper dataBaseHeleper;

    public PackDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public List<Pack> getAllPack() {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = dataBaseHeleper.getReadableDatabase();

	// The columns to return
	String[] projection = { TableConstant.PackTable._ID, TableConstant.PackTable.COLUMN_ORDER_NUMBER,
		TableConstant.PackTable.COLUMN_NAME, TableConstant.PackTable.COLUMN_DESCRIPTION,
		TableConstant.PackTable.COLUMN_LOCK, TableConstant.PackTable.COLUMN_SCORE_LIMIT,
		TableConstant.PackTable.COLUMN_CREDIT_LIMIT };

	// The columns for the WHERE clause
	String selection = null;
	// The values for the WHERE clause
	String[] selectionArgs = null;
	// Order
	String sortOrder = TableConstant.PackTable.COLUMN_ORDER_NUMBER + " ASC";
	// Group
	String group = null;
	// don't filter by row groups
	String having = null;
	// How you want the results sorted in the resulting Cursor
	Cursor c = session.query(TableConstant.PackTable.TABLE_NAME, projection, selection, selectionArgs, group,
		having, sortOrder);

	List<Pack> l = new ArrayList<Pack>();

	while (c.moveToNext()) {
	    long itemId = c.getLong(c.getColumnIndexOrThrow(TableConstant.PackTable._ID));
	    String itemName = c.getString(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_NAME));
	    String itemDesc = c.getString(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_DESCRIPTION));
	    String itemLock = c.getString(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_LOCK));
	    int itemCreditLimit = c.getInt(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_CREDIT_LIMIT));
	    int itemScoreLimit = c.getInt(c.getColumnIndexOrThrow(TableConstant.PackTable.COLUMN_SCORE_LIMIT));

	    Pack p = new Pack();
	    p.setId(itemId);
	    p.setName(itemName);
	    p.setDescription(itemDesc);
	    p.setLock(Boolean.valueOf(itemLock));
	    p.setCreditLimit(itemCreditLimit);
	    p.setScoreLimit(itemScoreLimit);

	    l.add(p);
	}

	dataBaseHeleper.close();
	return l;
    }
}
