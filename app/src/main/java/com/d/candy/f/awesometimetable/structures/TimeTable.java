package com.d.candy.f.awesometimetable.structures;

import android.content.Context;
import android.util.SparseArray;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.R;

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

        Subject math = new Subject("Math", "A-708", "Taylor Swift");
        Subject japanese = new Subject("Japanese", "B-208", "DJ Onishi");
        Subject english = new Subject("English", "E-009", "Mr.English");
        Subject physics = new Subject("Physics", "F9-3", "Newton");
        Subject chemistry = new Subject("Chemistry", "G-43", "Swift");

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
        int dayOfWeek = getDayOfWeekContainsPosition(position);
        int dayOfWeekPos = getDayOfWeekPosition(dayOfWeek);
        int offset = position-dayOfWeekPos-1;
        return (0 <= offset && offset < mSubjectTable.get(dayOfWeekPos).size());
    }

    public ArrayList<Subject> getSubjectsForDayOfWeekPosition(final int dayOfWeekPos) {
        return mSubjectTable.get(dayOfWeekPos, null);
    }

    public int getDayOfWeekPosition(final int dayOfWeek) {
        return dayOfWeek*mNumSubjectInOneDay+dayOfWeek;
    }

    public int getPreviousDayOfWeekPosition(final int dayOfWeekPos) {
        return dayOfWeekPos-(mNumSubjectInOneDay+1);
    }

    public int getDayOfWeekContainsPosition(final int position) {
        return position/(mNumSubjectInOneDay+1);
    }

    public String getDayOfWeekAsString(final int dayOfWeek) {
        switch (dayOfWeek) {
            case DayOfWeek.MONDAY: return mContext.getString(R.string.day_of_week_monday);
            case DayOfWeek.TUESDAY: return mContext.getString(R.string.day_of_week_tuesday);
            case DayOfWeek.WEDNESDAY: return mContext.getString(R.string.day_of_week_wednesday);
            case DayOfWeek.THURSDAY: return mContext.getString(R.string.day_of_week_thursday);
            case DayOfWeek.FRIDAY: return mContext.getString(R.string.day_of_week_friday);
            case DayOfWeek.SATURDAY: return mContext.getString(R.string.day_of_week_saturday);
            case DayOfWeek.SUNDAY: return mContext.getString(R.string.day_of_week_sunday);
            default: throw new IllegalArgumentException("in getDayOfWeekAsString: unknown type of 'DayOfWeek'");
        }
    }
}
