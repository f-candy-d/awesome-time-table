package com.d.candy.f.awesometimetable.structure;

import android.content.Context;
import android.util.SparseArray;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.DataStructureFactory;
import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.entity.Subject;

import java.util.ArrayList;

/**
 * Created by daichi on 7/11/17.
 */

public class TimeTable {

    private SparseArray<ArrayList<Subject>> mSubjectTable;
    private Context mContext;

    /**
     * The number of all of the subjects shown in the TimeTable
     */
    private int mNumSubject;

    /**
     * The maximum number of subjects in one day
     */
    private int mNumSubjectInOneDay;

    /**
     * The number of the day of week shown in the TimeTable
     */
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

    public TimeTable(Context context) {
        mContext = context;

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
        Subject math = DataStructureFactory.makeSubject(0);
        Subject japanese = DataStructureFactory.makeSubject(1);
        Subject english = DataStructureFactory.makeSubject(2);
        Subject physics = DataStructureFactory.makeSubject(3);
        Subject chemistry = DataStructureFactory.makeSubject(4);

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
    }

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

    public String getDayOfWeekAsString(final DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return mContext.getString(R.string.day_of_week_monday);
            case TUESDAY: return mContext.getString(R.string.day_of_week_tuesday);
            case WEDNESDAY: return mContext.getString(R.string.day_of_week_wednesday);
            case THURSDAY: return mContext.getString(R.string.day_of_week_thursday);
            case FRIDAY: return mContext.getString(R.string.day_of_week_friday);
            case SATURDAY: return mContext.getString(R.string.day_of_week_saturday);
            case SUNDAY: return mContext.getString(R.string.day_of_week_sunday);
            default: throw new IllegalArgumentException("in getDayOfWeekAsString: unknown type of 'DayOfWeek'");
        }
    }
}
