package com.compo.android.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private List<Player> joueurs = new ArrayList<Player>();

	public Team(String aName) {
		name = aName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(List<Player> joueurs) {
		this.joueurs = joueurs;
	}

}
