package com.d.candy.f.awesometimetable.utils;

import android.util.SparseArray;

import com.d.candy.f.awesometimetable.structures.Subject;
import com.d.candy.f.awesometimetable.structures.Teacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by daichi on 7/12/17.
 */

public class TeacherJSONParser extends BaseJSONParser {

    private static final String TAG = LogHelper.makeLogTag(TeacherJSONParser.class);

    /**
     * JSON key names
     */
    private static final String KEY_TEACHERS = "teachers";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "ID";
    private static final String KEY_SUBJECT_ID = "subjectID";
    private static final String KEY_ROOM = "room";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_PHONE = "phone";

    public static SparseArray<Teacher> parse(String jsonStr) {

        if(jsonStr == null) {
            throw new IllegalArgumentException(TAG+"in parse(): jsonStr is null...");
        }
        SparseArray<Teacher> teachers = new SparseArray<>();

        try {
            JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray(KEY_TEACHERS);
            for(int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Set data
                Teacher teacher = new Teacher();
                teacher.setName(jsonObject.getString(KEY_NAME));
                teacher.setID(jsonObject.getInt(KEY_ID));
                teacher.setSubjectID(jsonObject.getInt(KEY_SUBJECT_ID));
                teacher.setRoom(jsonObject.getString(KEY_ROOM));
                teacher.setMail(jsonObject.getString(KEY_MAIL));
                teacher.setPhone(jsonObject.getString(KEY_PHONE));

                teachers.put(teacher.getID(), teacher);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

        return teachers;
    }
}
