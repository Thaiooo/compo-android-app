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
    private int point;
    private int credit;
    private long overallTime;
    private Sound sound;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public int getPoint() {
	return point;
    }

    public void setPoint(int point) {
	this.point = point;
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

    public Sound getSound() {
	return sound;
    }

    public void setSound(Sound sound) {
	this.sound = sound;
    }

}
