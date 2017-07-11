package com.d.candy.f.awesometimetable.structures;

/**
 * Created by daichi on 7/12/17.
 */

public class Teacher {

    private String mName = null;
    private String mRoom = null;
    private String mMail = null;
    private String mPhone = null;
    private int mID;
    private int mSubjectID;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
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

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
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

    public Teacher(String name, String room, String mail, String phone,
                   int id, int subjectID) {
        mName = name;
        mRoom = room;
        mMail = mail;
        mPhone = phone;
        mID = id;
        mSubjectID = subjectID;
    }
}
