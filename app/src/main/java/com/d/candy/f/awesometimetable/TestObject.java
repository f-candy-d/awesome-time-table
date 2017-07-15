package com.d.candy.f.awesometimetable;

import com.d.candy.f.awesometimetable.structure.Subject;

/**
 * Created by daichi on 7/16/17.
 */

public class TestObject {

    public static Subject makeSubject(int id) {
        switch (id) {
            case DBContract.SubjectEntity.BLANK_SUBJECT_ID:
                return new Subject(id, null, null, null, 1);

            case 2:
                return new Subject(id, "Math", "Lec.202", "Mark Zuckerberg", 2);

            case 3:
                return new Subject(id, "Japanese", "Lec.406", "Bill Gates", 1);

            case 4:
                return new Subject(id, "English", "Lec.409", "Amancio Ortega", 1);

            case 5:
                return new Subject(id, "Physics", "Lec.102", "Warren Buffett", 3);

            case 6:
                return new Subject(id, "Chemistry", "Exp.Cent.405-A", "Carlos Slim", 1);

            default:
                throw new IllegalArgumentException("id=" + String.valueOf(id) + " is not supported");
        }
    }
}
