package com.compo.android.app.model;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	private int point;
	private int credit;
	private long overallTime;

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public long getOverallTime() {
		return overallTime;
	}

	public void setOverallTime(long overallTime) {
		this.overallTime = overallTime;
	}

}