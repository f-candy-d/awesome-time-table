package com.d.candy.f.awesometimetable.structure;

import com.d.candy.f.awesometimetable.utils.EntityCache;

/**
 * Created by daichi on 7/18/17.
 */

public class TimeTable {

    EntityCache mDataSet = null;

    public TimeTable() {}

    public TimeTable(final EntityCache dataSet) {
        mDataSet = dataSet;
    }

    public void setDataSet(final EntityCache cache) {
        mDataSet = cache;
    }

    protected EntityCache getDataSet() {
        return mDataSet;
    }
}
