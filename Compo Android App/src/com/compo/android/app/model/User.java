package com.compo.android.app.model;

import java.io.Serializable;

public class User implements Serializable {

    public enum Sound {
	ON, OFF
    }

    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private int credit;
    private long overallTime;
    private Sound soundEnable;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public int getCredit() {
	return credit;
    }

    public void setCredit(int credit) {
	this.credit = credit;
    }

    public long getOverallTime() {
	return overallTime;
    }

    public void setOverallTime(long overallTime) {
	this.overallTime = overallTime;
    }

    public Sound getSoundEnable() {
	return soundEnable;
    }

    public void setSoundEnable(Sound sound) {
	this.soundEnable = sound;
    }

}
