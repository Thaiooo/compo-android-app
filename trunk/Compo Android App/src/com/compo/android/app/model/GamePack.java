package com.compo.android.app.model;

import java.io.Serializable;
import java.util.List;

public class GamePack implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	private boolean lock;
	private String name;
	private String description;
	private List<Quizz> quizzList;

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

	public List<Quizz> getQuizzList() {
		return quizzList;
	}

	public void setQuizzList(List<Quizz> quizzList) {
		this.quizzList = quizzList;
	}

	@Override
	public String toString() {
		return "GamePack [lock=" + lock + ", name=" + name + ", description="
				+ description + "]";
	}

}
