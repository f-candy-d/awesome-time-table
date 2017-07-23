package com.d.candy.f.awesometimetable.utils;

import com.d.candy.f.awesometimetable.DayOfWeek;

import java.util.Calendar;

/**
 * Created by daichi on 7/22/17.
 */

public class TimeDateHelper {

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMin;
    // A day of week
    private int mEE;

    public TimeDateHelper() {
        this(0, 0, 0, 0, 0);
    }

    public TimeDateHelper(int year, int month, int day) {
        this(year, month, day, 0, 0);
    }

    public TimeDateHelper(int year, int month, int day, int hour, int min) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mHour = hour;
        mMin = min;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min);
        mEE = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public TimeDateHelper(final long millis) {
        setTimeInMillis(millis);
    }

    public void setTimeInMillis(final long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DATE);
        mHour = calendar.get(Calendar.HOUR);
        mMin = calendar.get(Calendar.MINUTE);
        mEE = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public void set(int year, int month, int day, int hour, int min) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mHour = hour;
        mMin = min;
    }

    public void set(final int property, final int value) {
        switch (property) {
            case Calendar.YEAR: mYear = value; break;
            case Calendar.MONTH: mMonth = value; break;
            case Calendar.DATE: mDay = value; break;
            case Calendar.HOUR: mHour = value; break;
            case Calendar.MINUTE: mMin = value; break;
            case Calendar.DAY_OF_WEEK: mEE = value; break;
            default:
                throw new IllegalArgumentException(
                        "property=" + String.valueOf(property) + " is not supported");
        }
    }

    public long getTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay, mHour, mMin);
        return calendar.getTimeInMillis();
    }

    public int get(final int property) {
        switch (property) {
            case Calendar.YEAR: return mYear;
            case Calendar.MONTH: return mMonth;
            case Calendar.DATE: return mDay;
            case Calendar.HOUR: return mHour;
            case Calendar.MINUTE: return mMin;
            case Calendar.DAY_OF_WEEK: return mEE;
            default:
                throw new IllegalArgumentException(
                        "property=" + String.valueOf(property) + " is not supported");
        }
    }

    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.getDayOfWeek(mEE);
    }

    public String getMonthAsString() {
        switch (mMonth) {
            case Calendar.JANUARY: return "January";
            case Calendar.FEBRUARY: return "February";
            case Calendar.MARCH: return "March";
            case Calendar.APRIL: return "April";
            case Calendar.MAY: return "May";
            case Calendar.JUNE: return "June";
            case Calendar.JULY: return "July";
            case Calendar.AUGUST: return "August";
            case Calendar.SEPTEMBER: return "September";
            case Calendar.OCTOBER: return "October";
            case Calendar.NOVEMBER: return "November";
            case Calendar.DECEMBER: return "December";
            default:
                throw new IllegalArgumentException(
                        "mMonth=" + String.valueOf(mMonth) + " is not supported");
        }
    }

    public String getMonthAsStringInShort() {
        String month = getMonthAsString();
        if (3 < month.length()) {
            return month.substring(0, 3);
        }
        return month;
    }

    public String getAsString(boolean withTime) {
        String date = String.valueOf(mYear) + "."
                + getMonthAsStringInShort() + "."
                + String.valueOf(mDay) + " ("
                + getDayOfWeek().toStringShort() + ")";

        if (withTime) {
            date += " - " + String.valueOf(mHour)
                    + ":" + String.valueOf(mMin);
        }

        return date;
    }
}
