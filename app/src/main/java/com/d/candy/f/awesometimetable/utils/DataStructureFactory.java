package com.d.candy.f.awesometimetable.utils;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.TestObject;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;

/**
 * Created by daichi on 7/15/17.
 */

public class DataStructureFactory {

    public DataStructureFactory() {}

    public static Subject makeSubject(int id) {
        return TestObject.makeSubject(id);
    }

    public static WeeklyTimeTable makeTimeTable(int id) {
        // TODO: The following codes are test code, Remove later
        WeeklyTimeTable table = new WeeklyTimeTable(id);
        Subject math = DataStructureFactory.makeSubject(2);
        Subject japanese = DataStructureFactory.makeSubject(3);
        Subject english = DataStructureFactory.makeSubject(4);
        Subject physics = DataStructureFactory.makeSubject(5);
        Subject chemistry = DataStructureFactory.makeSubject(6);

        // Register subjects
        table.addSubject(math);
        table.addSubject(japanese);
        table.addSubject(english);
        table.addSubject(physics);
        table.addSubject(chemistry);

        // Enroll subjects
        table.enrollSubjectTo(DayOfWeek.MONDAY, math);
        table.enrollSubjectTo(DayOfWeek.MONDAY, japanese);
        table.enrollSubjectTo(DayOfWeek.MONDAY, english);
        table.enrollSubjectTo(DayOfWeek.MONDAY, physics);
        table.enrollSubjectTo(DayOfWeek.MONDAY, chemistry);

        table.enrollSubjectTo(DayOfWeek.TUESDAY, japanese);
        table.enrollSubjectTo(DayOfWeek.TUESDAY, physics);
        table.enrollBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        table.enrollBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        table.enrollSubjectTo(DayOfWeek.TUESDAY, chemistry);

        table.enrollSubjectTo(DayOfWeek.WEDNESDAY, chemistry);
        table.enrollSubjectTo(DayOfWeek.WEDNESDAY, chemistry);
        table.enrollSubjectTo(DayOfWeek.WEDNESDAY, chemistry);
        table.enrollBlankSubjectTo(DayOfWeek.WEDNESDAY, 1);
        table.enrollSubjectTo(DayOfWeek.WEDNESDAY, physics);

        table.enrollSubjectTo(DayOfWeek.THURSDAY, japanese);
        table.enrollSubjectTo(DayOfWeek.THURSDAY, math);
        table.enrollSubjectTo(DayOfWeek.THURSDAY, math);
        table.enrollSubjectTo(DayOfWeek.THURSDAY, english);
        table.enrollBlankSubjectTo(DayOfWeek.THURSDAY, 1);

        table.enrollSubjectTo(DayOfWeek.FRIDAY, physics);
        table.enrollBlankSubjectTo(DayOfWeek.FRIDAY, 2);

        return table;
    }

    public static EnrollingInfo makeEnrollingInfo(int id) {
        return null;
    }
}
