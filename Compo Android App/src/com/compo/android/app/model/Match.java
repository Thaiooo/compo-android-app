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
    private boolean overtime;
    private Integer sogHome;
    private Integer sogAway;
    private List<QuizzPlayer> quizzs = new ArrayList<QuizzPlayer>();

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
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

    public boolean isOvertime() {
	return overtime;
    }

    public void setOvertime(boolean overtime) {
	this.overtime = overtime;
    }

    public Integer getSogHome() {
	return sogHome;
    }

    public void setSogHome(Integer sogHome) {
	this.sogHome = sogHome;
    }

    public Integer getSogAway() {
	return sogAway;
    }

    public void setSogAway(Integer sogAway) {
	this.sogAway = sogAway;
    }

}
