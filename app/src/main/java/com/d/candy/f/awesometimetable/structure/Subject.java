package com.d.candy.f.awesometimetable.structure;

/**
 * Created by daichi on 7/11/17.
 */

public class Subject {

    private String mName = null;
    private String mLocation = null;
    private String mTeacher = null;
    private int mID;
    private int mLocationID;
    private int mTeacherID;

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

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public int getLocationID() {
        return mLocationID;
    }

    public void setLocationID(int locationID) {
        mLocationID = locationID;
    }

    public int getTeacherID() {
        return mTeacherID;
    }

    public void setTeacherID(int teacherID) {
        mTeacherID = teacherID;
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
