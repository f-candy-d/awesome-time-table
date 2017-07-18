package com.d.candy.f.awesometimetable.structure;

import com.d.candy.f.awesometimetable.utils.EntityCache;

/**
 * Created by daichi on 7/18/17.
 */

public class TimeTable {

    EntityCache mEntityCache = null;

    public TimeTable() {}

    public TimeTable(EntityCache cache) {
        mEntityCache = cache;
    }

    protected void setEntityCache(EntityCache cache) {
        mEntityCache = cache;
    }

    protected EntityCache getEntityCache() {
        return mEntityCache;
    }

    public void addEntity(Entity entity) {
        mEntityCache.cache(entity, false);
    }
}
