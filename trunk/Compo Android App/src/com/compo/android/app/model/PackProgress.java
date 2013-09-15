package com.compo.android.app.model;

import java.io.Serializable;

public class PackProgress implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private Pack pack;
    /**
     * Number of success match
     */
    private int numberOfsuccessMatch;

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

    public int getNumberOfSuccessMatch() {
	return numberOfsuccessMatch;
    }

    public void setNumberOfSuccessMatch(int match) {
	this.numberOfsuccessMatch = match;
    }

}
