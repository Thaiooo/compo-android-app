package com.compo.android.app.model;

import java.io.Serializable;

public class PackProgress implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private Pack pack;
    private int match;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Pack getPack() {
	return pack;
    }

    public void setPack(Pack pack) {
	this.pack = pack;
    }

    public int getMatch() {
	return match;
    }

    public void setMatch(int match) {
	this.match = match;
    }

}
