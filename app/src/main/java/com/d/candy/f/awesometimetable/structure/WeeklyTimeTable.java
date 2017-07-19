package com.d.candy.f.awesometimetable.structure;

import android.util.SparseArray;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.utils.EntityCache;

/**
 * Created by daichi on 7/11/17.
 */

public class WeeklyTimeTable extends TimeTable {

    private int mID;
    private SparseArray<OneDayTimeTable> mTable;

    public WeeklyTimeTable(int id) {
        mID = id;
        mTable = new SparseArray<>();
        setEntityCache(new EntityCache());
    }

    public void enrollSubjectTo(EnrollingInfo enrollingInfo, Subject subject) {
        // If a time table does not exist on 'dayOfWeek', create new one
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(enrollingInfo.getDayOfWeek())) == null) {
            oneDayTimeTable = new OneDayTimeTable(enrollingInfo.getDayOfWeek(), getEntityCache());
            mTable.put(enrollingInfo.getDayOfWeek().toInt(), oneDayTimeTable);
        }

        oneDayTimeTable.enrollSubject(enrollingInfo, subject);
    }

    public void enrollSubjectTo(EnrollingInfo enrollingInfo) {
        enrollSubjectTo(enrollingInfo, null);
    }

    public Location getLocation(int id) {
        return getEntityCache().getLocation(id);
    }

    public void enrollBlankSubjectTo(DayOfWeek dayOfWeek, int size) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) == null) {
            oneDayTimeTable = new OneDayTimeTable(dayOfWeek, getEntityCache());
            mTable.put(dayOfWeek.toInt(), oneDayTimeTable);
        }

        oneDayTimeTable.enrollBlankSubject(size);
    }

    public Subject getSubjectAtPositionOn(DayOfWeek dayOfWeek, int position) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null) {
            return oneDayTimeTable.getSubjectAtPosition(position);
        } else {
            return null;
        }
    }

    public Subject getSubjectAtPeriodOn(DayOfWeek dayOfWeek, int period) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null) {
            return oneDayTimeTable.getSubjectAtPeriod(period);
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
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null) {
            return oneDayTimeTable.countSubject();
        } else {
            return 0;
        }
    }

    public int getBeginPeriodAtPositionOn(DayOfWeek dayOfWeek, int position) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null
                && position < countSubjectOn(dayOfWeek)) {
            int period = 1;
            for (int i = 0; i < position; ++i)
            {
                period += oneDayTimeTable.getSubjectAtPosition(i).getLength();
            }

            return period;
        }
        return 0;
    }

    private OneDayTimeTable isTimeTableExistOn(DayOfWeek dayOfWeek) {
        return (mTable.get(dayOfWeek.toInt(), null));
    }

    public EnrollingInfo getEnrollingInfoAtPositionOn(DayOfWeek dayOfWeek, int position) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null) {
            return oneDayTimeTable.getEnrollingInfoAtPosition(position);
        }
        return null;
    }
}
