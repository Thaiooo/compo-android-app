package com.compo.android.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Theme implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String code;
    private List<Pack> packs = new ArrayList<Pack>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packList) {
        this.packs = packList;
    }

}
