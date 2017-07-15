package com.d.candy.f.awesometimetable.structure;

import android.util.SparseArray;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.utils.EntityCache;

/**
 * Created by daichi on 7/11/17.
 */

public class WeeklyTimeTable {

    private int mID;
    private SparseArray<OneDayTimeTable> mTable;
    private EntityCache mCache;

    public WeeklyTimeTable(int id) {
        mID = id;
        mTable = new SparseArray<>();
        mCache = new EntityCache();
    }

    public void addSubjectTo(DayOfWeek dayOfWeek, Subject subject) {
        // If a time table does not exist on 'dayOfWeek', create new one
        if(!isTimeTableExistOn(dayOfWeek)) {
            OneDayTimeTable oneDayTable = new OneDayTimeTable(dayOfWeek, mCache);
            mTable.put(dayOfWeek.toInt(), oneDayTable);
        }

        mTable.get(dayOfWeek.toInt()).addSubject(subject);
    }

    public void addBlankSubjectTo(DayOfWeek dayOfWeek, int size) {
        if(!isTimeTableExistOn(dayOfWeek)) {
            OneDayTimeTable oneDayTable = new OneDayTimeTable(dayOfWeek, mCache);
            mTable.put(dayOfWeek.toInt(), oneDayTable);
        }

        mTable.get(dayOfWeek.toInt()).addBlankSubject(size);
    }

    public Subject getSubjectAtPositionOn(DayOfWeek dayOfWeek, int position) {
        if(isTimeTableExistOn(dayOfWeek)) {
            return mTable.get(dayOfWeek.toInt()).getSubjectAtPosition(position);
        } else {
            return null;
        }
    }

    public Subject getSubjectAtPeriodOn(DayOfWeek dayOfWeek, int period) {
        if(isTimeTableExistOn(dayOfWeek)) {
            return mTable.get(dayOfWeek.toInt()).getSubjectAtPeriod(period);
        } else {
            return null;
        }
    }

    public int countSubject() {
        int count = 0;
        for (int i = 0; i < mTable.size(); ++i) {
            int key = mTable.keyAt(i);
            count += mTable.valueAt(key).countSubject();
        }

        return count;
    }

    public int countSubjectOn(DayOfWeek dayOfWeek) {
        if(isTimeTableExistOn(dayOfWeek)) {
            return mTable.get(dayOfWeek.toInt()).countSubject();
        } else {
            return 0;
        }
    }

    private boolean isTimeTableExistOn(DayOfWeek dayOfWeek) {
        return (mTable.get(dayOfWeek.toInt(), null) != null);
    }
}
