package com.compo.android.app.model;

import java.io.Serializable;

public class Team implements Serializable {

    /**
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String code;
    private ColorEnum homeShortColor;
    private ColorEnum homeJerseyColor;
    private ColorEnum homeSockColor;
    private ColorEnum awayShortColor;
    private ColorEnum awayJerseyColor;
    private ColorEnum awaySockColor;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public ColorEnum getHomeShortColor() {
	return homeShortColor;
    }

    public void setHomeShortColor(ColorEnum homeShortColor) {
	this.homeShortColor = homeShortColor;
    }

    public ColorEnum getHomeJerseyColor() {
	return homeJerseyColor;
    }

    public void setHomeJerseyColor(ColorEnum homeJerseyColor) {
	this.homeJerseyColor = homeJerseyColor;
    }

    public ColorEnum getHomeSockColor() {
	return homeSockColor;
    }

    public void setHomeSockColor(ColorEnum homeSockColor) {
	this.homeSockColor = homeSockColor;
    }

    public ColorEnum getAwayShortColor() {
	return awayShortColor;
    }

    public void setAwayShortColor(ColorEnum awayShortColor) {
	this.awayShortColor = awayShortColor;
    }

    public ColorEnum getAwayJerseyColor() {
	return awayJerseyColor;
    }

    public void setAwayJerseyColor(ColorEnum awayJerseyColor) {
	this.awayJerseyColor = awayJerseyColor;
    }

    public ColorEnum getAwaySockColor() {
	return awaySockColor;
    }

    public void setAwaySockColor(ColorEnum awaySockColor) {
	this.awaySockColor = awaySockColor;
    }

}
