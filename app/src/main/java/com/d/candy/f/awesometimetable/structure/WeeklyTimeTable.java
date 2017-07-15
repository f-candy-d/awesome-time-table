package com.d.candy.f.awesometimetable.structure;

import android.content.Context;
import android.util.SparseArray;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.DataStructureFactory;

import java.util.ArrayList;

/**
 * Created by daichi on 7/11/17.
 */

public class WeeklyTimeTable {

    // TODO: remove
    private SparseArray<ArrayList<Subject>> mSubjectTable;

    // TODO: NEW
    private int mID;
    private SparseArray<OneDayTimeTable> mTable;
    private SparseArray<Subject> mSubjectCache;

    /**
     * The number of all of the subjects shown in the WeeklyTimeTable
     */
    private int mNumSubject;

    /**
     * The maximum number of subjects in one day
     */
    // TODO: remove
    private int mNumSubjectInOneDay;

    /**
     * The number of the day of week shown in the WeeklyTimeTable
     */
    // TODO: on hold
    private int mNumShownDayOfWeek;

    public int getNumSubject() {
        return mNumSubject;
    }

    public int getNumSubjectInOneDay() {
        return mNumSubjectInOneDay;
    }

    public int getNumShownDayOfWeek() {
        return mNumShownDayOfWeek;
    }

    public WeeklyTimeTable(int id) {

        // TODO: The following codes are test code, Remove later
        ArrayList<Subject> monday = new ArrayList<>(5);
        ArrayList<Subject> tuesday = new ArrayList<>(3);
        ArrayList<Subject> wednesday = new ArrayList<>(4);
        ArrayList<Subject> thursday = new ArrayList<>(4);
        ArrayList<Subject> friday = new ArrayList<>(1);

//        SubjectManager sbjManager = SubjectManager.getInstance();
//        Subject math = sbjManager.findSubjectByID(0);
//        Subject japanese = sbjManager.findSubjectByID(1);
//        Subject english = sbjManager.findSubjectByID(2);
//        Subject physics = sbjManager.findSubjectByID(3);
//        Subject chemistry = sbjManager.findSubjectByID(4);
        Subject math = DataStructureFactory.makeSubject(2);
        Subject japanese = DataStructureFactory.makeSubject(3);
        Subject english = DataStructureFactory.makeSubject(4);
        Subject physics = DataStructureFactory.makeSubject(5);
        Subject chemistry = DataStructureFactory.makeSubject(6);

        monday.add(math); monday.add(japanese); monday.add(english); monday.add(physics); monday.add(chemistry);
        tuesday.add(japanese); tuesday.add(physics); tuesday.add(chemistry);
        wednesday.add(chemistry); wednesday.add(chemistry); wednesday.add(chemistry); wednesday.add(physics);
        thursday.add(japanese); thursday.add(math); thursday.add(math); thursday.add(english);
        friday.add(physics);

        mNumSubject = 17;
        mNumShownDayOfWeek = 5;
        mNumSubjectInOneDay = 5;

        mSubjectTable = new SparseArray<>(mNumShownDayOfWeek);
        mSubjectTable.put(getDayOfWeekPosition(DayOfWeek.MONDAY), monday);
        mSubjectTable.put(getDayOfWeekPosition(DayOfWeek.TUESDAY), tuesday);
        mSubjectTable.put(getDayOfWeekPosition(DayOfWeek.WEDNESDAY), wednesday);
        mSubjectTable.put(getDayOfWeekPosition(DayOfWeek.THURSDAY), thursday);
        mSubjectTable.put(getDayOfWeekPosition(DayOfWeek.FRIDAY), friday);

        // TODO: NEW
        mID = id;
        mTable = new SparseArray<>();
    }

    // TODO: remove
    public boolean isPositionDayOfWeek(final int position) {
        return (mSubjectTable.get(position, null) != null);
    }

    public boolean isPositionOccupiedByAnySubject(final int position) {
        DayOfWeek dayOfWeek = getDayOfWeekContainsPosition(position);
        int dayOfWeekPos = getDayOfWeekPosition(dayOfWeek);
        int offset = position-dayOfWeekPos-1;
        return (0 <= offset && offset < mSubjectTable.get(dayOfWeekPos).size());
    }

    public ArrayList<Subject> getSubjectsForDayOfWeekPosition(final int dayOfWeekPos) {
        return mSubjectTable.get(dayOfWeekPos, null);
    }

    public int getDayOfWeekPosition(final DayOfWeek dayOfWeek) {
        return dayOfWeek.toInt()*mNumSubjectInOneDay+dayOfWeek.toInt();
    }

    public int getPreviousDayOfWeekPosition(final int dayOfWeekPos) {
        return dayOfWeekPos-(mNumSubjectInOneDay+1);
    }

    public DayOfWeek getDayOfWeekContainsPosition(final int position) {
        return DayOfWeek.getDayOfWeek(position/(mNumSubjectInOneDay+1));
    }

    // TODO: NEW
    public void addSubjectTo(DayOfWeek dayOfWeek, Subject subject) {
        // If a time table does not exist on 'dayOfWeek', create new one
        if(!isTimeTableExistOn(dayOfWeek)) {
            OneDayTimeTable oneDayTable = new OneDayTimeTable(dayOfWeek);
            mTable.put(dayOfWeek.toInt(), oneDayTable);
        }

        mTable.get(dayOfWeek.toInt()).addSubject(subject);
    }

    public void addBlankSubjectTo(DayOfWeek dayOfWeek, int size) {
        if(!isTimeTableExistOn(dayOfWeek)) {
            OneDayTimeTable oneDayTable = new OneDayTimeTable(dayOfWeek);
            mTable.put(dayOfWeek.toInt(), oneDayTable);
        }

        mTable.get(dayOfWeek.toInt()).addBlankSubject(size);
    }

    // TODO: NEW
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

    // TODO: NEW
    private boolean isTimeTableExistOn(DayOfWeek dayOfWeek) {
        return (mTable.get(dayOfWeek.toInt(), null) != null);
    }
}
