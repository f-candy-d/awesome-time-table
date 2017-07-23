package com.d.candy.f.awesometimetable.structure;

import android.util.SparseArray;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.utils.EntityCache;

import java.util.ArrayList;

/**
 * Created by daichi on 7/11/17.
 */

public class WeeklyTimeTable extends TimeTable {

    private int mID;
    private SparseArray<OneDayTimeTable> mTables;

    public WeeklyTimeTable(final int id) {
        this(id, null);
    }

    public WeeklyTimeTable(final int id, final EntityCache dataSet) {
        super(dataSet);
        mID = id;
        mTables = new SparseArray<>();
    }

    public void enrollSubjectTo(EnrollingInfo enrollingInfo, Subject subject) {
        getOneDayTimeTableOf(enrollingInfo.getDayOfWeek()).enrollSubject(enrollingInfo, subject);
    }

    public void enrollSubjectTo(EnrollingInfo enrollingInfo) {
        enrollSubjectTo(enrollingInfo, null);
    }

    public Location getLocation(int id) {
        return getDataSet().getLocation(id);
    }

    public void enrollBlankSubjectTo(DayOfWeek dayOfWeek, int size) {
        getOneDayTimeTableOf(dayOfWeek).enrollBlankSubject(size);
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
        for (int i = 0; i < mTables.size(); ++i) {
            int key = mTables.keyAt(i);
            count += mTables.get(key).countSubject();
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
        return (mTables.get(dayOfWeek.toInt(), null));
    }

    public EnrollingInfo getEnrollingInfoAtOrderOn(DayOfWeek dayOfWeek, int order) {
        OneDayTimeTable oneDayTimeTable;
        if((oneDayTimeTable = isTimeTableExistOn(dayOfWeek)) != null) {
            return oneDayTimeTable.getEnrollingInfoAtOrder(order);
        }
        return null;
    }

    public ArrayList<Integer> getAllAssignmentID() {
        return getDataSet().getAllAssignmentID();
    }

    public ArrayList<Integer> getAllNotificationID() {
        return getDataSet().getAllNotificationID();
    }

    /**
     * Return a one-day time table for 'dayOfWeek'.
     * If it does not exit, create new one.
     *
     * @param dayOfWeek
     * @return
     */
    private OneDayTimeTable getOneDayTimeTableOf(final DayOfWeek dayOfWeek) {
        OneDayTimeTable timeTable;
        if((timeTable = isTimeTableExistOn(dayOfWeek)) == null) {
            timeTable = new OneDayTimeTable(dayOfWeek, getDataSet());
            mTables.put(dayOfWeek.toInt(), timeTable);
        }

        return timeTable;
    }
}
