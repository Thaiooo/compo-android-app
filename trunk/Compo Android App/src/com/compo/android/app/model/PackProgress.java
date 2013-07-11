package com.compo.android.app.model;

import java.io.Serializable;

public class PackProgress implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private Theme theme;
    private int match;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Theme getTheme() {
	return theme;
    }

    public void setTheme(Theme theme) {
	this.theme = theme;
    }

    public int getMatch() {
	return match;
    }

    public void setMatch(int match) {
	this.match = match;
    }

}
