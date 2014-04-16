package com.compo.android.app.utils;

public enum FontEnum {
	/***/
	ACTIVITY_TITLE("FiraSansOT-Regular.otf"),

	/***/
	THEME_NAME("FiraSansOT-Regular.otf"),

	/***/
	PACK_NAME("FiraSansOT-Bold.otf"),
	/***/
	PACK_DESC("FiraSansOT-Regular.otf"),
	/***/
	PACK_PROGRESS("FiraSansOT-Regular.otf"),

	/***/
	MATCH_NAME("FiraSansOT-Regular.otf"),
	/***/
	MATCH_DESC("FiraSansOT-Regular.otf"),
	/***/
	MATCH_SCORE("FiraSansOT-Regular.otf"),

	/***/
	DIALOG_TITLE("FiraSansOT-Regular.otf"),
	/***/
	DIALOG_CONTENT("FiraSansOT-Regular.otf"),

	/***/
	RESPONSE_CONTENT("FiraSansOT-Regular.otf"),

	/***/
	BUTTON("FiraSansOT-Regular.otf"),
	/***/
	FIELD_TITLE("FiraSansOT-Regular.otf"),
	/***/
	SCORE_FONT("light_led_board.ttf");

	private String name;

	private FontEnum(String aName) {
		name = aName;
	}

	public String getName() {
		return name;
	}

}
