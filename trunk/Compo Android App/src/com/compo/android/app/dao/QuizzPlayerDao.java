package com.compo.android.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuizzPlayerDao {
    private DataBaseHelper dataBaseHeleper;

    public QuizzPlayerDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public int countHidePlayerForMatch(long aMatchId) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	int count = 0;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    count = countHidePlayerForMatch(session, aMatchId);
	} finally {
	    if (session != null) {
		session.close();
	    }
	    dataBaseHeleper.close();
	}

	return count;
    }

    public int countHidePlayerForMatch(SQLiteDatabase session, long aMatchId) {
	Cursor c = null;
	int count = 0;
	try {
	    String[] selectionArgs = { String.valueOf(aMatchId) };

	    StringBuffer req = new StringBuffer("select count(*) ");
	    req.append("from ");
	    req.append(TableConstant.QuizzPlayerTable.TABLE_NAME);
	    req.append(" where ");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_HIDE);
	    req.append(" = 1 and ");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_MATCH_ID);
	    req.append(" = ?");
	    c = session.rawQuery(req.toString(), selectionArgs);

	    c.moveToFirst();
	    count = c.getInt(0);
	} finally {
	    if (c != null) {
		c.close();
	    }
	}

	return count;
    }

}
