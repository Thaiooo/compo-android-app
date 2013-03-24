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
    private Team team;
    private Player player;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
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

}
