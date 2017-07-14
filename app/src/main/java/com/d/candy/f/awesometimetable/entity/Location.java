package com.d.candy.f.awesometimetable.entity;

import android.content.ContentValues;

/**
 * Created by daichi on 7/12/17.
 */

public class Location extends Entity {

    private String mName = null;
    private int mID;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }


    public Location() {}

    public Location(String name, int id) {
        mName = name;
        mID = id;
    }

    @Override
    public String getAffiliation() {
        return null;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.LOCATION;
    }

    @Override
    public ContentValues toContentValues() {
        return null;
    }
}
