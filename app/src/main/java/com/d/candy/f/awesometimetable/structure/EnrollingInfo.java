package com.d.candy.f.awesometimetable.structure;

import android.content.ContentValues;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.DayOfWeek;

/**
 * Created by daichi on 7/15/17.
 */

public class EnrollingInfo extends Entity {

    private DayOfWeek mDayOfWeek;
    private int mPeriod;
    private int mSubjectID;

    public EnrollingInfo() {}

    public EnrollingInfo(int id, DayOfWeek day_of_week, int period, int subject_id) {
        setID(id);
        mDayOfWeek = day_of_week;
        mPeriod = period;
        mSubjectID = subject_id;
    }

    public DayOfWeek getDayOfWeek() {
        return mDayOfWeek;
    }

    public int getSubjectID() {
        return mSubjectID;
    }

    public int getPeriod() { return mPeriod; }

    @Override
    public String getAffiliation() {
        return DBContract.EnrollingInfoEntity.TABLE_NAME;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENROLLING_INFO;
    }

    @Override
    public ContentValues toContentValues() {
        return null;
    }
}
