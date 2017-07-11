package com.d.candy.f.awesometimetable.managers;

import android.content.Context;
import android.util.SparseArray;

import com.d.candy.f.awesometimetable.structures.Subject;
import com.d.candy.f.awesometimetable.utils.LogHelper;
import com.d.candy.f.awesometimetable.utils.SubjectJSONParser;

/**
 * Created by daichi on 7/12/17.
 * This class is singleton class.
 */

public class SubjectManager {

    private static final String TAG = LogHelper.makeLogTag(SubjectManager.class);

    /**
     * single instance of this class
     */
    private static volatile SubjectManager mInstance = null;

    /**
     * Data file name
     */
    private static final String JSON_DATA_LOCATION = "test_subjects.json";

    /**
     * Frag indicating that this class store data or not
     */
    private boolean mIsDataStored = false;

    /**
     * Data of subjects; Key is the ID of a subject
     */
    private SparseArray<Subject> mSubjectTable = null;

    /**
     * Private constructor
     */
    private SubjectManager() {
        // Prevent from java reflection api
        if(mInstance != null) {
            throw new RuntimeException(
                    "Use getInstance() method to get the single instance of this class");
        }
    }

    /**
     * Use this method to get single instance of this class
     */
    public static SubjectManager getInstance() {
        // Double check locking pattern
        // 1. Check for the first time
        if(mInstance == null) {
            // 2. Check for the second time
            synchronized (SubjectManager.class) {
                // If there is no instance available, create new one...
                mInstance = new SubjectManager();
            }
        }

        return mInstance;
    }

    public boolean storeData(Context context) {
        if (!mIsDataStored) {
            mSubjectTable = SubjectJSONParser.parse(
                    SubjectJSONParser.loadJSONFromAsset(context, JSON_DATA_LOCATION));

            if (mSubjectTable != null) {
                mIsDataStored = true;
            }
        }

        return mIsDataStored;
    }

    public boolean isDataStored() {
        return mIsDataStored;
    }

    public void removeData() {
        if(mIsDataStored) {
            mSubjectTable.clear();
            mSubjectTable = null;
            mIsDataStored = false;
        }
    }

    public Subject findSubjectByID(int id) {
        if(mIsDataStored) {
            throw new NullPointerException(TAG+":findSubjectByID():Subject data is not stored!");
        }

        return mSubjectTable.get(id, null);
    }

    public int countSubjects() {
        if(mIsDataStored) {
            return mSubjectTable.size();
        } else {
            return 0;
        }
    }
}
