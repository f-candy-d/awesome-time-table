package com.d.candy.f.awesometimetable;

import com.d.candy.f.awesometimetable.structure.EnrollingInfo;

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
                EnrollingInfoEntity.TABLE_NAME,
                LocationEntity.TABLE_NAME,
                AssignmentEntity.TABLE_NAME,
                NotificationEntity.TABLE_NAME
        };
    }

    /**
     * Define the table contents here!
     */
    public static class SubjectEntity {
        private SubjectEntity() {}

        // Use this as the ID of a unspecified subject
        public static final int NULL_ID = 0;
        // The id used for BLANK-Subject
        public static final int BLANK_SUBJECT_ID = 1;
        // (MIN_USABLE_ID - 1) is used for BLANK-Subject entity
        public static final int MIN_USABLE_ID = BLANK_SUBJECT_ID + 1;
        public static final String TABLE_NAME = "subject";
        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String COL_ROOM_ID = "room_id";
        public static final String COL_TEACHER_ID = "teacher_id";
        public static final String COL_LENGTH = "length";
    }

    public static class TeacherEntity {
        private TeacherEntity() {}

        public static final String TABLE_NAME = "teacher";
        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String COL_LAB = "lab";
        public static final String COL_MAIL = "mail";
        public static final String COL_PHONE = "phone";
        public static final String COL_SUBJECT_ID = "subject_id";
    }

    public static class EnrollingInfoEntity {
        private EnrollingInfoEntity() {}

        // Use this as the ID of a unspecified subject
        public static final int NULL_ID = 0;
        public static final String TABLE_NAME = "enrolling_info";
        public static final String COL_ID = "id";
        public static final String COL_DAY_OF_WEEK = "day_of_week";
        public static final String COL_PERIOD = "period";
        public static final String COL_SUBJECT_ID = "subject_id";
    }

    public static class LocationEntity {
        private LocationEntity() {}

        public static final String TABLE_NAME = "location";
        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
    }

    public static class AssignmentEntity {
        private AssignmentEntity() {}

        public static final String TABLE_NAME = "assignment";
        public static final String COL_ID = "id";
        public static final String COL_TITLE = "title";
        public static final String COL_ENROLLING_INFO_ID = "enrolling_info_id";
        public static final String COL_NOTE = "note";
        public static final String COL_IS_DONE = "is_done";
        public static final String COL_DEADLINE_YEAR = "deadline_y";
        public static final String COL_DEADLINE_MONTH = "deadline_m";
        public static final String COL_DEADLINE_DAY = "deadline_d";
        public static final String COL_DEADLINE_DAY_OF_WEEK = "deadline_d_of_w";
    }

    public static class NotificationEntity {
        private NotificationEntity() {}

        public static final String TABLE_NAME = "notification";
        public static final String COL_ID = "id";
        public static final String COL_TITLE = "title";
        public static final String COL_NOTE = "note";
        public static final String COL_SUBJECT_ID = "subject_id";
        public static final String COL_CATEGORY_ID = "category_type";
        public static final String COL_DATETIME_START = "datetime_start";
        public static final String COL_DATETIME_END = "datetime_end";
        public static final String COL_IS_DONE = "is_done";
    }
}
