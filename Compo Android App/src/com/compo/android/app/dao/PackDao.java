package com.compo.android.app.dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

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

    public Pack findPackLightByMatch(Long aMatchId) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	Pack pack = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aMatchId) };

	    StringBuffer req = new StringBuffer("select ");
	    // Column 0 to 2
	    req.append(createPackColumnRequest("p"));
	    // Column 3 to 11
	    req.append(createMatchColumnRequest("m"));

	    req.append("from ");
	    req.append(TableConstant.PackTable.TABLE_NAME);
	    req.append(" p ");

	    req.append("left join ");
	    req.append(TableConstant.MatchTable.TABLE_NAME);
	    req.append(" m on m.");
	    req.append(TableConstant.MatchTable.COLUMN_PACK_ID);
	    req.append(" = p.");
	    req.append(TableConstant.PackTable._ID);

	    req.append(" where m.");
	    req.append(TableConstant.MatchTable._ID);
	    req.append(" = ? ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    while (c.moveToNext()) {
		int index = 0;
		long packId = c.getLong(index);

		index++;
		pack = new Pack();
		pack.setId(packId);
		index = fillPack(c, index, pack);

		index = 3;
		if (c.getLong(index) != 0) {
		    Match match = new Match();
		    fillMatch(c, index, match);
		    pack.getMatchs().add(match);
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

	return pack;
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
	    // Column 0 to 2
	    req.append(createPackColumnRequest("p"));
	    // Column 3 to 11
	    req.append(createMatchColumnRequest("m"));

	    req.append("from ");
	    req.append(TableConstant.PackTable.TABLE_NAME);
	    req.append(" p ");

	    req.append("left join ");
	    req.append(TableConstant.MatchTable.TABLE_NAME);
	    req.append(" m on m.");
	    req.append(TableConstant.MatchTable.COLUMN_PACK_ID);
	    req.append(" = p.");
	    req.append(TableConstant.PackTable._ID);
	    req.append(" ");

	    req.append("where p.");
	    req.append(TableConstant.PackTable.COLUMN_THEME_ID);
	    req.append(" = ? ");

	    req.append("order by p.");
	    req.append(TableConstant.PackTable.COLUMN_ORDER_NUMBER);
	    req.append(" asc , m.");
	    req.append(TableConstant.MatchTable.COLUMN_ORDER_NUMBER);
	    req.append(" asc ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    Map<Long, Pack> mapPack = new HashMap<Long, Pack>();

	    while (c.moveToNext()) {
		int index = 0;
		long packId = c.getLong(index);
		Pack pack = mapPack.get(packId);

		if (pack == null) {
		    pack = new Pack();
		    pack.setId(packId);
		    index = fillPack(c, index, pack);

		    mapPack.put(packId, pack);
		    l.add(pack);
		}

		index = 3;
		if (c.getLong(index) != 0) {
		    Match match = new Match();
		    fillMatch(c, index, match);
		    pack.getMatchs().add(match);
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

    private String createPackColumnRequest(String aPrefixe) {
	StringBuffer req = new StringBuffer();
	// Index 0
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.PackTable._ID);
	req.append(", ");
	// Index 1
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.PackTable.COLUMN_NAME);
	req.append(", ");
	// Index 2
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.PackTable.COLUMN_DESCRIPTION);
	req.append(", ");

	return req.toString();
    }

    private String createMatchColumnRequest(String aPrefixe) {
	StringBuffer req = new StringBuffer();

	// Index 3
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable._ID);
	req.append(", ");
	// Index 4
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_NAME);
	req.append(", ");
	// Index 5
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_DATE);
	req.append(", ");
	// Index 6
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_SCORE_AWAY);
	req.append(", ");
	// Index 7
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_SCORE_HOME);
	req.append(", ");
	// Index 8
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_IS_OVERTIME);
	req.append(", ");
	// Index 9
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_SOG_HOME);
	req.append(", ");
	// Index 10
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_SOG_AWAY);
	req.append(", ");
	// Index 11
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_ORDER_NUMBER);
	req.append(" ");

	return req.toString();
    }

    private int fillPack(Cursor aCursor, int anIndex, Pack aPack) {
	anIndex++;
	String packName = aCursor.getString(anIndex);
	aPack.setName(packName);

	anIndex++;
	String packDescription = aCursor.getString(anIndex);
	aPack.setDescription(packDescription);

	aPack.setMatchs(new ArrayList<Match>());

	return anIndex;
    }

    private int fillMatch(Cursor aCursor, int anIndex, Match aMatch) {
	aMatch.setId(aCursor.getLong(anIndex));

	anIndex++;
	aMatch.setName(aCursor.getString(anIndex));

	anIndex++;
	String matchDate = aCursor.getString(anIndex);
	try {
	    java.util.Date d = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(matchDate);
	    Date date = new Date(d.getTime());
	    aMatch.setDate(date);
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	anIndex++;
	aMatch.setScoreAway(aCursor.getInt(anIndex));

	anIndex++;
	aMatch.setScoreHome(aCursor.getInt(anIndex));

	anIndex++;
	boolean isOvertime = BooleanUtils.toBoolean(aCursor.getInt(anIndex));
	aMatch.setOvertime(isOvertime);

	anIndex++;
	String quizzSogHome = aCursor.getString(anIndex);
	if (StringUtils.isNotBlank(quizzSogHome)) {
	    aMatch.setSogHome(Integer.getInteger(quizzSogHome));
	}

	anIndex++;
	String quizzSogAway = aCursor.getString(anIndex);
	if (StringUtils.isNotBlank(quizzSogAway)) {
	    aMatch.setSogAway(Integer.getInteger(quizzSogAway));
	}

	anIndex++;
	int orderNumber = aCursor.getInt(anIndex);
	aMatch.setOrderNumber(orderNumber);

	return anIndex;
    }

}
