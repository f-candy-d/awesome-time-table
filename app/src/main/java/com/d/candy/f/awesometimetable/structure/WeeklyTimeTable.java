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

    public Subject getSubjectAtOrderOn(DayOfWeek dayOfWeek, int order) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null) {
            return oneDayTimeTable.getSubjectAtOrder(order);
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
            count += mTable.get(key).countSubject();
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

    public int countSubjectOn(DayOfWeek[] dayOfWeeks) {
        int count = 0;
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            count += countSubjectOn(dayOfWeek);
        }
        return count;
    }

    public int getBeginPeriodAtOrderOn(DayOfWeek dayOfWeek, int order) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null
                && order < countSubjectOn(dayOfWeek)) {
            int period = 1;
            for (int i = 0; i < order; ++i)
            {
                period += oneDayTimeTable.getSubjectAtOrder(i).getLength();
            }

            return period;
        }
        return 0;
    }

    private OneDayTimeTable isTimeTableExistOn(DayOfWeek dayOfWeek) {
        return (mTable.get(dayOfWeek.toInt(), null));
    }

    public EnrollingInfo getEnrollingInfoAtOrderOn(DayOfWeek dayOfWeek, int order) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null) {
            return oneDayTimeTable.getEnrollingInfoAtOrder(order);
        }
        return null;
    }
}
