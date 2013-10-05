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

    /**
     * Return a map Pack id to <code>PackProgress</code>
     */
    public Map<Long, PackProgress> getAllPackProgressByTheme(long aThemeId) {
	Map<Long, PackProgress> map = new HashMap<Long, PackProgress>();

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aThemeId) };

	    StringBuffer req = new StringBuffer("select ");
	    // Column 0 to 2
	    req.append(createPackProgressColumnRequest("p"));

	    req.append("from ");
	    req.append(TableConstant.PackProgressTable.TABLE_NAME);
	    req.append(" p ");
	    
	    req.append("inner join ");
	    req.append(TableConstant.PackTable.TABLE_NAME);
	    req.append(" k on k.");
	    req.append(TableConstant.PackTable._ID);
	    req.append(" = p.");
	    req.append(TableConstant.PackProgressTable.COLUMN_PACK_ID);
	    req.append(" ");

	    req.append("where k.");
	    req.append(TableConstant.PackTable.COLUMN_THEME_ID);
	    req.append(" = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		PackProgress progress = new PackProgress();
		int index = fillPackProgress(c, 0, progress);

		index++;
		long packId = c.getLong(index);

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

    private int fillPackProgress(Cursor aCursor, int anIndex, PackProgress aPackProgress) {
	long progressId = aCursor.getLong(anIndex);
	aPackProgress.setId(progressId);

	anIndex++;
	int progressMatch = aCursor.getInt(anIndex);
	aPackProgress.setNumberOfSuccessMatch(progressMatch);

	return anIndex;
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
	    // Column 0 to 2
	    req.append(createPackProgressColumnRequest("p"));

	    req.append("from ");
	    req.append(TableConstant.PackProgressTable.TABLE_NAME);
	    req.append(" p ");
	    req.append("where p.");
	    req.append(TableConstant.PackProgressTable.COLUMN_PACK_ID);
	    req.append(" = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		progress = new PackProgress();
		fillPackProgress(c, 0, progress);
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
	    values.put(TableConstant.PackProgressTable.COLUMN_NB_MATCH_SUCCESS, aProgress.getNumberOfSuccessMatch());

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
	    values.put(TableConstant.PackProgressTable.COLUMN_NB_MATCH_SUCCESS, aProgress.getNumberOfSuccessMatch());
	    values.put(TableConstant.PackProgressTable.COLUMN_PACK_ID, aProgress.getPack().getId());

	    session.insert(TableConstant.PackProgressTable.TABLE_NAME, null, values);

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}

    }

    private String createPackProgressColumnRequest(String aPrefixe) {
	StringBuffer req = new StringBuffer();
	// Index 0
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.PackProgressTable._ID);
	req.append(", ");
	// Index 1
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.PackProgressTable.COLUMN_NB_MATCH_SUCCESS);
	req.append(", ");
	// Index 2
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.PackProgressTable.COLUMN_PACK_ID);
	req.append(" ");
	return req.toString();
    }

}
