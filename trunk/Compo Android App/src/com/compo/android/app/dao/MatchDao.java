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

import com.compo.android.app.model.ColorEnum;
import com.compo.android.app.model.Match;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Player;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;

public class MatchDao {
    private DataBaseHelper dataBaseHeleper;

    public MatchDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public List<Match> getAllQuizz(long aPackId) {
	List<Match> l = null;

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aPackId) };

	    StringBuffer req = new StringBuffer("select ");
	    // Column 0 to 8
	    req.append(createMatchColumnRequest("q"));
	    // Column 9 to 23
	    req.append(createQuizzPlayerColumnRequest("qp"));
	    // Column 24 to 32
	    req.append(createTeamColumnRequest("t"));
	    // Column 33 to 34
	    req.append(createPlayerColumnRequest("p"));

	    req.append("from ");
	    req.append(TableConstant.MatchTable.TABLE_NAME);
	    req.append(" q ");

	    req.append("left join ");
	    req.append(TableConstant.QuizzPlayerTable.TABLE_NAME);
	    req.append(" qp on q.");
	    req.append(TableConstant.MatchTable._ID);
	    req.append(" = qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_MATCH_ID);
	    req.append(" ");

	    req.append("left join ");
	    req.append(TableConstant.TeamTable.TABLE_NAME);
	    req.append(" t on qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_TEAM_ID);
	    req.append(" = t.");
	    req.append(TableConstant.TeamTable._ID);
	    req.append(" ");

	    req.append("left join ");
	    req.append(TableConstant.PlayerTable.TABLE_NAME);
	    req.append(" p on qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_PLAYER_ID);
	    req.append(" = p.");
	    req.append(TableConstant.PlayerTable._ID);
	    req.append(" ");

	    req.append("where q.");
	    req.append(TableConstant.MatchTable.COLUMN_PACK_ID);
	    req.append(" = ? ");
	    req.append("order by q.");
	    req.append(TableConstant.MatchTable.COLUMN_ORDER_NUMBER);
	    req.append(" asc ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    l = fillResult(c);

	} finally {
	    if (c != null) {
		c.close();
	    }
	    if (session != null) {
		session.close();
	    }
	    dataBaseHeleper.close();
	}

	if (l == null) {
	    l = new ArrayList<Match>();
	}
	return l;
    }

    public List<Match> getUncompletedMatchsByPack(Pack aPack) {
	List<Match> l = null;

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aPack.getId()) };

	    StringBuffer req = new StringBuffer("select ");
	    // Column 0 to 8
	    req.append(createMatchColumnRequest("q"));
	    // Column 9 to 23
	    req.append(createQuizzPlayerColumnRequest("qp"));
	    // Column 24 to 32
	    req.append(createTeamColumnRequest("t"));
	    // Column 33 to 34
	    req.append(createPlayerColumnRequest("p"));

	    req.append("from ");
	    req.append(TableConstant.MatchTable.TABLE_NAME);
	    req.append(" q ");

	    req.append("left join ");
	    req.append(TableConstant.MatchProgressTable.TABLE_NAME);
	    req.append(" mp on q.");
	    req.append(TableConstant.MatchTable._ID);
	    req.append(" = mp.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_MATCH_ID);
	    req.append(" ");

	    req.append("left join ");
	    req.append(TableConstant.QuizzPlayerTable.TABLE_NAME);
	    req.append(" qp on q.");
	    req.append(TableConstant.MatchTable._ID);
	    req.append(" = qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_MATCH_ID);
	    req.append(" ");

	    req.append("left join ");
	    req.append(TableConstant.TeamTable.TABLE_NAME);
	    req.append(" t on qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_TEAM_ID);
	    req.append(" = t.");
	    req.append(TableConstant.TeamTable._ID);
	    req.append(" ");

	    req.append("left join ");
	    req.append(TableConstant.PlayerTable.TABLE_NAME);
	    req.append(" p on qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_PLAYER_ID);
	    req.append(" = p.");
	    req.append(TableConstant.PlayerTable._ID);
	    req.append(" ");

	    req.append("where q.");
	    req.append(TableConstant.MatchTable.COLUMN_PACK_ID);
	    req.append(" = ? ");

	    req.append("and (mp.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_IS_COMPLETED);
	    req.append(" is null or mp.");
	    req.append(TableConstant.MatchProgressTable.COLUMN_IS_COMPLETED);
	    req.append("=");
	    req.append(BooleanUtils.toInteger(false));
	    req.append(" ) ");
	    req.append("order by q.");
	    req.append(TableConstant.MatchTable.COLUMN_ORDER_NUMBER);
	    req.append(" asc ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    l = fillResult(c);

	} finally {
	    if (c != null) {
		c.close();
	    }
	    if (session != null) {
		session.close();
	    }
	    dataBaseHeleper.close();
	}

	if (l == null) {
	    l = new ArrayList<Match>();
	}

	return l;
    }

    private List<Match> fillResult(Cursor c) {
	List<Match> l = new ArrayList<Match>();
	Map<Long, Match> mapMatch = new HashMap<Long, Match>();
	while (c.moveToNext()) {
	    int index = 0;
	    long matchId = c.getLong(index);
	    Match match = mapMatch.get(matchId);

	    if (match == null) {
		match = new Match();
		match.setId(matchId);

		index = fillMatch(c, index, match);
		mapMatch.put(matchId, match);

		l.add(match);
	    }
	    index = 9;
	    if (c.getLong(index) != 0) {
		fillQuizzPlayer(c, index, match);
	    }
	}

	return l;
    }

    private int fillMatch(Cursor aCursor, int anIndex, Match aMatch) {
	anIndex++;
	String quizzName = aCursor.getString(anIndex);
	aMatch.setName(quizzName);

	anIndex++;
//	String quizzDate = aCursor.getString(anIndex);
//	try {
//	    java.util.Date d = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(quizzDate);
//	    Date date = new Date(d.getTime());
//	    aMatch.setDate(date);
//	} catch (ParseException e) {
//	    e.printStackTrace();
//	}

	anIndex++;
	int quizzScoreAway = aCursor.getInt(anIndex);
	aMatch.setScoreAway(quizzScoreAway);

	anIndex++;
	int quizzScoreHome = aCursor.getInt(anIndex);
	aMatch.setScoreHome(quizzScoreHome);

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

	aMatch.setQuizzs(new ArrayList<QuizzPlayer>());

	return anIndex;
    }

    private void fillQuizzPlayer(Cursor aCursor, int anIndex, Match aMatch) {
	QuizzPlayer quizzPlayer = new QuizzPlayer();
	quizzPlayer.setMatch(aMatch);
	quizzPlayer.setId(aCursor.getLong(anIndex));

	anIndex++;
	quizzPlayer.setEarnCredit(aCursor.getInt(anIndex));

	anIndex++;
	quizzPlayer.setX(aCursor.getInt(anIndex));

	anIndex++;
	quizzPlayer.setY(aCursor.getInt(anIndex));

	anIndex++;
	quizzPlayer.setHide(BooleanUtils.toBoolean(aCursor.getInt(anIndex)));

	anIndex++;
	quizzPlayer.setHome(BooleanUtils.toBoolean(aCursor.getInt(anIndex)));

	anIndex++;
	quizzPlayer.setCoach(BooleanUtils.toBoolean(aCursor.getInt(anIndex)));

	anIndex++;
	quizzPlayer.setCsc(aCursor.getInt(anIndex));

	anIndex++;
	quizzPlayer.setGoal(aCursor.getInt(anIndex));

	anIndex++;
	quizzPlayer.setHint(aCursor.getString(anIndex));

	anIndex++;
	quizzPlayer.setCaptain(BooleanUtils.toBoolean(aCursor.getInt(anIndex)));

	anIndex++;
	quizzPlayer.setCreditToUnlockHint(aCursor.getInt(anIndex));

	anIndex++;
	quizzPlayer.setCreditToUnlockRandom(aCursor.getInt(anIndex));

	anIndex++;
	quizzPlayer.setCreditToUnlockHalf(aCursor.getInt(anIndex));

	anIndex++;
	quizzPlayer.setCreditToUnlockResponse(aCursor.getInt(anIndex));

	Team team = new Team();
	anIndex++;
	team.setId(aCursor.getLong(anIndex));

	anIndex++;
	team.setCode(aCursor.getString(anIndex));

	anIndex++;
	team.setName(aCursor.getString(anIndex));

	anIndex++;
	String s = aCursor.getString(anIndex);
	if (StringUtils.isNotBlank(s)) {
	    team.setHomeJerseyColor(ColorEnum.valueOf(s));
	}

	anIndex++;
	s = aCursor.getString(anIndex);
	if (StringUtils.isNotBlank(s)) {
	    team.setHomeShortColor(ColorEnum.valueOf(s));
	}

	anIndex++;
	s = aCursor.getString(anIndex);
	if (StringUtils.isNotBlank(s)) {
	    team.setHomeSockColor(ColorEnum.valueOf(s));
	}

	anIndex++;
	s = aCursor.getString(anIndex);
	if (StringUtils.isNotBlank(s)) {
	    team.setAwayJerseyColor(ColorEnum.valueOf(s));
	}

	anIndex++;
	s = aCursor.getString(anIndex);
	if (StringUtils.isNotBlank(s)) {
	    team.setAwayShortColor(ColorEnum.valueOf(s));
	}

	anIndex++;
	s = aCursor.getString(anIndex);
	if (StringUtils.isNotBlank(s)) {
	    team.setAwaySockColor(ColorEnum.valueOf(s));
	}
	quizzPlayer.setTeam(team);

	Player player = new Player();
	anIndex++;
	player.setId(aCursor.getLong(anIndex));

	anIndex++;
	player.setName(aCursor.getString(anIndex));
	quizzPlayer.setPlayer(player);

	aMatch.getQuizzs().add(quizzPlayer);
    }

    private String createMatchColumnRequest(String aPrefixe) {
	StringBuffer req = new StringBuffer();
	// Index 0
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable._ID);
	req.append(", ");
	// Index 1
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_NAME);
	req.append(", ");
	// Index 2
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_DATE);
	req.append(", ");
	// Index 3
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_SCORE_AWAY);
	req.append(", ");
	// Index 4
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_SCORE_HOME);
	req.append(", ");
	// Index 5
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_IS_OVERTIME);
	req.append(", ");
	// Index 6
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_SOG_HOME);
	req.append(", ");
	// Index 7
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_SOG_AWAY);
	req.append(", ");
	// Index 8
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.MatchTable.COLUMN_ORDER_NUMBER);
	req.append(", ");

	return req.toString();
    }

    private String createQuizzPlayerColumnRequest(String aPrefixe) {
	StringBuffer req = new StringBuffer();

	// Index 9
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable._ID);
	req.append(", ");
	// Index 10
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_EARN_CREDIT);
	req.append(", ");
	// Index 11
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_X);
	req.append(", ");
	// Index 12
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_Y);
	req.append(", ");
	// Index 13
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_HIDE);
	req.append(", ");
	// Index 14
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_HOME);
	req.append(", ");
	// Index 15
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_COACH);
	req.append(", ");
	// Index 16
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_CSC);
	req.append(", ");
	// Index 17
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_GOAL);
	req.append(", ");
	// Index 18
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_HINT);
	req.append(", ");
	// Index 19
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_CAPTAIN);
	req.append(", ");
	// Index 20
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_CREDIT_TO_UNLOCK_HINT);
	req.append(", ");
	// Index 21
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_CREDIT_TO_UNLOCK_RANDOM);
	req.append(", ");
	// Index 22
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_CREDIT_TO_UNLOCK_HALF);
	req.append(", ");
	// Index 23
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_CREDIT_TO_UNLOCK_RESPONSE);
	req.append(", ");

	return req.toString();
    }

    private String createTeamColumnRequest(String aPrefixe) {
	StringBuffer req = new StringBuffer();

	// Index 24
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable._ID);
	req.append(", ");
	// Index 25
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable.COLUMN_CODE);
	req.append(", ");
	// Index 26
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable.COLUMN_NAME);
	req.append(", ");
	// Index 27
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable.COLUMN_HOME_JERSY_COLOR);
	req.append(", ");
	// Index 28
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable.COLUMN_HOME_SHORT_COLOR);
	req.append(", ");
	// Index 29
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable.COLUMN_HOME_SOCK_COLOR);
	req.append(", ");
	// Index 30
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable.COLUMN_AWAY_JERSY_COLOR);
	req.append(", ");
	// Index 31
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable.COLUMN_AWAY_SHORT_COLOR);
	req.append(", ");
	// Index 32
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.TeamTable.COLUMN_AWAY_SOCK_COLOR);
	req.append(", ");

	return req.toString();
    }

    private String createPlayerColumnRequest(String aPrefixe) {
	StringBuffer req = new StringBuffer();

	// Index 33
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.PlayerTable._ID);
	req.append(", ");
	// Index 34
	req.append(aPrefixe);
	req.append(".");
	req.append(TableConstant.PlayerTable.COLUMN_NAME);
	req.append(" ");

	return req.toString();
    }

}
