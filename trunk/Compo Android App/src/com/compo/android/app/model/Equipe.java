package com.compo.android.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipe implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private List<Joueur> joueurs = new ArrayList<Joueur>();

	public Equipe(String aName) {
		name = aName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

}
