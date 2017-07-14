package com.d.candy.f.awesometimetable.structure;

/**
 * Created by daichi on 7/12/17.
 */

public class Location {

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
}
