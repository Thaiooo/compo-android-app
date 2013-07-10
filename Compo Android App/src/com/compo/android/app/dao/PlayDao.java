package com.compo.android.app.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.Play;

public class PlayDao {
    private DataBaseHelper dataBaseHeleper;

    public PlayDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public void update(Play o) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    values.put(TableConstant.PlayTable.COLUMN_RESPONSE, o.getResponse());
	    values.put(
		    TableConstant.PlayTable.COLUMN_DATE_TIME,
		    DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(
			    o.getDateTime()));

	    session.update(TableConstant.PlayTable.TABLE_NAME, values, TableConstant.PlayTable._ID + " = ?",
		    new String[] { String.valueOf(o.getId()) });

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}
    }

    public void add(Play o) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    values.put(
		    TableConstant.PlayTable.COLUMN_DATE_TIME,
		    DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(
			    o.getDateTime()));
	    values.put(TableConstant.PlayTable.COLUMN_QUIZZ_ID, o.getQuizzId());
	    values.put(TableConstant.PlayTable.COLUMN_USER_ID, o.getUserId());
	    values.put(TableConstant.PlayTable.COLUMN_RESPONSE, o.getResponse());

	    session.insert(TableConstant.PlayTable.TABLE_NAME, null, values);

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}
    }

    public Map<Long, Play> getAllPlay(Long anUserId) {
	Map<Long, Play> map = new HashMap<Long, Play>();

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	session = dataBaseHeleper.getReadableDatabase();
	Cursor c = null;
	try {

	    // The columns to return
	    String[] projection = { TableConstant.PlayTable._ID, TableConstant.PlayTable.COLUMN_DATE_TIME,
		    TableConstant.PlayTable.COLUMN_QUIZZ_ID, TableConstant.PlayTable.COLUMN_USER_ID,
		    TableConstant.PlayTable.COLUMN_RESPONSE };

	    // The columns for the WHERE clause
	    String selection = TableConstant.PlayTable.COLUMN_USER_ID + " = ?";
	    // The values for the WHERE clause
	    String[] selectionArgs = { anUserId.toString() };
	    // Order
	    String sortOrder = null;
	    // Group
	    String group = null;
	    // don't filter by row groups
	    String having = null;
	    // How you want the results sorted in the resulting Cursor
	    c = session.query(TableConstant.PlayTable.TABLE_NAME, projection, selection, selectionArgs, group, having,
		    sortOrder);

	    while (c.moveToNext()) {
		long itemId = c.getLong(c.getColumnIndexOrThrow(TableConstant.PlayTable._ID));
		String itemDateTime = c.getString(c.getColumnIndexOrThrow(TableConstant.PlayTable.COLUMN_DATE_TIME));
		long itemQuizzId = c.getLong(c.getColumnIndexOrThrow(TableConstant.PlayTable.COLUMN_QUIZZ_ID));
		long iteUserId = c.getLong(c.getColumnIndexOrThrow(TableConstant.PlayTable.COLUMN_USER_ID));
		String itemResponse = c.getString(c.getColumnIndexOrThrow(TableConstant.PlayTable.COLUMN_RESPONSE));

		Play p = new Play();
		p.setId(itemId);
		p.setQuizzId(itemQuizzId);
		p.setUserId(iteUserId);
		p.setResponse(itemResponse);
		try {
		    java.util.Date d = DateFormat
			    .getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).parse(itemDateTime);
		    p.setDateTime(d);
		} catch (ParseException e) {
		    e.printStackTrace();
		}

		map.put(p.getQuizzId(), p);
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

	return map;
    }

}