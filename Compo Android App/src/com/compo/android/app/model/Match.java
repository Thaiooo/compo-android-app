package com.compo.android.app.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Match implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private Date date;
    private int scoreHome;
    private int scoreAway;
    private Level level;
    private int point;
    private List<QuizzPlayer> quizzs = new ArrayList<QuizzPlayer>();

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

    public Level getLevel() {
	return level;
    }

    public void setLevel(Level level) {
	this.level = level;
    }

    public List<QuizzPlayer> getQuizzs() {
	return quizzs;
    }

    public void setQuizzs(List<QuizzPlayer> quizzList) {
	this.quizzs = quizzList;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public int getScoreHome() {
	return scoreHome;
    }

    public void setScoreHome(int scoreHome) {
	this.scoreHome = scoreHome;
    }

    public int getScoreAway() {
	return scoreAway;
    }

    public void setScoreAway(int scoreAway) {
	this.scoreAway = scoreAway;
    }

}
