package com.d.candy.f.awesometimetable.structure;

import android.app.DatePickerDialog;
import android.util.Log;

import com.d.candy.f.awesometimetable.DBContract;
import com.d.candy.f.awesometimetable.utils.DataStructureFactory;
import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.utils.EntityCache;

import java.util.ArrayList;

/**
 * Created by daichi on 7/15/17.
 */

public class OneDayTimeTable extends TimeTable {

    private DayOfWeek mDayOfWeek;
    private ArrayList<EnrollingInfo> mTable;

    public OneDayTimeTable(final DayOfWeek dayOfWeek) {
        this(dayOfWeek, null);
    }

    public OneDayTimeTable(final DayOfWeek dayOfWeek, EntityCache dataSet) {
        super(dataSet);
        mDayOfWeek = dayOfWeek;
        mTable = new ArrayList<>();
    }

    /**
     * Count the number of subjects in the time table.
     * A return value includes the number of BLANK-Subjects.
     *
     * @return The number of subjects in the time table
     */
    public int countSubject() {
        return mTable.size();
    }

    /**
     * Count the number of subjects in the time table correctly.
     * 'correctly' means that a return value doesn't include the
     * number of BLANK-Subjects.
     *
     * @return The number of subjects in the time table except BLANK-Subjects.
     */
    public int countSubjectCorrectly() {
        int count = 0;
        for(EnrollingInfo info : mTable) {
            // If this returns FALSE, it means that
            // 'id' is used for a BLANK-Subject entity
            if(DBContract.SubjectEntity.MIN_USABLE_ID <= info.getSubjectID()) ++count;
        }

        return count;
    }

    public int countPeriod() { return 0; }

    public int countPeriodCorrectly() { return 0; }

    public DayOfWeek getDayOfWeek() {
        return mDayOfWeek;
    }

    public Location getLocation(int id) {
        return getDataSet().getLocation(id);
    }

    public void enrollSubject(EnrollingInfo enrollingInfo) {
        enrollSubject(enrollingInfo, null);
    }

    public void enrollSubject(EnrollingInfo enrollingInfo, Subject subject) {
        if(subject != null && enrollingInfo.getSubjectID() != subject.getID()) {
            throw new IllegalArgumentException(
                    "enrolling-info's subject id and subject's one does not match");
        }

//        if(subject != null && subject.getID() != DBContract.SubjectEntity.NULL_ID) {
//            addEntity(subject);
//        }
        mTable.add(enrollingInfo);
    }

    /**
     * Enroll a blank subject to the last of the time table.
     * If a blank subject which has the same size as 'size' does not
     * exists in the cache, make new one and cache it.
     * In that case, the id of a new Subject is -1*size.
     *
     * @param size The size of a blank subject
     */
    public void enrollBlankSubject(final int size) {
        if(0 < size) {
            int blankSbjID = -size;
            if(!getDataSet().isCached(blankSbjID, EntityType.SUBJECT)) {
                Subject blank = DataStructureFactory
                        .makeSubject(DBContract.SubjectEntity.BLANK_SUBJECT_ID);
                blank.setID(blankSbjID);
                blank.setLength(size);
//                addEntity(blank);
                // TODO; Is there any other suggestions?
                getDataSet().cache(blank, true);
            }

            // Enroll a blank subject as a spacer
            EnrollingInfo enrollingInfo = new EnrollingInfo(
                    DBContract.EnrollingInfoEntity.NULL_ID, null, mTable.size(), blankSbjID);
            enrollSubject(enrollingInfo);

        } else {
            throw new IllegalArgumentException(
                    "enrollBlankSubject(): 'length' must be a positive number");
        }
    }

    public Subject getSubjectAtOrder(int order) {
        return getDataSet().getSubject(mTable.get(order).getSubjectID());
    }

    public int getSubjectIDAtOrder(int order) {
        return mTable.get(order).getSubjectID();
    }

    public Subject getSubjectAtPeriod(int period) { return null; }

    public EnrollingInfo getEnrollingInfoAtOrder(int order) {
        return mTable.get(order);
    }

}
