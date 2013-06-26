package com.compo.android.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.QuizzPlayer;

public class QuizzPlayerDao {
    private DataBaseHelper dataBaseHeleper;

    public QuizzPlayerDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public void save(QuizzPlayer o) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	try {
	    session = dataBaseHeleper.getWritableDatabase();

	    ContentValues values = new ContentValues();
	    values.put(TableConstant.QuizzPlayerTable.COLUMN_DISCOVERED, Boolean.toString(o.isDiscovered()));

	    session.update(TableConstant.QuizzPlayerTable.TABLE_NAME, values, TableConstant.QuizzPlayerTable._ID
		    + " = ?", new String[] { String.valueOf(o.getId()) });

	} finally {
	    if (session != null) {
		session.close();
	    }

	    dataBaseHeleper.close();
	}
    }

}
