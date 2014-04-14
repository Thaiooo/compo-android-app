package com.compo.android.app.utils;

public enum FontEnum {
    DEFAULT("SFCollegiateSolid-Bold.ttf"),
    LUCKY_PENNY("MyLuckyPenny.ttf"), ERASER("Eraser.ttf"), SCORE_FONT("light_led_board.ttf"), DRAWING_GUIDES(
	    "DrawingGuides.ttf");

    private String name;

    private FontEnum(String aName) {
	name = aName;
    }

    public String getName() {
	return name;
    }

}
