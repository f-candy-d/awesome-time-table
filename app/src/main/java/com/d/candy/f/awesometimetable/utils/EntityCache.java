package com.d.candy.f.awesometimetable.utils;

import android.util.Log;
import android.util.SparseArray;

import com.d.candy.f.awesometimetable.structure.Entity;
import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.structure.Subject;

/**
 * Created by daichi on 7/16/17.
 */

public class EntityCache {

    private static final String TAG = LogHelper.makeLogTag(EntityCache.class);
    private SparseArray<Subject> mSubjectCache;

    public EntityCache() {
        mSubjectCache = new SparseArray<>();
    }

    public void cache(final Entity entity, boolean replaceIfExist) {
        if(replaceIfExist || !isCached(entity.getID(), entity.getEntityType())) {
            switch (entity.getEntityType()) {

                case SUBJECT:
                    mSubjectCache.put(entity.getID(), (Subject) entity);
                    Log.d(TAG, "Cached SUBJECT -> " + ((Subject) entity).getName() + " | id=" + String.valueOf(entity.getID()));
                    break;
            }
        }
    }

    public boolean isCached(int id, EntityType type) {
        switch (type) {

            case SUBJECT: return (mSubjectCache.get(id, null) != null);
            default: return false;
        }
    }

    public Subject getSubject(int id) {
        return mSubjectCache.get(id, null);
    }
}
