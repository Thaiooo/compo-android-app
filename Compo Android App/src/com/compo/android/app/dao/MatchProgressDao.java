package com.compo.android.app.dao;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.MatchProgress;

public class MatchProgressDao {
    private DataBaseHelper dataBaseHeleper;

    public MatchProgressDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public void eraseAll() {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String where = null;
	    String[] whereArgs = {};
	    session.delete(TableConstant.MatchProgressTable.TABLE_NAME, where, whereArgs);

	} finally {
	    if (c != null) {
		c.close();
	    }
	    if (session != null) {
		session.close();
	    }
	    dataBaseHeleper.close();
	}

    }

    public Map<Long, MatchProgress> getAllMatchProgress(long aPackId) {
	Map<Long, MatchProgress> map = new HashMap<Long, MatchProgress>();

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aPackId) };

	    StringBuffer req = new StringBuffer("select ");
	    // Index 0
	    req.append("p.");
	    req.append(TableConstant.MatchProgressTable._ID);
	    req.append(", ");
	    // Index 1
	    req.append("p.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_NB_QUIZZ_SUCCESS);
	    req.append(", ");
	    // Index 2
	    req.append("p.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_MATCH_ID);
	    req.append(" ");

	    req.append("from " + TableConstant.MatchProgressTable.TABLE_NAME + " p ");
	    req.append("inner join " + TableConstant.MatchTable.TABLE_NAME + " k on k." + TableConstant.MatchTable._ID
		    + " = p." + TableConstant.MatchProgressTable.COLUMN_MATCH_ID + " ");

	    req.append("where k." + TableConstant.PackTable.COLUMN_THEME_ID + " = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		MatchProgress progress = new MatchProgress();

		int index = 0;
		long progressId = c.getLong(index);
		index++;
		int nbSuccess = c.getInt(index);
		index++;
		long matchId = c.getLong(index);

		progress.setId(progressId);
		progress.setNumberOfSuccessQuizz(nbSuccess);

		map.put(matchId, progress);

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

    public MatchProgress find(long aMatchId) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	MatchProgress progress = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aMatchId) };

	    StringBuffer req = new StringBuffer("select ");
	    // Index 0
	    req.append("p.");
	    req.append(TableConstant.MatchProgressTable._ID);
	    req.append(", ");
	    // Index 1
	    req.append("p.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_NB_QUIZZ_SUCCESS);
	    req.append(", ");
	    // Index 2
	    req.append("p.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_MATCH_ID);
	    req.append(" ");

	    req.append("from " + TableConstant.MatchProgressTable.TABLE_NAME + " p ");
	    req.append("where p." + TableConstant.MatchProgressTable.COLUMN_MATCH_ID + " = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		progress = new MatchProgress();

		int index = 0;
		long progressId = c.getLong(index);
		index++;
		int nbSuccess = c.getInt(index);

		progress.setId(progressId);
		progress.setNumberOfSuccessQuizz(nbSuccess);

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

	return progress;
    }

    public void update(MatchProgress aProgress) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    values.put(TableConstant.MatchProgressTable.COLUMN_NB_QUIZZ_SUCCESS, aProgress.getNumberOfSuccessQuizz());

	    session.update(TableConstant.MatchProgressTable.TABLE_NAME, values, TableConstant.MatchProgressTable._ID
		    + " = ?", new String[] { String.valueOf(aProgress.getId()) });

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}

    }

    public void add(MatchProgress aProgress) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    values.put(TableConstant.MatchProgressTable.COLUMN_NB_QUIZZ_SUCCESS, aProgress.getNumberOfSuccessQuizz());
	    values.put(TableConstant.MatchProgressTable.COLUMN_MATCH_ID, aProgress.getMatch().getId());

	    session.insert(TableConstant.MatchProgressTable.TABLE_NAME, null, values);

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}

    }
}
