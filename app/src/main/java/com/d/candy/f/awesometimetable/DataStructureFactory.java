package com.d.candy.f.awesometimetable;

import android.content.Context;

import com.d.candy.f.awesometimetable.entity.EnrollingInfo;
import com.d.candy.f.awesometimetable.entity.Subject;
import com.d.candy.f.awesometimetable.manager.SubjectManager;
import com.d.candy.f.awesometimetable.structure.TimeTable;

/**
 * Created by daichi on 7/15/17.
 */

public class DataStructureFactory {

    public DataStructureFactory() {}

    public static Subject makeSubject(int id) {
        SubjectManager sbjManager = SubjectManager.getInstance();
        return sbjManager.findSubjectByID(id);
    }

    public static TimeTable makeTimeTable(int id, Context context) {
        return new TimeTable(context);
    }

    public static EnrollingInfo makeEnrollingInfo(int id) {
        return null;
    }
}
