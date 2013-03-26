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

import com.compo.android.app.model.Level;
import com.compo.android.app.model.Player;
import com.compo.android.app.model.Quizz;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;

public class QuizzDao {
    private DataBaseHelper dataBaseHeleper;

    public QuizzDao(Context context) {
	dataBaseHeleper = new DataBaseHelper(context);
    }

    public List<Quizz> getAllQuizz(long aPackId, Level aLevel) {
	dataBaseHeleper.openDataBase();
	SQLiteDatabase session = dataBaseHeleper.getReadableDatabase();

	String[] selectionArgs = { String.valueOf(aPackId), aLevel.name() };

	StringBuffer req = new StringBuffer("select ");
	// Index 0
	req.append("q.");
	req.append(TableConstant.QuizzTable._ID);
	req.append(", ");
	// Index 1
	req.append("q.");
	req.append(TableConstant.QuizzTable.COLUMN_NAME);
	req.append(", ");
	// Index 2
	req.append("q.");
	req.append(TableConstant.QuizzTable.COLUMN_LEVEL);
	req.append(", ");
	// Index 3
	req.append("q.");
	req.append(TableConstant.QuizzTable.COLUMN_DATE);
	req.append(", ");
	// Index 4
	req.append("q.");
	req.append(TableConstant.QuizzTable.COLUMN_POINT);
	req.append(", ");
	// Index 5
	req.append("q.");
	req.append(TableConstant.QuizzTable.COLUMN_SCORE_AWAY);
	req.append(", ");
	// Index 6
	req.append("q.");
	req.append(TableConstant.QuizzTable.COLUMN_SCORE_HOME);
	req.append(", ");
	// ---------------------------------------------------------------------------------
	// Index 7
	req.append("qp.");
	req.append(TableConstant.QuizzPlayerTable._ID);
	req.append(", ");
	// Index 8
	req.append("qp.");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_X);
	req.append(", ");
	// Index 9
	req.append("qp.");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_Y);
	req.append(", ");
	// Index 10
	req.append("qp.");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_HIDE);
	req.append(", ");
	// Index 11
	req.append("qp.");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_HOME);
	req.append(", ");
	// Index 12
	req.append("qp.");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_COACH);
	req.append(", ");
	// ---------------------------------------------------------------------------------
	// Index 13
	req.append("qp.");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_TEAM_ID);
	req.append(", ");
	// Index 14
	req.append("t.");
	req.append(TableConstant.TeamTable.COLUMN_CODE);
	req.append(", ");
	// Index 15
	req.append("t.");
	req.append(TableConstant.TeamTable.COLUMN_NAME);
	req.append(", ");
	// ---------------------------------------------------------------------------------
	// Index 16
	req.append("qp.");
	req.append(TableConstant.QuizzPlayerTable.COLUMN_PLAYER_ID);
	req.append(", ");
	// Index 17
	req.append("p.");
	req.append(TableConstant.PlayerTable.COLUMN_NAME);
	req.append(" ");

	req.append("from " + TableConstant.QuizzTable.TABLE_NAME + " q ");
	req.append("left join " + TableConstant.QuizzPlayerTable.TABLE_NAME + " qp on q."
		+ TableConstant.QuizzTable._ID + " = qp." + TableConstant.QuizzPlayerTable.COLUMN_QUIZZ_ID + " ");

	req.append("left join " + TableConstant.TeamTable.TABLE_NAME + " t on qp."
		+ TableConstant.QuizzPlayerTable.COLUMN_TEAM_ID + " = t." + TableConstant.TeamTable._ID + " ");

	req.append("left join " + TableConstant.PlayerTable.TABLE_NAME + " p on qp."
		+ TableConstant.QuizzPlayerTable.COLUMN_PLAYER_ID + " = p." + TableConstant.PlayerTable._ID + " ");

	req.append("where q." + TableConstant.QuizzTable.COLUMN_PACK_ID + " = ? ");
	req.append("and q." + TableConstant.QuizzTable.COLUMN_LEVEL + " = ? ");
	req.append("order by q." + TableConstant.QuizzTable.COLUMN_ORDER_NUMBER + " asc ");

	Cursor c = session.rawQuery(req.toString(), selectionArgs);

	List<Quizz> l = new ArrayList<Quizz>();
	Map<Long, Quizz> mapQuizz = new HashMap<Long, Quizz>();

	while (c.moveToNext()) {
	    long quizzId = c.getLong(0);

	    Quizz quizz = mapQuizz.get(quizzId);
	    if (quizz == null) {
		quizz = new Quizz();
		String quizzName = c.getString(1);
		Level quizzLevel = Level.valueOf(c.getString(2));
		String quizzDate = c.getString(3);
		int quizzPoint = c.getInt(4);
		int quizzScoreAway = c.getInt(5);
		int quizzScoreHome = c.getInt(6);
		quizz.setId(quizzId);
		quizz.setName(quizzName);
		try {
		    java.util.Date d = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(quizzDate);
		    Date date = new Date(d.getTime());
		    quizz.setDate(date);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		quizz.setLevel(quizzLevel);
		quizz.setPoint(quizzPoint);
		quizz.setScoreAway(quizzScoreAway);
		quizz.setScoreHome(quizzScoreHome);
		quizz.setQuizzList(new ArrayList<QuizzPlayer>());

		if (c.getLong(7) != 0) {
		    QuizzPlayer quizzPlayer = new QuizzPlayer();
		    quizzPlayer.setId(c.getLong(7));
		    quizzPlayer.setX(c.getInt(8));
		    quizzPlayer.setY(c.getInt(9));
		    quizzPlayer.setHide(Boolean.parseBoolean(c.getString(10)));
		    quizzPlayer.setHome(Boolean.parseBoolean(c.getString(11)));
		    quizzPlayer.setCoach(Boolean.parseBoolean(c.getString(12)));

		    Team team = new Team();
		    team.setId(c.getLong(13));
		    team.setCode(c.getString(14));
		    team.setName(c.getString(15));
		    quizzPlayer.setTeam(team);

		    Player player = new Player();
		    player.setId(c.getLong(16));
		    player.setName(c.getString(17));
		    quizzPlayer.setPlayer(player);

		    quizz.getQuizzList().add(quizzPlayer);
		}
		mapQuizz.put(quizzId, quizz);

		l.add(quizz);
	    } else {
		if (c.getLong(7) != 0) {
		    QuizzPlayer quizzPlayer = new QuizzPlayer();
		    quizzPlayer.setId(c.getLong(7));
		    quizzPlayer.setX(c.getInt(8));
		    quizzPlayer.setY(c.getInt(9));
		    quizzPlayer.setHide(Boolean.parseBoolean(c.getString(10)));
		    quizzPlayer.setHome(Boolean.parseBoolean(c.getString(11)));
		    quizzPlayer.setCoach(Boolean.parseBoolean(c.getString(12)));

		    Team team = new Team();
		    team.setId(c.getLong(13));
		    team.setCode(c.getString(14));
		    team.setName(c.getString(15));
		    quizzPlayer.setTeam(team);

		    Player player = new Player();
		    player.setId(c.getLong(16));
		    player.setName(c.getString(17));
		    quizzPlayer.setPlayer(player);

		    quizz.getQuizzList().add(quizzPlayer);
		}
	    }
	}

	dataBaseHeleper.close();
	return l;
    }
}
