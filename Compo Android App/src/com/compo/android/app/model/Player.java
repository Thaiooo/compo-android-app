package com.compo.android.app.model;

import java.io.Serializable;

public class Player implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private int numero;
	private float positionXPercent;
	private float positionYPercent;

	public Player(String aName, int aNumero, float aPositionXPercent,
			float aPositionYPercent) {
		name = aName;
		numero = aNumero;
		positionXPercent = aPositionXPercent;
		positionYPercent = aPositionYPercent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public float getPositionXPercent() {
		return positionXPercent;
	}

	public void setPositionXPercent(float positionXPercent) {
		this.positionXPercent = positionXPercent;
	}

	public float getPositionYPercent() {
		return positionYPercent;
	}

	public void setPositionYPercent(float positionYPercent) {
		this.positionYPercent = positionYPercent;
	}

}
