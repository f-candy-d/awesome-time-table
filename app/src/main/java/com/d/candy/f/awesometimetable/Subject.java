package com.d.candy.f.awesometimetable;

/**
 * Created by daichi on 7/11/17.
 */

public class Subject {

    private String mName = null;
    private String mLocation = null;
    private String mTeacher = null;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getTeacher() {
        return mTeacher;
    }

    public void setTeacher(String teacher) {
        mTeacher = teacher;
    }

    /**
     * public constructors
     */
    public Subject() {}

    public Subject(String name, String location, String teacher) {
        mName = name;
        mLocation = location;
        mTeacher = teacher;
    }
}
