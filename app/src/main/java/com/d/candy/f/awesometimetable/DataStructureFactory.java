package com.d.candy.f.awesometimetable;

import android.content.Context;

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
        SubjectManager sbjManager = SubjectManager.getInstance();
        return sbjManager.findSubjectByID(id);
    }

    public static WeeklyTimeTable makeTimeTable(int id, Context context) {
        return new WeeklyTimeTable(context);
    }

    public static EnrollingInfo makeEnrollingInfo(int id) {
        return null;
    }
}
