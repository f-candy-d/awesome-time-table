package com.d.candy.f.awesometimetable.entity;

import android.content.ContentValues;

import com.d.candy.f.awesometimetable.DBContract;

/**
 * Created by daichi on 7/15/17.
 */

public class EnrollingInfo extends Entity {

    private int mID;
    private int mDayOfWeek;
    private int mPeriod;
    private int mSubjectID;

    public EnrollingInfo() {}

    public EnrollingInfo(int id, int day_of_week, int period, int subject_id) {
        mID = id;
        mDayOfWeek = day_of_week;
        mPeriod = period;
        mSubjectID = subject_id;
    }

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
