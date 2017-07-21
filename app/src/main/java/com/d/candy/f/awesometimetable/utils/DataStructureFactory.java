package com.d.candy.f.awesometimetable.utils;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.TestObject;
import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;
import com.d.candy.f.awesometimetable.structure.WeeklyTimeTable;

import java.util.ArrayList;

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

        EnrollingInfo infoMon1 = DataStructureFactory.makeEnrollingInfo(1);
        EnrollingInfo infoMon3 = DataStructureFactory.makeEnrollingInfo(2);
        EnrollingInfo infoMon4 = DataStructureFactory.makeEnrollingInfo(3);
        EnrollingInfo infoMon5 = DataStructureFactory.makeEnrollingInfo(4);
        EnrollingInfo infoMon7 = DataStructureFactory.makeEnrollingInfo(5);

        EnrollingInfo infoTue1 = DataStructureFactory.makeEnrollingInfo(6);
        EnrollingInfo infoTue2 = DataStructureFactory.makeEnrollingInfo(7);
        EnrollingInfo infoTue6 = DataStructureFactory.makeEnrollingInfo(8);

        EnrollingInfo infoWed1 = DataStructureFactory.makeEnrollingInfo(9);
        EnrollingInfo infoWed2 = DataStructureFactory.makeEnrollingInfo(10);
        EnrollingInfo infoWed3 = DataStructureFactory.makeEnrollingInfo(11);
        EnrollingInfo infoWed4 = DataStructureFactory.makeEnrollingInfo(12);

        EnrollingInfo infoThu1 = DataStructureFactory.makeEnrollingInfo(13);
        EnrollingInfo infoThu2 = DataStructureFactory.makeEnrollingInfo(14);
        EnrollingInfo infoThu4 = DataStructureFactory.makeEnrollingInfo(15);
        EnrollingInfo infoThu6 = DataStructureFactory.makeEnrollingInfo(16);

        EnrollingInfo infoFri1 = DataStructureFactory.makeEnrollingInfo(17);

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
        table.enrollSubjectTo(infoMon1);
        table.enrollSubjectTo(infoMon3);
        table.enrollSubjectTo(infoMon4);
        table.enrollSubjectTo(infoMon5);
        table.enrollSubjectTo(infoMon7);

        table.enrollSubjectTo(infoTue1);
        table.enrollSubjectTo(infoTue2);
        table.enrollBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        table.enrollBlankSubjectTo(DayOfWeek.TUESDAY, 1);
        table.enrollSubjectTo(infoTue6);

        table.enrollSubjectTo(infoWed1);
        table.enrollSubjectTo(infoWed2);
        table.enrollSubjectTo(infoWed3);
        table.enrollSubjectTo(infoWed4);
        table.enrollBlankSubjectTo(DayOfWeek.WEDNESDAY, 1);

        table.enrollSubjectTo(infoThu1);
        table.enrollSubjectTo(infoThu2);
        table.enrollSubjectTo(infoThu4);
        table.enrollSubjectTo(infoThu6);
        table.enrollBlankSubjectTo(DayOfWeek.THURSDAY, 1);

        table.enrollSubjectTo(infoFri1);
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

    public static Assignment makeAssignment(int id) {
        return TestObject.makeAssignment(id);
    }

    public static Assignment[] makeAssignmentListForEnrollingInfo(EnrollingInfo enrollingInfo) {

        if(enrollingInfo.getID() == 3) {
            return new Assignment[] {makeAssignment(0)};
        } else if(enrollingInfo.getID() == 10) {
            return new Assignment[] {makeAssignment(1)};
        } else if (enrollingInfo.getID() == 8) {
            return new Assignment[] { makeAssignment(2), makeAssignment(3) };
        }

        return new Assignment[0];
    }

    public static ArrayList<Assignment> makeAllAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<>();
        assignments.add(makeAssignment(0));
        assignments.add(makeAssignment(1));
        assignments.add(makeAssignment(2));
        assignments.add(makeAssignment(3));

        return assignments;
    }
}
