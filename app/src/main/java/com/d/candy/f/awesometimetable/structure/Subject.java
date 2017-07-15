package com.d.candy.f.awesometimetable.structure;

import android.content.ContentValues;

import com.d.candy.f.awesometimetable.DBContract;

/**
 * Created by daichi on 7/11/17.
 */

public class Subject extends Entity {

    private String mName = null;
    private String mLocation = null;
    private String mTeacher = null;
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

    @Override
    public EntityType getEntityType() {
        return EntityType.SUBJECT;
    }

    @Override
    public String getAffiliation() {
        return DBContract.SubjectEntity.TABLE_NAME;
    }

    @Override
    public ContentValues toContentValues() {
        return null;
    }
}
