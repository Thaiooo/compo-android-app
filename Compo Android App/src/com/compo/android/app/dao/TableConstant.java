package com.compo.android.app.dao;

import android.provider.BaseColumns;

public class TableConstant {

    private TableConstant() {
    }

    public static class ThemeTable implements BaseColumns {
	public static final String TABLE_NAME = "theme";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_IS_LOCK = "is_lock";
	public static final String COLUMN_CREDIT_LIMIT = "credit_limit";
	public static final String COLUMN_ORDER_NUMBER = "order_number";
    }

    public static class PackTable implements BaseColumns {
	public static final String TABLE_NAME = "pack";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_ORDER_NUMBER = "order_number";
	public static final String COLUMN_THEME_ID = "theme_id";
    }

    public static class UserTable implements BaseColumns {
	public static final String TABLE_NAME = "user";
	public static final String COLUMN_CREDIT = "credit";
	public static final String COLUMN_OVERALL_TIME = "overall_time";
	public static final String COLUMN_IS_SOUND_ENABLE = "is_sound_enable";
    }

    public static class MatchTable implements BaseColumns {
	public static final String TABLE_NAME = "match";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_SCORE_HOME = "score_home";
	public static final String COLUMN_SCORE_AWAY = "score_away";
	public static final String COLUMN_ORDER_NUMBER = "order_number";
	public static final String COLUMN_PACK_ID = "pack_id";
	public static final String COLUMN_IS_OVERTIME = "is_overtime";
	public static final String COLUMN_SOG_HOME = "sog_home";
	public static final String COLUMN_SOG_AWAY = "sog_away";
    }

    public static class PlayTable implements BaseColumns {
	public static final String TABLE_NAME = "play";
	public static final String COLUMN_DATE_TIME = "date_time";
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_QUIZZ_ID = "quizz_id";
	public static final String COLUMN_RESPONSE = "response";
	public static final String COLUMN_IS_UNLOCK_HINT = "is_unlock_hint";
	public static final String COLUMN_IS_UNLOCK_RANDOM = "is_unlock_random";
	public static final String COLUMN_IS_UNLOCK_50_PERCENT = "is_unlock_50_percent";
	public static final String COLUMN_IS_UNLOCK_RESPONSE = "is_unlock_response";
    }

    public static class QuizzPlayerTable implements BaseColumns {
	public static final String TABLE_NAME = "quizz_player";
	public static final String COLUMN_QUIZZ_ID = "quizz_id";
	public static final String COLUMN_IS_HIDE = "is_hide";
	public static final String COLUMN_IS_HOME = "is_home";
	public static final String COLUMN_IS_COACH = "is_coach";
	public static final String COLUMN_X = "x";
	public static final String COLUMN_Y = "y";
	public static final String COLUMN_CSC = "csc";
	public static final String COLUMN_GOAL = "goal";
	public static final String COLUMN_TEAM_ID = "team_id";
	public static final String COLUMN_PLAYER_ID = "player_id";
	public static final String COLUMN_EARN_CREDIT = "earn_credit";
	public static final String COLUMN_HINT = "hint";
	public static final String COLUMN_IS_CAPTAIN = "is_captain";
    }

    public static class TeamTable implements BaseColumns {
	public static final String TABLE_NAME = "team";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_HOME_JERSY_COLOR = "home_jersey_color";
	public static final String COLUMN_AWAY_JERSY_COLOR = "away_jersey_color";
	public static final String COLUMN_HOME_SHORT_COLOR = "home_short_color";
	public static final String COLUMN_AWAY_SHORT_COLOR = "away_short_color";
	public static final String COLUMN_HOME_SOCK_COLOR = "home_sock_color";
	public static final String COLUMN_AWAY_SOCK_COLOR = "away_sock_color";
    }

    public static class PlayerTable implements BaseColumns {
	public static final String TABLE_NAME = "player";
	public static final String COLUMN_NAME = "name";
    }

    public static class PackProgressTable implements BaseColumns {
	public static final String TABLE_NAME = "pack_progress";
	public static final String COLUMN_MATCH = "match";
	public static final String COLUMN_PACK_ID = "pack_id";
    }
}
