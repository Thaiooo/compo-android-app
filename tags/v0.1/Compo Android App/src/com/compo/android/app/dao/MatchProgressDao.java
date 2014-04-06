package com.compo.android.app.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;

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
	    session.delete(TableConstant.MatchProgressTable.TABLE_NAME, where, whereArgs);

	} finally {
	    if (c != null) {
		c.close();
	    }
	}

    }

    public Map<Long, MatchProgress> getAllMatchProgressByPack(long aPackId) {
	Map<Long, MatchProgress> map = new HashMap<Long, MatchProgress>();

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aPackId) };

	    StringBuffer req = new StringBuffer("select ");
	    req.append(createMatchColumnRequest("p"));

	    req.append("from ");
	    req.append(TableConstant.MatchProgressTable.TABLE_NAME);
	    req.append(" p ");

	    req.append("inner join ");
	    req.append(TableConstant.MatchTable.TABLE_NAME);
	    req.append(" k on k.");
	    req.append(TableConstant.MatchTable._ID);
	    req.append(" = p.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_MATCH_ID);
	    req.append(" ");

	    req.append("where k.");
	    req.append(TableConstant.PackTable.COLUMN_THEME_ID);
	    req.append(" = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		MatchProgress progress = new MatchProgress();

		int index = fillMatchProgress(c, 0, progress);
		index++;
		long matchId = c.getLong(index);

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
	MatchProgress progress = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    progress = find(session, aMatchId);

	} finally {
	    if (session != null) {
		session.close();
	    }
	    dataBaseHeleper.close();
	}

	return progress;
    }

    public MatchProgress find(SQLiteDatabase session, long aMatchId) {
	Cursor c = null;
	MatchProgress progress = null;
	try {
	    String[] selectionArgs = { String.valueOf(aMatchId) };

	    StringBuffer req = new StringBuffer("select ");
	    req.append(createMatchColumnRequest("p"));

	    req.append("from ");
	    req.append(TableConstant.MatchProgressTable.TABLE_NAME);
	    req.append(" p ");
	    req.append("where p.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_MATCH_ID);
	    req.append(" = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		progress = new MatchProgress();
		fillMatchProgress(c, 0, progress);
	    }
	} finally {
	    if (c != null) {
		c.close();
	    }
	}

	return progress;
    }

    public void update(MatchProgress aProgress) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();
	    update(session, aProgress);

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}

    }

    public void update(SQLiteDatabase session, MatchProgress aProgress) {
	ContentValues values = new ContentValues();
	values.put(TableConstant.MatchProgressTable.COLUMN_NB_QUIZZ_SUCCESS, aProgress.getNumberOfSuccessQuizz());
	values.put(TableConstant.MatchProgressTable.COLUMN_IS_COMPLETED, aProgress.isCompleted());

	session.update(TableConstant.MatchProgressTable.TABLE_NAME, values, TableConstant.MatchProgressTable._ID
		+ " = ?", new String[] { String.valueOf(aProgress.getId()) });
    }

    public void add(MatchProgress aProgress) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();
	    add(session, aProgress);
	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}
    }

    public void add(SQLiteDatabase session, MatchProgress aProgress) {
	ContentValues values = new ContentValues();
	values.put(TableConstant.MatchProgressTable.COLUMN_NB_QUIZZ_SUCCESS, aProgress.getNumberOfSuccessQuizz());
	values.put(TableConstant.MatchProgressTable.COLUMN_MATCH_ID, aProgress.getMatch().getId());
	values.put(TableConstant.MatchProgressTable.COLUMN_IS_COMPLETED,
		BooleanUtils.toIntegerObject(aProgress.isCompleted()));
	session.insert(TableConstant.MatchProgressTable.TABLE_NAME, null, values);
    }

    private int fillMatchProgress(Cursor c, int index, MatchProgress progress) {
	long progressId = c.getLong(index);
	progress.setId(progressId);

	index++;
	int nbSuccess = c.getInt(index);
	progress.setNumberOfSuccessQuizz(nbSuccess);

	index++;
	boolean isCompleted = BooleanUtils.toBoolean(c.getInt(index));
	progress.setCompleted(isCompleted);

	return index;
    }

    private String createMatchColumnRequest(String aPrefixe) {
	StringBuffer req = new StringBuffer();
	// Index 0
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchProgressTable._ID);
	req.append(", ");
	// Index 1
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchProgressTable.COLUMN_NB_QUIZZ_SUCCESS);
	req.append(", ");
	// Index 2
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchProgressTable.COLUMN_IS_COMPLETED);
	req.append(", ");
	// Index 3
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchProgressTable.COLUMN_MATCH_ID);
	req.append(" ");

	return req.toString();
    }

}
