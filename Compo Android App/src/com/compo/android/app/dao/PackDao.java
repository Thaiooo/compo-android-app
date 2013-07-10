package com.compo.android.app.dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.Match;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Theme;

public class PackDao {
    private DataBaseHelper dataBaseHeleper;

    public PackDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public List<Pack> findPacks(Theme aTheme) {
	List<Pack> l = new ArrayList<Pack>();

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aTheme.getId()) };

	    StringBuffer req = new StringBuffer("select ");
	    // Index 0
	    req.append("p.");
	    req.append(TableConstant.PackTable._ID);
	    req.append(", ");
	    // Index 1
	    req.append("p.");
	    req.append(TableConstant.PackTable.COLUMN_NAME);
	    req.append(", ");
	    // Index 2
	    req.append("p.");
	    req.append(TableConstant.PackTable.COLUMN_DESCRIPTION);
	    req.append(", ");
	    // Index 3
	    req.append("p.");
	    req.append(TableConstant.PackTable.COLUMN_LOCK);
	    req.append(", ");
	    // Index 4
	    req.append("p.");
	    req.append(TableConstant.PackTable.COLUMN_CREDIT_LIMIT);
	    req.append(", ");

	    // ---------------------------------------------------------------------------------
	    // Index 5
	    req.append("m.");
	    req.append(TableConstant.MatchTable._ID);
	    req.append(", ");
	    // Index 6
	    req.append("m.");
	    req.append(TableConstant.MatchTable.COLUMN_NAME);
	    req.append(", ");
	    // Index 7
	    req.append("m.");
	    req.append(TableConstant.MatchTable.COLUMN_DATE);
	    req.append(", ");
	    // Index 8
	    req.append("m.");
	    req.append(TableConstant.MatchTable.COLUMN_SCORE_AWAY);
	    req.append(", ");
	    // Index 9
	    req.append("m.");
	    req.append(TableConstant.MatchTable.COLUMN_SCORE_HOME);
	    req.append(" ");

	    req.append("from " + TableConstant.PackTable.TABLE_NAME + " p ");
	    req.append("left join " + TableConstant.MatchTable.TABLE_NAME + " m on m."
		    + TableConstant.MatchTable.COLUMN_PACK_ID + " = p." + TableConstant.PackTable._ID + " ");

	    req.append("where p." + TableConstant.PackTable.COLUMN_THEME_ID + " = ? ");
	    req.append("order by p." + TableConstant.PackTable.COLUMN_ORDER_NUMBER + " asc ");
	    req.append(", m." + TableConstant.MatchTable.COLUMN_ORDER_NUMBER + " asc ");

	    
	    c = session.rawQuery(req.toString(), selectionArgs);

	    Map<Long, Pack> mapPack = new HashMap<Long, Pack>();

	    while (c.moveToNext()) {
		int index = 0;
		long packId = c.getLong(index);
		Pack pack = mapPack.get(packId);

		index++;
		if (pack == null) {
		    pack = new Pack();

		    String packName = c.getString(index);
		    index++;
		    String packDescription = c.getString(index);
		    index++;
		    boolean packLock = Boolean.valueOf(c.getString(index));
		    index++;
		    int packCreditLimit = c.getInt(index);

		    pack.setId(packId);
		    pack.setName(packName);
		    pack.setDescription(packDescription);
		    pack.setLock(packLock);
		    pack.setCreditLimit(packCreditLimit);

		    pack.setMatchs(new ArrayList<Match>());

		    index++;
		    if (c.getLong(index) != 0) {
			Match match = new Match();
			match.setId(c.getLong(index));
			index++;
			match.setName(c.getString(index));
			index++;
			String matchDate = c.getString(index);
			try {
			    java.util.Date d = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(
				    matchDate);
			    Date date = new Date(d.getTime());
			    match.setDate(date);
			} catch (ParseException e) {
			    e.printStackTrace();
			}
			index++;
			match.setScoreAway(c.getInt(index));
			index++;
			match.setScoreHome(c.getInt(index));

			pack.getMatchs().add(match);
		    }
		    mapPack.put(packId, pack);

		    l.add(pack);
		} else {
		    index = 5;
		    if (c.getLong(index) != 0) {
			Match match = new Match();
			match.setId(c.getLong(index));
			index++;
			match.setName(c.getString(index));
			index++;
			String matchDate = c.getString(index);
			try {
			    java.util.Date d = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(
				    matchDate);
			    Date date = new Date(d.getTime());
			    match.setDate(date);
			} catch (ParseException e) {
			    e.printStackTrace();
			}
			index++;
			match.setScoreAway(c.getInt(index));
			index++;
			match.setScoreHome(c.getInt(index));

			pack.getMatchs().add(match);
		    }
		}
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

	return l;
    }

}
