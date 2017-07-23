package com.d.candy.f.awesometimetable.structure;

import com.d.candy.f.awesometimetable.utils.EntityCache;

/**
 * Created by daichi on 7/18/17.
 */

public class TimeTable {

    EntityCache mDataSet = null;

    public TimeTable() {}

    public TimeTable(EntityCache dataSet) {
        mDataSet = dataSet;
    }

    public void setDataSet(EntityCache cache) {
        mDataSet = cache;
    }

    protected EntityCache getDataSet() {
        return mDataSet;
    }

//    public void addEntity(Entity entity) {
//        mDataSet.cache(entity, false);
//    }
}
