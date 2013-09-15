package com.compo.android.app.dao;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.PackProgress;

public class PackProgressDao {
    private DataBaseHelper dataBaseHeleper;

    public PackProgressDao(Context context) {
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
	    session.delete(TableConstant.PackProgressTable.TABLE_NAME, where, whereArgs);

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

    public Map<Long, PackProgress> getAllPackProgress(long aThemeId) {
	Map<Long, PackProgress> map = new HashMap<Long, PackProgress>();

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aThemeId) };

	    StringBuffer req = new StringBuffer("select ");
	    // Index 0
	    req.append("p.");
	    req.append(TableConstant.PackProgressTable._ID);
	    req.append(", ");
	    // Index 1
	    req.append("p.");
	    req.append(TableConstant.PackProgressTable.COLUMN_MATCH);
	    req.append(", ");
	    // Index 2
	    req.append("p.");
	    req.append(TableConstant.PackProgressTable.COLUMN_PACK_ID);
	    req.append(" ");

	    req.append("from " + TableConstant.PackProgressTable.TABLE_NAME + " p ");
	    req.append("inner join " + TableConstant.PackTable.TABLE_NAME + " k on k." + TableConstant.PackTable._ID
		    + " = p." + TableConstant.PackProgressTable.COLUMN_PACK_ID + " ");

	    req.append("where k." + TableConstant.PackTable.COLUMN_THEME_ID + " = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		PackProgress progress = new PackProgress();

		int index = 0;
		long progressId = c.getLong(index);
		index++;
		int progressMatch = c.getInt(index);
		index++;
		long packId = c.getLong(index);

		progress.setId(progressId);
		progress.setNumberOfSuccessMatch(progressMatch);

		map.put(packId, progress);

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

    public PackProgress find(Pack aPack) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	PackProgress progress = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aPack.getId()) };

	    StringBuffer req = new StringBuffer("select ");
	    // Index 0
	    req.append("p.");
	    req.append(TableConstant.PackProgressTable._ID);
	    req.append(", ");
	    // Index 1
	    req.append("p.");
	    req.append(TableConstant.PackProgressTable.COLUMN_MATCH);
	    req.append(", ");
	    // Index 2
	    req.append("p.");
	    req.append(TableConstant.PackProgressTable.COLUMN_PACK_ID);
	    req.append(" ");

	    req.append("from " + TableConstant.PackProgressTable.TABLE_NAME + " p ");
	    req.append("where p." + TableConstant.PackProgressTable.COLUMN_PACK_ID + " = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		progress = new PackProgress();

		int index = 0;
		long progressId = c.getLong(index);
		index++;
		int progressMatch = c.getInt(index);

		progress.setId(progressId);
		progress.setNumberOfSuccessMatch(progressMatch);

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

    public void update(PackProgress aProgress) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    values.put(TableConstant.PackProgressTable.COLUMN_MATCH, aProgress.getNumberOfSuccessMatch());

	    session.update(TableConstant.PackProgressTable.TABLE_NAME, values, TableConstant.PackProgressTable._ID
		    + " = ?", new String[] { String.valueOf(aProgress.getId()) });

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}

    }

    public void add(PackProgress aProgress) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    values.put(TableConstant.PackProgressTable.COLUMN_MATCH, aProgress.getNumberOfSuccessMatch());
	    values.put(TableConstant.PackProgressTable.COLUMN_PACK_ID, aProgress.getPack().getId());

	    session.insert(TableConstant.PackProgressTable.TABLE_NAME, null, values);

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}

    }
}
