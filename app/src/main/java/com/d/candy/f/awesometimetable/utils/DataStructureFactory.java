package com.d.candy.f.awesometimetable.utils;

import com.d.candy.f.awesometimetable.DayOfWeek;
import com.d.candy.f.awesometimetable.TestObject;
import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Notification;
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

    public static ArrayList<Notification> makeAllNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(makeNotification(1));
        notifications.add(makeNotification(2));
        notifications.add(makeNotification(3));
        notifications.add(makeNotification(4));
        return notifications;
    }

    public static Notification makeNotification(int id) {
        return TestObject.makeNotification(id);
    }

    public static CharSequence[] makeEnrolledSubjectNameList() {
        CharSequence[] names = new CharSequence[17];

        EnrollingInfo info;
        Subject subject;
        for (int i = 1; i <= 17; ++i) {
            info = makeEnrollingInfo(i);
            subject = makeSubject(info.getSubjectID());
            names[i-1] = subject.getName() + "/Period="
                    + String.valueOf(info.getPeriod()) + " on " + info.getDayOfWeek().toString();
        }

        return names;
    }

    public static ArrayList<EnrollingInfo> makeEnrollingInfoList() {
        ArrayList<EnrollingInfo> infos = new ArrayList<>();
        for (int i = 1; i <= 17; ++i) {
            infos.add(makeEnrollingInfo(i));
        }

        return infos;
    }
}
