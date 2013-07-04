package com.compo.android.app.model;

import java.io.Serializable;
import java.util.Date;

public class Play implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private Date dateTime;
    private String response;
    private long quizzId;
    private long userId;

    public long getQuizzId() {
	return quizzId;
    }

    public void setQuizzId(long quizzId) {
	this.quizzId = quizzId;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Date getDateTime() {
	return dateTime;
    }

    public void setDateTime(Date dateTime) {
	this.dateTime = dateTime;
    }

    public String getResponse() {
	return response;
    }

    public void setResponse(String response) {
	this.response = response;
    }

}
