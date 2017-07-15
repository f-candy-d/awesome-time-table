package com.d.candy.f.awesometimetable.idea;

import com.d.candy.f.awesometimetable.structure.Entity;

/**
 * Created by daichi on 7/14/17.
 */

abstract public class SQLDataManager {

    protected void insertEntity(Entity entity) {
        String table_name = entity.getAffiliation();
        // TODO: write insertion code here!
    }

    protected void deleteEntity(Entity entity) {
        String table_name = entity.getAffiliation();
        // TODO: write removing data code here
    }
}
