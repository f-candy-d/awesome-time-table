package com.d.candy.f.awesometimetable;

/**
 * Created by daichi on 7/11/17.
 */

public enum  DayOfWeek {

    MONDAY(0, "Monday"),
    TUESDAY(1, "Tuesday"),
    WEDNESDAY(2, "Wednesday"),
    THURSDAY(3, "Thursday"),
    FRIDAY(4, "Friday"),
    SATURDAY(5, "Saturday"),
    SUNDAY(6, "Sunday"),
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

    public static DayOfWeek getDayOfWeek(int integer) {
        DayOfWeek[] types = DayOfWeek.values();
        for(DayOfWeek type : types) {
            if(type.toInt() == integer)
                return type;
        }

        return null;
    }
}
