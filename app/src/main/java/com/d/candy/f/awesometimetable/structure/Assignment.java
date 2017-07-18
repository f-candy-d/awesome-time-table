package com.d.candy.f.awesometimetable.structure;

import android.content.ContentValues;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.DayOfWeek;

/**
 * Created by daichi on 7/19/17.
 */

public class Assignment extends Entity {

    private String mTitle;
    private String mNote;
    private int mEnrollingInfoID;
    private int mDeadlineYear;
    private int mDeadlineMonth;
    private int mDeadlineDay;
    private DayOfWeek mDeadlineDayOfWeek;
    private boolean mIsDone;

    public String getTitle() {
        return mTitle;
    }

    public String getNote() {
        return mNote;
    }

    public int getEnrollingInfoID() {
        return mEnrollingInfoID;
    }

    public int getDeadlineYear() {
        return mDeadlineYear;
    }

    public int getDeadlineMonth() {
        return mDeadlineMonth;
    }

    public int getDeadlineDay() {
        return mDeadlineDay;
    }

    public DayOfWeek getDeadlineDayOfWeek() {
        return mDeadlineDayOfWeek;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public Assignment() {}

    public Assignment(
            String title, String note, int enrollingInfoID,
            int deadlineYear, int deadlineMonth, int deadlineDay,
            DayOfWeek deadlineDayOfweek, boolean isDone) {

        mTitle = title;
        mNote = note;
        mEnrollingInfoID = enrollingInfoID;
        mDeadlineYear = deadlineYear;
        mDeadlineMonth = deadlineMonth;
        mDeadlineDay = deadlineDay;
        mDeadlineDayOfWeek = deadlineDayOfweek;
        mIsDone = isDone;
    }

    @Override
    public String getAffiliation() {
        return DBContract.AssignmentEntity.TABLE_NAME;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ASSIGNMENT;
    }

    @Override
    public ContentValues toContentValues() {
        return null;
    }
}
