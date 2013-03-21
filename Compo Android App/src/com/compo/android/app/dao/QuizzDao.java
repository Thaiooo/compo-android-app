package com.compo.android.app.dao;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.Level;
import com.compo.android.app.model.Quizz;

public class QuizzDao {
    private DataBaseHelper dataBaseHeleper;

    public QuizzDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public List<Quizz> getAllQuizz(long aPackId) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = dataBaseHeleper.getReadableDatabase();

	// The columns to return
	String[] projection = { TableConstant.QuizzTable._ID, TableConstant.QuizzTable.COLUMN_NAME,
		TableConstant.QuizzTable.COLUMN_DATE, TableConstant.QuizzTable.COLUMN_LEVEL,
		TableConstant.QuizzTable.COLUMN_POINT, TableConstant.QuizzTable.COLUMN_SCORE_AWAY,
		TableConstant.QuizzTable.COLUMN_SCORE_HOME };

	// The columns for the WHERE clause
	String selection = TableConstant.QuizzTable.COLUMN_PACK_ID + " = ?";
	// The values for the WHERE clause
	String[] selectionArgs = { String.valueOf(aPackId) };
	// Order
	String sortOrder = TableConstant.QuizzTable.COLUMN_ORDER_NUMBER + " ASC";
	// Group
	String group = null;
	// don't filter by row groups
	String having = null;
	// How you want the results sorted in the resulting Cursor
	Cursor c = session.query(TableConstant.QuizzTable.TABLE_NAME, projection, selection, selectionArgs, group,
		having, sortOrder);

	List<Quizz> l = new ArrayList<Quizz>();

	System.out.println("===========>" + DateFormat.getInstance().format(new Date(1)));

	while (c.moveToNext()) {
	    long itemId = c.getLong(c.getColumnIndexOrThrow(TableConstant.QuizzTable._ID));
	    String itemName = c.getString(c.getColumnIndexOrThrow(TableConstant.QuizzTable.COLUMN_NAME));
	    // String itemDate = c.getString(c.getColumnIndexOrThrow(TableConstant.QuizzTable.COLUMN_DATE));
	    Level itemLevel = Level
		    .valueOf(c.getString(c.getColumnIndexOrThrow(TableConstant.QuizzTable.COLUMN_LEVEL)));
	    int itemPoint = c.getInt(c.getColumnIndexOrThrow(TableConstant.QuizzTable.COLUMN_POINT));
	    int itemScoreAway = c.getInt(c.getColumnIndexOrThrow(TableConstant.QuizzTable.COLUMN_SCORE_AWAY));
	    int itemScoreHome = c.getInt(c.getColumnIndexOrThrow(TableConstant.QuizzTable.COLUMN_SCORE_HOME));

	    Quizz p = new Quizz();
	    p.setId(itemId);
	    p.setName(itemName);
	    // p.setDate(itemDesc);
	    p.setLevel(itemLevel);
	    p.setPoint(itemPoint);
	    p.setScoreAway(itemScoreAway);
	    p.setScoreHome(itemScoreHome);

	    l.add(p);
	}

	dataBaseHeleper.close();
	return l;
    }
}
