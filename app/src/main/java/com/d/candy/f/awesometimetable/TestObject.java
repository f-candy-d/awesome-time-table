package com.d.candy.f.awesometimetable;

import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;

/**
 * Created by daichi on 7/16/17.
 */

public class TestObject {

    public static Subject makeSubject(int id) {
        switch (id) {
            case DBContract.SubjectEntity.BLANK_SUBJECT_ID:
                return new Subject(id, null, -1, null, 1);

            case 2:
                return new Subject(id, "Math", 0, "Mark Zuckerberg", 2);

            case 3:
                return new Subject(id, "Japanese", 1, "Bill Gates", 1);

            case 4:
                return new Subject(id, "English", 2, "Amancio Ortega", 1);

            case 5:
                return new Subject(id, "Physics", 1, "Warren Buffett", 2);

            case 6:
                return new Subject(id, "Chemistry", 3, "Carlos Slim", 1);

            default:
                throw new IllegalArgumentException("id=" + String.valueOf(id) + " is not supported");
        }
    }

    public static EnrollingInfo makeEnrollingInfo(int id) {
        switch (id) {
            case 0:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 1, 2);
            case 1:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 3, 3);
            case 2:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 4, 4);
            case 3:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 5, 5);
            case 4:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 7, 6);

            case 5:
                return new EnrollingInfo(id, DayOfWeek.TUESDAY, 1, 3);
            case 6:
                return new EnrollingInfo(id, DayOfWeek.TUESDAY, 2, 5);
            case 7:
                return new EnrollingInfo(id, DayOfWeek.TUESDAY, 6, 6);

            case 8:
                return new EnrollingInfo(id, DayOfWeek.WEDNESDAY, 1, 6);
            case 9:
                return new EnrollingInfo(id, DayOfWeek.WEDNESDAY, 2, 6);
            case 10:
                return new EnrollingInfo(id, DayOfWeek.WEDNESDAY, 3, 6);
            case 11:
                return new EnrollingInfo(id, DayOfWeek.WEDNESDAY, 4, 5);

            case 12:
                return new EnrollingInfo(id, DayOfWeek.THURSDAY, 1, 3);
            case 13:
                return new EnrollingInfo(id, DayOfWeek.THURSDAY, 2, 2);
            case 14:
                return new EnrollingInfo(id, DayOfWeek.THURSDAY, 4, 2);
            case 15:
                return new EnrollingInfo(id, DayOfWeek.THURSDAY, 6, 4);

            case 16:
                return new EnrollingInfo(id, DayOfWeek.FRIDAY, 1, 5);

            default:
                throw new IllegalArgumentException("id=" + String.valueOf(id) + " is not supported");
        }
    }

    public static Location makeLocation(int id) {
        switch (id) {
            case 0:
                return new Location(id, "Lec.202");
            case 1:
                return new Location(id, "Lec.305");
            case 2:
                return new Location(id, "Lec.409");
            case 3:
                return new Location(id, "Exp.Cent.405-A");
            default:
                throw new IllegalArgumentException("id=" + String.valueOf(id) + " is not supported");
        }
    }
}
