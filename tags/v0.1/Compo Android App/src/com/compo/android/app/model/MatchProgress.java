package com.compo.android.app.model;

import java.io.Serializable;

public class MatchProgress implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private Match match;
    /**
     * Number of success quizz
     */
    private int numberOfSuccessQuizz;

    /**
     * Indicate if all the quizz of the match is completed
     */
    private boolean completed;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Match getMatch() {
	return match;
    }

    public void setMatch(Match match) {
	this.match = match;
    }

    public int getNumberOfSuccessQuizz() {
	return numberOfSuccessQuizz;
    }

    public void setNumberOfSuccessQuizz(int numberOfSuccessQuizz) {
	this.numberOfSuccessQuizz = numberOfSuccessQuizz;
    }

    public boolean isCompleted() {
	return completed;
    }

    public void setCompleted(boolean completed) {
	this.completed = completed;
    }

}
