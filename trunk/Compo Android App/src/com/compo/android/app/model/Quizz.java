package com.compo.android.app.model;

import java.io.Serializable;

public class Quizz implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	private boolean success;
	private String name;
	private String description;
	private Team equipeDomicile;
	private Team equipeExterieur;
	private int scoreEquipeDomicile;
	private int scoreEquipeExterieur;

	public int getScoreEquipeDomicile() {
		return scoreEquipeDomicile;
	}

	public void setScoreEquipeDomicile(int scoreEquipeDomicile) {
		this.scoreEquipeDomicile = scoreEquipeDomicile;
	}

	public int getScoreEquipeExterieur() {
		return scoreEquipeExterieur;
	}

	public void setScoreEquipeExterieur(int scoreEquipeExterieur) {
		this.scoreEquipeExterieur = scoreEquipeExterieur;
	}

	public Team getEquipeDomicile() {
		return equipeDomicile;
	}

	public void setEquipeDomicile(Team equipeDomicile) {
		this.equipeDomicile = equipeDomicile;
	}

	public Team getEquipeExterieur() {
		return equipeExterieur;
	}

	public void setEquipeExterieur(Team equipeExterieur) {
		this.equipeExterieur = equipeExterieur;
	}

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
