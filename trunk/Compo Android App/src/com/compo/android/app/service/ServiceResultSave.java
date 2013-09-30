package com.compo.android.app.service;

import com.compo.android.app.model.Play;

public class ServiceResultSave {
    private boolean allQuizzSuccess;
    private Play play;

    public boolean isAllQuizzSuccess() {
	return allQuizzSuccess;
    }

    public void setAllQuizzSuccess(boolean allQuizzSuccess) {
	this.allQuizzSuccess = allQuizzSuccess;
    }

    public Play getPlay() {
	return play;
    }

    public void setPlay(Play play) {
	this.play = play;
    }

}
