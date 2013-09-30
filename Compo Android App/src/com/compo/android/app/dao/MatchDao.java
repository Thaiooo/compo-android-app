package com.compo.android.app.dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.compo.android.app.model.ColorEnum;
import com.compo.android.app.model.Match;
import com.compo.android.app.model.Player;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;

public class MatchDao {
    private DataBaseHelper dataBaseHeleper;

    public MatchDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public List<Match> getAllQuizz(long aPackId) {
	List<Match> l = new ArrayList<Match>();

	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = null;
	Cursor c = null;
	try {
	    session = dataBaseHeleper.getReadableDatabase();
	    String[] selectionArgs = { String.valueOf(aPackId) };

	    StringBuffer req = new StringBuffer("select ");
	    // Index 0
	    req.append("q.");
	    req.append(TableConstant.MatchTable._ID);
	    req.append(", ");
	    // Index 1
	    req.append("q.");
	    req.append(TableConstant.MatchTable.COLUMN_NAME);
	    req.append(", ");
	    // Index 2
	    req.append("q.");
	    req.append(TableConstant.MatchTable.COLUMN_DATE);
	    req.append(", ");
	    // Index 3
	    req.append("q.");
	    req.append(TableConstant.MatchTable.COLUMN_SCORE_AWAY);
	    req.append(", ");
	    // Index 4
	    req.append("q.");
	    req.append(TableConstant.MatchTable.COLUMN_SCORE_HOME);
	    req.append(", ");
	    // Index 5
	    req.append("q.");
	    req.append(TableConstant.MatchTable.COLUMN_IS_OVERTIME);
	    req.append(", ");
	    // Index 6
	    req.append("q.");
	    req.append(TableConstant.MatchTable.COLUMN_SOG_HOME);
	    req.append(", ");
	    // Index 7
	    req.append("q.");
	    req.append(TableConstant.MatchTable.COLUMN_SOG_AWAY);
	    req.append(", ");
	    // ---------------------------------------------------------------------------------
	    // Index 8
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable._ID);
	    req.append(", ");
	    // Index 9
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_EARN_CREDIT);
	    req.append(", ");
	    // Index 10
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_X);
	    req.append(", ");
	    // Index 11
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_Y);
	    req.append(", ");
	    // Index 12
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_HIDE);
	    req.append(", ");
	    // Index 13
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_HOME);
	    req.append(", ");
	    // Index 14
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_COACH);
	    req.append(", ");
	    // Index 15
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_CSC);
	    req.append(", ");
	    // Index 16
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_GOAL);
	    req.append(", ");
	    // Index 17
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_HINT);
	    req.append(", ");
	    // Index 18
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_IS_CAPTAIN);
	    req.append(", ");
	    // Index 19
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_CREDIT_TO_UNLOCK_HINT);
	    req.append(", ");
	    // Index 20
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_CREDIT_TO_UNLOCK_RANDOM);
	    req.append(", ");
	    // Index 21
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_CREDIT_TO_UNLOCK_HALF);
	    req.append(", ");
	    // Index 22
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_CREDIT_TO_UNLOCK_RESPONSE);
	    req.append(", ");

	    // ---------------------------------------------------------------------------------
	    // Index 23
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_TEAM_ID);
	    req.append(", ");
	    // Index 24
	    req.append("t.");
	    req.append(TableConstant.TeamTable.COLUMN_CODE);
	    req.append(", ");
	    // Index 25
	    req.append("t.");
	    req.append(TableConstant.TeamTable.COLUMN_NAME);
	    req.append(", ");
	    // Index 26
	    req.append("t.");
	    req.append(TableConstant.TeamTable.COLUMN_HOME_JERSY_COLOR);
	    req.append(", ");
	    // Index 27
	    req.append("t.");
	    req.append(TableConstant.TeamTable.COLUMN_HOME_SHORT_COLOR);
	    req.append(", ");
	    // Index 28
	    req.append("t.");
	    req.append(TableConstant.TeamTable.COLUMN_HOME_SOCK_COLOR);
	    req.append(", ");
	    // Index 29
	    req.append("t.");
	    req.append(TableConstant.TeamTable.COLUMN_AWAY_JERSY_COLOR);
	    req.append(", ");
	    // Index 30
	    req.append("t.");
	    req.append(TableConstant.TeamTable.COLUMN_AWAY_SHORT_COLOR);
	    req.append(", ");
	    // Index 31
	    req.append("t.");
	    req.append(TableConstant.TeamTable.COLUMN_AWAY_SOCK_COLOR);
	    req.append(", ");

	    // ---------------------------------------------------------------------------------
	    // Index 32
	    req.append("qp.");
	    req.append(TableConstant.QuizzPlayerTable.COLUMN_PLAYER_ID);
	    req.append(", ");
	    // Index 33
	    req.append("p.");
	    req.append(TableConstant.PlayerTable.COLUMN_NAME);
	    req.append(" ");

	    req.append("from " + TableConstant.MatchTable.TABLE_NAME + " q ");
	    req.append("left join " + TableConstant.QuizzPlayerTable.TABLE_NAME + " qp on q."
		    + TableConstant.MatchTable._ID + " = qp." + TableConstant.QuizzPlayerTable.COLUMN_MATCH_ID + " ");

	    req.append("left join " + TableConstant.TeamTable.TABLE_NAME + " t on qp."
		    + TableConstant.QuizzPlayerTable.COLUMN_TEAM_ID + " = t." + TableConstant.TeamTable._ID + " ");

	    req.append("left join " + TableConstant.PlayerTable.TABLE_NAME + " p on qp."
		    + TableConstant.QuizzPlayerTable.COLUMN_PLAYER_ID + " = p." + TableConstant.PlayerTable._ID + " ");

	    req.append("where q." + TableConstant.MatchTable.COLUMN_PACK_ID + " = ? ");
	    req.append("order by q." + TableConstant.MatchTable.COLUMN_ORDER_NUMBER + " asc ");

	    c = session.rawQuery(req.toString(), selectionArgs);

	    Map<Long, Match> mapQuizz = new HashMap<Long, Match>();

	    while (c.moveToNext()) {
		int index = 0;
		long quizzId = c.getLong(index);
		Match match = mapQuizz.get(quizzId);

		index++;
		if (match == null) {
		    match = new Match();

		    String quizzName = c.getString(index);
		    index++;
		    String quizzDate = c.getString(index);
		    index++;
		    int quizzScoreAway = c.getInt(index);
		    index++;
		    int quizzScoreHome = c.getInt(index);
		    index++;
		    boolean isOvertime = Boolean.parseBoolean(c.getString(index));
		    index++;
		    String quizzSogHome = c.getString(index);
		    index++;
		    String quizzSogAway = c.getString(index);

		    match.setId(quizzId);
		    match.setName(quizzName);
		    try {
			java.util.Date d = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(quizzDate);
			Date date = new Date(d.getTime());
			match.setDate(date);
		    } catch (ParseException e) {
			e.printStackTrace();
		    }
		    match.setScoreAway(quizzScoreAway);
		    match.setScoreHome(quizzScoreHome);
		    match.setOvertime(isOvertime);
		    if (StringUtils.isNotBlank(quizzSogHome)) {
			match.setSogHome(Integer.getInteger(quizzSogHome));
		    }
		    if (StringUtils.isNotBlank(quizzSogAway)) {
			match.setSogAway(Integer.getInteger(quizzSogAway));
		    }
		    match.setQuizzs(new ArrayList<QuizzPlayer>());

		    index++;
		    if (c.getLong(index) != 0) {
			fillQuizzPlayer(c, index, match);
		    }
		    mapQuizz.put(quizzId, match);

		    l.add(match);
		} else {
		    index = 8;
		    if (c.getLong(index) != 0) {
			fillQuizzPlayer(c, index, match);
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
	quizzPlayer.setHide(Boolean.parseBoolean(aCursor.getString(anIndex)));
	anIndex++;
	quizzPlayer.setHome(Boolean.parseBoolean(aCursor.getString(anIndex)));
	anIndex++;
	quizzPlayer.setCoach(Boolean.parseBoolean(aCursor.getString(anIndex)));
	anIndex++;
	quizzPlayer.setCsc(aCursor.getInt(anIndex));
	anIndex++;
	quizzPlayer.setGoal(aCursor.getInt(anIndex));
	anIndex++;
	quizzPlayer.setHint(aCursor.getString(anIndex));
	anIndex++;
	quizzPlayer.setCaptain(Boolean.parseBoolean(aCursor.getString(anIndex)));
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
}
