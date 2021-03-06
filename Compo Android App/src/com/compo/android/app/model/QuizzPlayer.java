package com.compo.android.app.model;

import java.io.Serializable;

public class QuizzPlayer implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;
    private long id;
    private int x;
    private int y;
    private boolean hide;
    private boolean home;
    private boolean coach;
    private Team team;
    private Player player;
    private int goal;
    private int csc;
    private int earnCredit;
    private String hint;
    private boolean captain;
    private int creditToUnlockHint;
    private int creditToUnlockRandom;
    private int creditToUnlockHalf;
    private int creditToUnlockResponse;
    private Match match;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public int getEarnCredit() {
	return earnCredit;
    }

    public void setEarnCredit(int earnCredit) {
	this.earnCredit = earnCredit;
    }

    public Team getTeam() {
	return team;
    }

    public void setTeam(Team team) {
	this.team = team;
    }

    public Player getPlayer() {
	return player;
    }

    public void setPlayer(Player player) {
	this.player = player;
    }

    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    public boolean isHide() {
	return hide;
    }

    public void setHide(boolean hide) {
	this.hide = hide;
    }

    public boolean isHome() {
	return home;
    }

    public void setHome(boolean home) {
	this.home = home;
    }

    public boolean isCoach() {
	return coach;
    }

    public void setCoach(boolean coach) {
	this.coach = coach;
    }

    public int getGoal() {
	return goal;
    }

    public void setGoal(int goal) {
	this.goal = goal;
    }

    public int getCsc() {
	return csc;
    }

    public void setCsc(int csc) {
	this.csc = csc;
    }

    public String getHint() {
	return hint;
    }

    public void setHint(String hint) {
	this.hint = hint;
    }

    public boolean isCaptain() {
	return captain;
    }

    public void setCaptain(boolean captain) {
	this.captain = captain;
    }

    public int getCreditToUnlockHint() {
	return creditToUnlockHint;
    }

    public void setCreditToUnlockHint(int creditToUnlockHint) {
	this.creditToUnlockHint = creditToUnlockHint;
    }

    public int getCreditToUnlockRandom() {
	return creditToUnlockRandom;
    }

    public void setCreditToUnlockRandom(int creditToUnlockRandom) {
	this.creditToUnlockRandom = creditToUnlockRandom;
    }

    public int getCreditToUnlockHalf() {
	return creditToUnlockHalf;
    }

    public void setCreditToUnlockHalf(int creditToUnlockHalf) {
	this.creditToUnlockHalf = creditToUnlockHalf;
    }

    public int getCreditToUnlockResponse() {
	return creditToUnlockResponse;
    }

    public void setCreditToUnlockResponse(int creditToUnlockResponse) {
	this.creditToUnlockResponse = creditToUnlockResponse;
    }

    public Match getMatch() {
	return match;
    }

    public void setMatch(Match match) {
	this.match = match;
    }

}
