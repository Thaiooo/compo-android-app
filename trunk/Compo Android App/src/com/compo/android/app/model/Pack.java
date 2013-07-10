package com.compo.android.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pack implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String description;
    private boolean lock;
    private int creditLimit;
    private List<Match> matchs = new ArrayList<Match>();

    public boolean isLock() {
	return lock;
    }

    public void setLock(boolean lock) {
	this.lock = lock;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Match> getMatchs() {
	return matchs;
    }

    public void setMatchs(List<Match> aMatchs) {
	this.matchs = aMatchs;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public int getCreditLimit() {
	return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
	this.creditLimit = creditLimit;
    }

}
