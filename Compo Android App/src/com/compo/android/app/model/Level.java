package com.compo.android.app.model;

public enum Level {
    /**/
    EASY(0),
    /**/
    INTERMEDIATE(1),
    /**/
    HARD(2);

    int index;

    Level(int anIndex) {
	index = anIndex;
    }

    public static Level getLevel(int anIndex) {
	switch (anIndex) {
	case 0:
	    return EASY;
	case 1:
	    return INTERMEDIATE;
	default:
	    return HARD;
	}
    }
}
