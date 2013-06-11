package com.compo.android.app.model;

import java.io.Serializable;

public class Play implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private long time;
    private boolean success;
    private Quizz quizz;
    private User user;

    public Quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
