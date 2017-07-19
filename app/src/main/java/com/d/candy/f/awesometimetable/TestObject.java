package com.d.candy.f.awesometimetable;

import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;

/**
 * Created by daichi on 7/16/17.
 */

public class TestObject {

    public static Subject makeSubject(int id) {
        switch (id) {
            case DBContract.SubjectEntity.BLANK_SUBJECT_ID:
                return new Subject(id, null, DBContract.SubjectEntity.NULL_ID,
                        DBContract.SubjectEntity.NULL_ID, 1);

            case 2:
                return new Subject(id, "Math", 0, 0, 2);

            case 3:
                return new Subject(id, "Japanese", 1, 1, 1);

            case 4:
                return new Subject(id, "English", 2, 2, 1);

            case 5:
                return new Subject(id, "Physics", 1, 3, 2);

            case 6:
                return new Subject(id, "Chemistry", 3, 4, 1);

            default:
                throw new IllegalArgumentException("id=" + String.valueOf(id) + " is not supported");
        }
    }

    public static EnrollingInfo makeEnrollingInfo(int id) {
        switch (id) {
            case 1:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 1, 2);
            case 2:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 3, 3);
            case 3:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 4, 4);
            case 4:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 5, 5);
            case 5:
                return new EnrollingInfo(id, DayOfWeek.MONDAY, 7, 6);

            case 6:
                return new EnrollingInfo(id, DayOfWeek.TUESDAY, 1, 3);
            case 7:
                return new EnrollingInfo(id, DayOfWeek.TUESDAY, 2, 5);
            case 8:
                return new EnrollingInfo(id, DayOfWeek.TUESDAY, 6, 6);

            case 9:
                return new EnrollingInfo(id, DayOfWeek.WEDNESDAY, 1, 6);
            case 10:
                return new EnrollingInfo(id, DayOfWeek.WEDNESDAY, 2, 6);
            case 11:
                return new EnrollingInfo(id, DayOfWeek.WEDNESDAY, 3, 6);
            case 12:
                return new EnrollingInfo(id, DayOfWeek.WEDNESDAY, 4, 5);

            case 13:
                return new EnrollingInfo(id, DayOfWeek.THURSDAY, 1, 3);
            case 14:
                return new EnrollingInfo(id, DayOfWeek.THURSDAY, 2, 2);
            case 15:
                return new EnrollingInfo(id, DayOfWeek.THURSDAY, 4, 2);
            case 16:
                return new EnrollingInfo(id, DayOfWeek.THURSDAY, 6, 4);

            case 17:
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

    public static Teacher makeTeacher(int id) {
        switch (id) {
            case 0:
                return new Teacher(id, "Qubelley.E.hamarn", "Axis-605",
                        "char-100shiki@zion.co.com", "1192-296-9433", 2);

            case 1:
                return new Teacher(id, "Mark Zuckerberg", "Axis-404",
                        "facebook@american.com", "5656-023-4649", 3);

            case 2:
                return new Teacher(id, "Bill Gates", "MS-RX75",
                        "microsoft@numeric.com", "123-5645-933", 4);

            case 3:
                return new Teacher(id, "Carlos Slim", "Axis-008",
                        "anonymous@homhom.com", "4594-33-3322", 5);

            case 4:
                return new Teacher(id, "Warren Buffett", "Axis-48A",
                        "disny-micky@gmail.com", "599-233-2222", 6);
            default:
                throw new IllegalArgumentException("id=" + String.valueOf(id) + " is not supported");
        }
    }

    public static Assignment makeAssignment(int id) {
        switch (id) {
            case 0:
                return new Assignment(id, "Textbook p.4 Q.2 [1]", "Use a A4 paper.", 3,
                        2017, 7, 19, DayOfWeek.MONDAY, false);

            case 1:
                return new Assignment(id, "Read 'Land Of Lisp from p.100 to p.380",
                        null, 10, 2017, 7, 25, DayOfWeek.FRIDAY, true);

            case 2:
                return new Assignment(id, "Make the Facebook using php",
                        "Do NOT ask Mark Zuckerberg for help!", 8, 2018, 8, 4,
                        DayOfWeek.WEDNESDAY, false);
            default:
                throw new IllegalArgumentException("id=" + String.valueOf(id) + " is not supported");
        }
    }
}
