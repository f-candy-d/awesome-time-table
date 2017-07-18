package com.d.candy.f.awesometimetable.utils;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.TestObject;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;
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

        Location locA = DataStructureFactory.makeLocation(0);
        Location locB = DataStructureFactory.makeLocation(1);
        Location locC = DataStructureFactory.makeLocation(2);
        Location locD = DataStructureFactory.makeLocation(3);

        EnrollingInfo infoMon1 = DataStructureFactory.makeEnrollingInfo(0);
        EnrollingInfo infoMon3 = DataStructureFactory.makeEnrollingInfo(1);
        EnrollingInfo infoMon4 = DataStructureFactory.makeEnrollingInfo(2);
        EnrollingInfo infoMon5 = DataStructureFactory.makeEnrollingInfo(3);
        EnrollingInfo infoMon7 = DataStructureFactory.makeEnrollingInfo(4);

        EnrollingInfo infoTue1 = DataStructureFactory.makeEnrollingInfo(5);
        EnrollingInfo infoTue2 = DataStructureFactory.makeEnrollingInfo(6);
        EnrollingInfo infoTue6 = DataStructureFactory.makeEnrollingInfo(7);

        EnrollingInfo infoWed1 = DataStructureFactory.makeEnrollingInfo(8);
        EnrollingInfo infoWed2 = DataStructureFactory.makeEnrollingInfo(9);
        EnrollingInfo infoWed3 = DataStructureFactory.makeEnrollingInfo(10);
        EnrollingInfo infoWed4 = DataStructureFactory.makeEnrollingInfo(11);

        EnrollingInfo infoThu1 = DataStructureFactory.makeEnrollingInfo(12);
        EnrollingInfo infoThu2 = DataStructureFactory.makeEnrollingInfo(13);
        EnrollingInfo infoThu4 = DataStructureFactory.makeEnrollingInfo(14);
        EnrollingInfo infoThu6 = DataStructureFactory.makeEnrollingInfo(15);

        EnrollingInfo infoFri1 = DataStructureFactory.makeEnrollingInfo(16);

        // Register entities
        table.addEntity(math);
        table.addEntity(japanese);
        table.addEntity(english);
        table.addEntity(physics);
        table.addEntity(chemistry);

        table.addEntity(locA);
        table.addEntity(locB);
        table.addEntity(locC);
        table.addEntity(locD);

        // Enroll subjects
        table.enrollSubjectTo(infoMon1.getDayOfWeek(), infoMon1.getID());
        table.enrollSubjectTo(infoMon3.getDayOfWeek(), infoMon3.getID());
        table.enrollSubjectTo(infoMon4.getDayOfWeek(), infoMon4.getID());
        table.enrollSubjectTo(infoMon5.getDayOfWeek(), infoMon5.getID());
        table.enrollSubjectTo(infoMon7.getDayOfWeek(), infoMon7.getID());

        table.enrollSubjectTo(infoTue1.getDayOfWeek(), infoTue1.getID());
        table.enrollSubjectTo(infoTue2.getDayOfWeek(), infoTue2.getID());
        table.enrollBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        table.enrollBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        table.enrollSubjectTo(infoTue6.getDayOfWeek(), infoTue6.getID());

        table.enrollSubjectTo(infoWed1.getDayOfWeek(), infoWed1.getID());
        table.enrollSubjectTo(infoWed2.getDayOfWeek(), infoWed2.getID());
        table.enrollSubjectTo(infoWed3.getDayOfWeek(), infoWed3.getID());
        table.enrollSubjectTo(infoWed4.getDayOfWeek(), infoWed4.getID());
        table.enrollBlankSubjectTo(DayOfWeek.WEDNESDAY, 1);

        table.enrollSubjectTo(infoThu1.getDayOfWeek(), infoThu1.getID());
        table.enrollSubjectTo(infoThu2.getDayOfWeek(), infoThu2.getID());
        table.enrollSubjectTo(infoThu4.getDayOfWeek(), infoThu4.getID());
        table.enrollSubjectTo(infoThu6.getDayOfWeek(), infoThu6.getID());
        table.enrollBlankSubjectTo(DayOfWeek.THURSDAY, 1);

        table.enrollSubjectTo(infoFri1.getDayOfWeek(), infoFri1.getID());
        table.enrollBlankSubjectTo(DayOfWeek.FRIDAY, 2);

        return table;
    }

    public static EnrollingInfo makeEnrollingInfo(int id) {
        return TestObject.makeEnrollingInfo(id);
    }

    public static Location makeLocation(int id) {
        return TestObject.makeLocation(id);
    }

    public static Teacher makeTeacher(int id) { return TestObject.makeTeacher(id); }
}
