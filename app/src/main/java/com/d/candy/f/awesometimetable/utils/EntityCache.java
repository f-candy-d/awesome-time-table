package com.d.candy.f.awesometimetable.utils;

import android.util.Log;
import android.util.SparseArray;

import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;

/**
 * Created by daichi on 7/16/17.
 */

public class EntityCache {

    private static final String TAG = LogHelper.makeLogTag(EntityCache.class);
    private SparseArray<Subject> mSubjectCache = null;
    private SparseArray<Location> mLocationCache = null;
    private SparseArray<Teacher> mTeacherCache = null;

    public EntityCache() {}

    public void cache(final Entity entity, boolean replaceIfExist) {
        if(replaceIfExist || !isCached(entity.getID(), entity.getEntityType())) {
            switch (entity.getEntityType()) {

                case SUBJECT: {
                    if(mSubjectCache == null) {
                        mSubjectCache = new SparseArray<>();
                    }
                    mSubjectCache.put(entity.getID(), (Subject) entity);
                    Log.d(TAG, "Cached SUBJECT -> " + ((Subject) entity).getName() + " | id=" + String.valueOf(entity.getID()));
                    break;
                }

                case LOCATION: {
                    if(mLocationCache == null) {
                        mLocationCache = new SparseArray<>();
                    }
                    mLocationCache.put(entity.getID(), (Location) entity);
                    Log.d(TAG, "Cached LOCATION -> " + ((Location) entity).getName() + " | id=" + String.valueOf(entity.getID()));
                    break;
                }

                case TEACHER: {
                    if(mTeacherCache == null) {
                        mTeacherCache = new SparseArray<>();
                    }
                    mTeacherCache.put(entity.getID(), (Teacher) entity);
                    Log.d(TAG, "Cached TEACHER -> " + ((Teacher) entity).getName() + " | id=" + String.valueOf(entity.getID()));
                    break;
                }
            }
        }
    }

    public boolean isCached(int id, EntityType type) {

        switch (type) {

            case SUBJECT: {
                if(mSubjectCache == null) {
                    return false;
                } else {
                    return (mSubjectCache.get(id, null) != null);
                }
            }

            case LOCATION: {
                if(mLocationCache == null) {
                    return false;
                } else {
                    return (mLocationCache.get(id, null) != null);
                }
            }

            case TEACHER: {
                if(mTeacherCache == null) {
                    return false;
                } else {
                    return (mTeacherCache.get(id, null) != null);
                }
            }

            default:
                return false;
        }
    }

    public Subject getSubject(int id) {
        if(mSubjectCache != null) {
            return mSubjectCache.get(id, null);
        }
        return null;
    }

    public Location getLocation(int id) {
        if (mLocationCache != null) {
            return mLocationCache.get(id, null);
        }
        return null;
    }

    public Teacher getTeacher(int id) {
        if (mTeacherCache != null) {
            return mTeacherCache.get(id, null);
        }
        return null;
    }
}
