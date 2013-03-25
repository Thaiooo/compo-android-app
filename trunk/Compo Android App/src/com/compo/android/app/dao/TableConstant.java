package com.compo.android.app.dao;

import android.provider.BaseColumns;

public class TableConstant {

    private TableConstant() {
    }

    public static class PackTable implements BaseColumns {
	public static final String TABLE_NAME = "pack";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_LOCK = "lock";
	public static final String COLUMN_SCORE_LIMIT = "score_limit";
	public static final String COLUMN_CREDIT_LIMIT = "credit_limit";
	public static final String COLUMN_ORDER_NUMBER = "order_number";
    }

    public static class UserTable implements BaseColumns {
	public static final String TABLE_NAME = "user";
	public static final String COLUMN_CREDIT = "credit";
	public static final String COLUMN_POINT = "point";
	public static final String COLUMN_OVERALL_TIME = "overall_time";
    }

    public static class QuizzTable implements BaseColumns {
	public static final String TABLE_NAME = "quizz";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_SCORE_HOME = "score_home";
	public static final String COLUMN_SCORE_AWAY = "score_away";
	public static final String COLUMN_LEVEL = "level";
	public static final String COLUMN_POINT = "point";
	public static final String COLUMN_ORDER_NUMBER = "order_number";
	public static final String COLUMN_PACK_ID = "pack_id";
    }

    public static class QuizzPlayerTable implements BaseColumns {
	public static final String TABLE_NAME = "quizz_player";
	public static final String COLUMN_QUIZZ_ID = "quizz_id";
	public static final String COLUMN_HIDE = "hide";
	public static final String COLUMN_HOME = "home";
	public static final String COLUMN_X = "x";
	public static final String COLUMN_Y = "y";
	public static final String COLUMN_TEAM_ID = "team_id";
	public static final String COLUMN_PLAYER_ID = "player_id";
    }

    public static class TeamTable implements BaseColumns {
	public static final String TABLE_NAME = "team";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CODE = "code";
    }

    public static class PlayerTable implements BaseColumns {
	public static final String TABLE_NAME = "player";
	public static final String COLUMN_NAME = "name";
    }
}
