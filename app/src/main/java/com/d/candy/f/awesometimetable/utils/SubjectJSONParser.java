package com.d.candy.f.awesometimetable.utils;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.d.candy.f.awesometimetable.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by daichi on 7/12/17.
 */

public class SubjectJSONParser {

    private static final String TAG = LogHelper.makeLogTag(SubjectJSONParser.class);

    /**
     * JSON key names
     */
    private static final String KEY_SUBJECTS = "subjects";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "id";
    private static final String KEY_LOCATION_ID = "location_id";
    private static final String KEY_TEACHER_ID = "teacher_id";

    public static String loadJSONFromAsset(Context context, String file) {

        String json;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public static SparseArray<Subject> parse(String jsonStr) {

        if(jsonStr == null) {
            throw new IllegalArgumentException(TAG+"in parse(): jsonStr is null...");
        }
        SparseArray<Subject> subjects = new SparseArray<>();

        try {
            JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray(KEY_SUBJECTS);
            for(int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Set data
                Subject subject = new Subject();
                subject.setName(jsonObject.getString(KEY_NAME));
                subject.setID(jsonObject.getInt(KEY_ID));
                subject.setLocationID(jsonObject.getInt(KEY_LOCATION_ID));
                subject.setTeacherID(jsonObject.getInt(KEY_TEACHER_ID));

                subjects.put(subject.getID(), subject);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

        return subjects;
    }
}
