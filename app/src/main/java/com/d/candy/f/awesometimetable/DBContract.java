package com.d.candy.f.awesometimetable;

/**
 * Created by daichi on 7/14/17.
 */

public class DBContract {

    // Prevent someone from accidentally instantiating this class
    private DBContract() {}

    public static String[] getTableNames() {
        // TODO: When define a new table contents, add the name of the table to the returned String array below
        return new String[] {
                SubjectEntity.TABLE_NAME,
                TeacherEntity.TABLE_NAME,
                EnrollingInfoEntity.TABLE_NAME
        };
    }

    /**
     * Define the table contents here!
     */
    public static class SubjectEntity {
        // id=1 is used for BLANK-Subject entity
        public static final int MIN_USABLE_ID = 2;
        // The id used for BLANK_Subject
        public static final int BLANK_SUBJECT_ID = 1;
        public static final String TABLE_NAME = "subject";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ROOM_ID = "room_id";
        public static final String CLOUMN_TEACHER_ID = "teacher_id";
    }

    public static class TeacherEntity {
        public static final String TABLE_NAME = "teacher";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LAB = "lab";
    }

    public static class EnrollingInfoEntity {
        public static final String TABLE_NAME = "enrolling_info";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
        public static final String COLUMN_PERIOD = "period";
        public static final String COLUMN_SUBJECT_ID = "subject_id";
    }
}
