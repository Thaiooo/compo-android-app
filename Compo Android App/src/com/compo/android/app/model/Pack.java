package com.compo.android.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pack implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String description;
    private List<Match> matchs = new ArrayList<Match>();

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

    public List<Match> getMatchs() {
	return matchs;
    }

    public void setMatchs(List<Match> aMatchs) {
	this.matchs = aMatchs;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Pack other = (Pack) obj;
	if (id != other.id)
	    return false;
	return true;
    }

}
