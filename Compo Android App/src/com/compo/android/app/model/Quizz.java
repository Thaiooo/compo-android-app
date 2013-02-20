package com.compo.android.app.model;

import java.io.Serializable;

public class Quizz implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	private boolean success;
	private String name;
	private String description;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	@Override
	public String toString() {
		return "Quizz [success=" + success + ", name=" + name
				+ ", description=" + description + "]";
	}

}
