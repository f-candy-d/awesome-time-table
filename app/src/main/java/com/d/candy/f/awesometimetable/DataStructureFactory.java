package com.d.candy.f.awesometimetable;

import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.manager.SubjectManager;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;

/**
 * Created by daichi on 7/15/17.
 */

public class DataStructureFactory {

    public DataStructureFactory() {}

    public static Subject makeSubject(int id) {
        if(id == DBContract.SubjectEntity.BLANK_SUBJECT_ID) {
            return TestObject.makeSubject(DBContract.SubjectEntity.BLANK_SUBJECT_ID);
        }
//        SubjectManager sbjManager = SubjectManager.getInstance();
//        return sbjManager.findSubjectByID(id);
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

        table.addSubjectTo(DayOfWeek.MONDAY, math);
        table.addSubjectTo(DayOfWeek.MONDAY, japanese);
        table.addSubjectTo(DayOfWeek.MONDAY, english);
        table.addSubjectTo(DayOfWeek.MONDAY, physics);
        table.addSubjectTo(DayOfWeek.MONDAY, chemistry);

        table.addSubjectTo(DayOfWeek.TUESDAY, japanese);
        table.addSubjectTo(DayOfWeek.TUESDAY, physics);
        table.addBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        table.addBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        table.addSubjectTo(DayOfWeek.TUESDAY, chemistry);

        table.addSubjectTo(DayOfWeek.WEDNESDAY, chemistry);
        table.addSubjectTo(DayOfWeek.WEDNESDAY, chemistry);
        table.addSubjectTo(DayOfWeek.WEDNESDAY, chemistry);
        table.addBlankSubjectTo(DayOfWeek.WEDNESDAY, 1);
        table.addSubjectTo(DayOfWeek.WEDNESDAY, physics);

        table.addSubjectTo(DayOfWeek.THURSDAY, japanese);
        table.addSubjectTo(DayOfWeek.THURSDAY, math);
        table.addSubjectTo(DayOfWeek.THURSDAY, math);
        table.addSubjectTo(DayOfWeek.THURSDAY, english);
        table.addBlankSubjectTo(DayOfWeek.THURSDAY, 1);

        table.addSubjectTo(DayOfWeek.FRIDAY, physics);
        table.addBlankSubjectTo(DayOfWeek.FRIDAY, 2);

        return table;
    }

    public static EnrollingInfo makeEnrollingInfo(int id) {
        return null;
    }
}
