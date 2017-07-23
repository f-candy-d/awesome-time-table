package com.d.candy.f.awesometimetable.utils;

import android.util.Log;
import android.util.SparseArray;

import com.d.candy.f.awesometimetable.structure.Assignment;
import com.d.candy.f.awesometimetable.structure.EnrollingInfo;
import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Location;
import com.d.candy.f.awesometimetable.structure.Notification;
import com.d.candy.f.awesometimetable.structure.Subject;
import com.d.candy.f.awesometimetable.structure.Teacher;

import java.util.ArrayList;

/**
 * Created by daichi on 7/16/17.
 */

public class EntityCache {

    private static final String TAG = LogHelper.makeLogTag(EntityCache.class);
    private SparseArray<Subject> mSubjectCache = null;
    private SparseArray<Location> mLocationCache = null;
    private SparseArray<Teacher> mTeacherCache = null;
    private SparseArray<Assignment> mAssignmentCache = null;
    private SparseArray<Notification> mNotificationCache = null;

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

                case ASSIGNMENT: {
                    if(mAssignmentCache == null) {
                        mAssignmentCache = new SparseArray<>();
                    }
                    mAssignmentCache.put(entity.getID(), (Assignment) entity);
                    Log.d(TAG, "Cached ASSIGNMENT -> " + ((Assignment) entity).getTitle() + " | id=" + String.valueOf(entity.getID()));
                    break;
                }

                case NOTIFICATION: {
                    if(mNotificationCache == null) {
                        mNotificationCache = new SparseArray<>();
                    }
                    mNotificationCache.put(entity.getID(), (Notification) entity);
                    Log.d(TAG, "Cached NOTIFICATION-> " + ((Notification) entity).getTitle() + " | id=" + String.valueOf(entity.getID()));
                    break;
                }

                default:
                    throw new IllegalArgumentException("EntityType::" + entity.getEntityType().toString() + " is not supported");
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

            case ASSIGNMENT: {
                if(mAssignmentCache == null) {
                    return false;
                } else {
                    return (mAssignmentCache.get(id, null) != null);
                }
            }

            case NOTIFICATION: {
                if(mNotificationCache == null) {
                    return false;
                } else {
                    return (mNotificationCache.get(id, null) != null);
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

    public Assignment getAssignment(int id) {
        if (mAssignmentCache != null) {
            return mAssignmentCache.get(id, null);
        }
        return null;
    }

    public Notification getNotification(int id) {
        if (mNotificationCache != null) {
            return mNotificationCache.get(id, null);
        }
        return null;
    }

    public ArrayList<Integer> getAllSubjectID() {
        if (mSubjectCache != null) {
            ArrayList<Integer> list = new ArrayList<>(mSubjectCache.size());
            for (int i = 0; i < mSubjectCache.size(); ++i) {
                list.add(mSubjectCache.keyAt(i));
            }
            return list;
        }
        return null;
    }

    public ArrayList<Assignment> getAllAssignments() {
        if (mAssignmentCache != null) {
            ArrayList<Assignment> list = new ArrayList<>(mAssignmentCache.size());
            for (int i = 0; i < mAssignmentCache.size(); ++i) {
                int key = mAssignmentCache.keyAt(i);
                list.add(mAssignmentCache.get(key));
            }
            return list;
        }
        return null;
    }

    public ArrayList<Notification> getAllNotifications() {
        if (mNotificationCache != null) {
            ArrayList<Notification> list = new ArrayList<>(mNotificationCache.size());
            for (int i = 0; i < mNotificationCache.size(); ++i) {
                int key = mNotificationCache.keyAt(i);
                list.add(mNotificationCache.get(key));
            }
            return list;
        }
        return null;
    }

    public ArrayList<Integer> getAllAssignmentID() {
        if (mAssignmentCache != null) {
            ArrayList<Integer> list = new ArrayList<>(mAssignmentCache.size());
            for (int i = 0; i < mAssignmentCache.size(); ++i) {
                list.add(mAssignmentCache.keyAt(i));
            }
            return list;
        }
        return null;
    }

    public ArrayList<Integer> getAllNotificationID() {
        if (mNotificationCache != null) {
            ArrayList<Integer> list = new ArrayList<>(mNotificationCache.size());
            for (int i = 0; i < mNotificationCache.size(); ++i) {
                list.add(mNotificationCache.keyAt(i));
            }
            return list;
        }
        return null;
    }
}
