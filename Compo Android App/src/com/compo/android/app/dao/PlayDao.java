package com.compo.android.app.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;

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

    public void eraseAll() {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    eraseAll(session);
	} finally {
	    if (session != null) {
		session.close();
	    }
	    dataBaseHeleper.close();
	}

    }

    public void eraseAll(SQLiteDatabase session) {
	Cursor c = null;
	try {
	    String where = null;
	    String[] whereArgs = {};
	    session.delete(TableConstant.PlayTable.TABLE_NAME, where, whereArgs);

	} finally {
	    if (c != null) {
		c.close();
	    }
	}
    }

    public int count() {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	int result = 0;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = {};

	    StringBuffer req = new StringBuffer("select count(*) from ");
	    req.append(TableConstant.PlayTable.TABLE_NAME);

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		result = c.getInt(0);
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

	return result;
    }

    public void update(Play aPlayer) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();
	    update(session, aPlayer);
	} finally {
	    if (session != null) {
		session.close();
	    }
	    dataBaseHeleper.close();
	}
    }

    public void update(SQLiteDatabase session, Play aPlayer) {
	ContentValues values = new ContentValues();
	values.put(TableConstant.PlayTable.COLUMN_RESPONSE, aPlayer.getResponse());
	values.put(
		TableConstant.PlayTable.COLUMN_DATE_TIME,
		DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(
			aPlayer.getDateTime()));
	values.put(TableConstant.PlayTable.COLUMN_IS_UNLOCK_HINT, BooleanUtils.toIntegerObject(aPlayer.isUnlockHint()));
	values.put(TableConstant.PlayTable.COLUMN_IS_UNLOCK_RANDOM,
		BooleanUtils.toIntegerObject(aPlayer.isUnlockRandom()));
	values.put(TableConstant.PlayTable.COLUMN_IS_UNLOCK_50_PERCENT,
		BooleanUtils.toIntegerObject(aPlayer.isUnlock50Percent()));
	values.put(TableConstant.PlayTable.COLUMN_IS_UNLOCK_RESPONSE,
		BooleanUtils.toIntegerObject(aPlayer.isUnlockResponse()));

	session.update(TableConstant.PlayTable.TABLE_NAME, values, TableConstant.PlayTable._ID + " = ?",
		new String[] { String.valueOf(aPlayer.getId()) });
    }

    public void add(Play o) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();
	    add(session, o);
	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}
    }

    public void add(SQLiteDatabase session, Play o) {
	ContentValues values = new ContentValues();
	values.put(
		TableConstant.PlayTable.COLUMN_DATE_TIME,
		DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(
			o.getDateTime()));
	values.put(TableConstant.PlayTable.COLUMN_QUIZZ_ID, o.getQuizzId());
	values.put(TableConstant.PlayTable.COLUMN_USER_ID, o.getUserId());
	values.put(TableConstant.PlayTable.COLUMN_RESPONSE, o.getResponse());
	values.put(TableConstant.PlayTable.COLUMN_IS_UNLOCK_HINT, BooleanUtils.toIntegerObject(o.isUnlockHint()));
	values.put(TableConstant.PlayTable.COLUMN_IS_UNLOCK_RANDOM, BooleanUtils.toIntegerObject(o.isUnlockRandom()));
	values.put(TableConstant.PlayTable.COLUMN_IS_UNLOCK_50_PERCENT,
		BooleanUtils.toIntegerObject(o.isUnlock50Percent()));
	values.put(TableConstant.PlayTable.COLUMN_IS_UNLOCK_RESPONSE,
		BooleanUtils.toIntegerObject(o.isUnlockResponse()));

	session.insert(TableConstant.PlayTable.TABLE_NAME, null, values);

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
		    TableConstant.PlayTable.COLUMN_RESPONSE, TableConstant.PlayTable.COLUMN_IS_UNLOCK_HINT,
		    TableConstant.PlayTable.COLUMN_IS_UNLOCK_RANDOM,
		    TableConstant.PlayTable.COLUMN_IS_UNLOCK_50_PERCENT,
		    TableConstant.PlayTable.COLUMN_IS_UNLOCK_RESPONSE };

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
		boolean isUnlockHint = BooleanUtils.toBoolean(c.getInt(c
			.getColumnIndexOrThrow(TableConstant.PlayTable.COLUMN_IS_UNLOCK_HINT)));
		boolean isUnlockRandom = BooleanUtils.toBoolean(c.getInt(c
			.getColumnIndexOrThrow(TableConstant.PlayTable.COLUMN_IS_UNLOCK_RANDOM)));
		boolean isUnlock50percent = BooleanUtils.toBoolean(c.getInt(c
			.getColumnIndexOrThrow(TableConstant.PlayTable.COLUMN_IS_UNLOCK_50_PERCENT)));
		boolean isUnlockResponse = BooleanUtils.toBoolean(c.getInt(c
			.getColumnIndexOrThrow(TableConstant.PlayTable.COLUMN_IS_UNLOCK_RESPONSE)));

		Play p = new Play();
		p.setId(itemId);
		p.setQuizzId(itemQuizzId);
		p.setUserId(iteUserId);
		p.setResponse(itemResponse);
		p.setUnlockHint(isUnlockHint);
		p.setUnlockRandom(isUnlockRandom);
		p.setUnlock50Percent(isUnlock50percent);
		p.setUnlockResponse(isUnlockResponse);
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
