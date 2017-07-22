package com.d.candy.f.awesometimetable;

import java.util.Calendar;

/**
 * Created by daichi on 7/11/17.
 */

public enum  DayOfWeek {

    MONDAY(Calendar.MONDAY, "Monday"),
    TUESDAY(Calendar.TUESDAY, "Tuesday"),
    WEDNESDAY(Calendar.WEDNESDAY, "Wednesday"),
    THURSDAY(Calendar.THURSDAY, "Thursday"),
    FRIDAY(Calendar.FRIDAY, "Friday"),
    SATURDAY(Calendar.SATURDAY, "Saturday"),
    SUNDAY(Calendar.SUNDAY, "Sunday"),
    ;

    private final int mInt;
    private final String mString;

    DayOfWeek(int integer, String string) {
        mInt = integer;
        mString = string;
    }

    public int toInt() {
        return mInt;
    }

    @Override
    public String toString() {
        return mString;
    }

    public String toStringShort() { return mString.substring(0, 3); }

    public static DayOfWeek getDayOfWeek(int integer) {
        DayOfWeek[] types = DayOfWeek.values();
        for(DayOfWeek type : types) {
            if(type.toInt() == integer)
                return type;
        }

        return null;
    }
}
