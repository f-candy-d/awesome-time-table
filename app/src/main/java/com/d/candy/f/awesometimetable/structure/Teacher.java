package com.d.candy.f.awesometimetable.structure;

import android.content.ContentValues;

import com.d.candy.f.awesometimetable.DBContract;

/**
 * Created by daichi on 7/12/17.
 */

public class Teacher extends Entity {

    private String mName = null;
    private String mLab = null;
    private String mMail = null;
    private String mPhone = null;
    private int mSubjectID;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLab() {
        return mLab;
    }

    public void setLab(String lab) {
        mLab = lab;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String mail) {
        mMail = mail;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public int getSubjectID() {
        return mSubjectID;
    }

    public void setSubjectID(int subjectID) {
        mSubjectID = subjectID;
    }

    /**
     * Public constructor
     */
    public Teacher() {}

    public Teacher(int id, String name, String lab,
                   String mail, String phone, int subjectID) {
        setID(id);
        mName = name;
        mLab = lab;
        mMail = mail;
        mPhone = phone;
        mSubjectID = subjectID;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TEACHER;
    }

    @Override
    public String getAffiliation() {
        return DBContract.TeacherEntity.TABLE_NAME;
    }

    @Override
    public ContentValues toContentValues() {
        return null;
    }
}
