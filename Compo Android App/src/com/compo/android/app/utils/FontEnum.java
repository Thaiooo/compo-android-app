package com.compo.android.app.utils;

public enum FontEnum {
    /***/
    ACTIVITY_TITLE("FiraSansOT-Regular.otf"),

    /***/
    THEME_NAME("FiraSansOT-Regular.otf"),

    /***/
    PACK_NAME("FiraSansOT-Regular.otf"),
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
    DIALOG_TITLE("FiraSansOT-Bold.otf"),
    /***/
    DIALOG_CONTENT("FiraSansOT-Regular.otf"),
    /***/
    BUTTON("FiraSansOT-Regular.otf"),
    /***/
    DEFAULT("SFCollegiateSolid-Bold.ttf"),
    /***/
    LUCKY_PENNY("MyLuckyPenny.ttf"),
    /***/
    ERASER("Eraser.ttf"),
    /***/
    SCORE_FONT("light_led_board.ttf"),
    /***/
    DRAWING_GUIDES("DrawingGuides.ttf");

    private String name;

    private FontEnum(String aName) {
	name = aName;
    }

    public String getName() {
	return name;
    }

}
